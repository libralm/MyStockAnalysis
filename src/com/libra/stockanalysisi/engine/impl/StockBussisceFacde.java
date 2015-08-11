package com.libra.stockanalysisi.engine.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.accounts.NetworkErrorException;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.libra.stockanalysisi.bean.BaseStock;
import com.libra.stockanalysisi.bean.NetFileData;
import com.libra.stockanalysisi.bean.Stock;
import com.libra.stockanalysisi.engine.BaseStockInfoCallBack;
import com.libra.stockanalysisi.engine.FacdeService;
import com.libra.stockanalysisi.engine.IContinousStateStocksCallBack;
import com.libra.stockanalysisi.engine.IDataSyncService.AllNetFilesCallback;
import com.libra.stockanalysisi.engine.IDataSyncService.AsyncFileCallback;
import com.libra.stockanalysisi.engine.IDataSyncService.QueryNetFileCallback;
import com.libra.stockanalysisi.engine.IPersistenceService;
import com.libra.stockanalysisi.engine.IUpdateProgress;
import com.libra.stockanalysisi.engine.NetDataCallback;
import com.libra.stockanalysisi.engine.StockInfoCallBack;

@SuppressLint("SimpleDateFormat")
public class StockBussisceFacde implements FacdeService {

	private DataNetService m_NetService;

	private IPersistenceService m_PersistenceService;

	private IUpdateProgress m_UpdateProgressCallBack;

	private Context m_Context;

	public StockBussisceFacde(Context pContext) {
		super();
		m_Context = pContext;
		m_NetService = new DataNetService(pContext);
		m_PersistenceService = new PersistenceServiceImpl();
	}

	private Stock[] readDetailStocksInfo(Date pDate)
			throws FileNotFoundException {
		return m_PersistenceService.readAllStocksDetailInfo(pDate);
	}

	private BaseStock[] readAllBaseStockInfo() {
		return m_PersistenceService.readAllBaseStockInfo();
	}

	public int getStocksNumber() {
		return readAllBaseStockInfo().length;
	}

	/**
	 * 检查在此期间没有下载的文件
	 * 
	 * @param pBeginDate
	 * @param pEndingDate
	 * @return
	 * @throws NetworkErrorException
	 */
	private Date[] checkNoDownloadFileDate(Date pBeginDate, Date pEndingDate)
			throws NetworkErrorException {
		List<Date> dates = new ArrayList<Date>();
		long seconds = pEndingDate.getTime() - pBeginDate.getTime();
		int days = (int) (seconds / 1000 / 60 / 60 / 24);
		for (int i = 0; i < days; i++) {
			Date date = new Date(pEndingDate.getTime() - (24 * 60 * 60 * 1000) * i);
			try {
				if (!m_NetService.isHoliday(date)) {
					m_PersistenceService.readAllStocksDetailInfo(date);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				dates.add(date);
			}
		}
		Date[] array = new Date[dates.size()];
		return dates.toArray(array);
	}

	/**
	 * 指定连续下跌的日期。
	 * 
	 * @param pBeginDate
	 * @param pEndingDate
	 * @param pCallback
	 * @throws NetworkErrorException
	 */
	public void caculateCustomDatesContinousFallingStocks(
			final Date pBeginDate, final Date pEndingDate,
			final IContinousStateStocksCallBack pCallback)
			{
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				try {
					pCallback.onProgressInfo("检查股票数据完整性……");
					final Date[] noDownloadFile = checkNoDownloadFileDate(
							pBeginDate, pEndingDate);
					if (noDownloadFile.length > 0) {
						// 获取下载地址。
						pCallback.onProgressInfo("查询股票数据信息……");
						requestDownloadFiles(pBeginDate, pEndingDate,
								noDownloadFile,
								new RequestDownloadNetFileDataCallback() {

									@Override
									public void onNetFilesData(
											List<NetFileData> pNetFileData) {
										// TODO Auto-generated method stub
										
										if(pNetFileData.size() > 0){
											pCallback.onProgressInfo("下载股票数据……");
											downloadFiles(noDownloadFile,
													pNetFileData,
													new NetDataCallback() {
												
												@Override
												public void onSuccess() {
													// TODO Auto-generated
													// method stub
													pCallback.onProgressInfo("开始分析股票数据……");
													continuousFallingFromLocal(pEndingDate, pBeginDate, pCallback);
												}
												
												@Override
												public void onFailure(
														int pCode,
														String pMsg) {
													// TODO Auto-generated
													// method stub
													
												}

												@Override
												public void onSkip() {
													// TODO Auto-generated method stub
													pCallback.onProgressInfo("开始分析股票数据……");
													continuousFallingFromLocal(new Date(pEndingDate.getTime() - (24*60*60*1000)), new Date(pBeginDate.getTime() - (24*60*60*1000)), pCallback);
												}
											});
										} else{
											pCallback.onProgressInfo("开始分析股票数据……");
											continuousFallingFromLocal(new Date(pEndingDate.getTime() - (24*60*60*1000)), new Date(pBeginDate.getTime() - (24*60*60*1000)), pCallback);
										}
									}
								});
					} else {
						// 本地获取相应的数据开始分析。
						pCallback.onProgressInfo("开始分析股票数据……");
						continuousFallingFromLocal(pEndingDate, pBeginDate, pCallback);
					}
				} catch (NetworkErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					pCallback.onFailure(e);
				}
				return null;
			}

		}.execute();
	}
	
	/**
	 * 根据一个时间区间，下载未在本地的文件
	 * @param pNoDownloadFile
	 * @param pNetFileData
	 * @param pCallback
	 */
	private void downloadFiles(final Date[] pNoDownloadFile,final List<NetFileData> pNetFileData,final NetDataCallback pCallback) {
		List<Date> noDownloadFiles = Arrays.asList(pNoDownloadFile);
		int size = pNetFileData.size();
		List<NetFileData> needDownloadFile = new ArrayList<NetFileData>();
		//查询出的数据。
		for (int i = 0; i < size; i++) {
			NetFileData data = pNetFileData.get(i);
			String fileName = data.getOriFileName();
			//匹配本地不存在的数据
			for (int j = 0; j < noDownloadFiles.size(); j++) {
				if (new SimpleDateFormat("yyyy-MM-dd").format(noDownloadFiles.get(j)).equals(fileName)) {
					needDownloadFile.add(data);
				noDownloadFiles.remove(fileName);
				break;
				}
			}
		}
		if(needDownloadFile.size() > 0){			
			iteraterDownload(needDownloadFile,0,pCallback);
		} else{
			pCallback.onSkip();
		}
	}
	
	/**
	 * 迭代下载文件
	 * @param pNeedDownloadFiles
	 * @param pPosition
	 * @param pCallback
	 */
	private void iteraterDownload(final List<NetFileData> pNeedDownloadFiles, final int pPosition, final NetDataCallback pCallback) {
		// TODO Auto-generated method stub
		NetFileData data = pNeedDownloadFiles.get(pPosition);
		m_NetService.downFile(data, new AsyncFileCallback() {
			
			@Override
			public void onSuccess(String pFileName,String url) {
				int nextPosition = pPosition + 1;
				if(nextPosition < pNeedDownloadFiles.size()){
					iteraterDownload(pNeedDownloadFiles, nextPosition, pCallback);
				} else{					
					pCallback.onSuccess();
				}
			}

			@Override
			public void onProgress(int pRatio) {
			}

			@Override
			public void onError(int pCode, String pMsg) {
				int nextPosition = pPosition + 1;
				pCallback.onFailure(pCode, pMsg);
				if(nextPosition < pNeedDownloadFiles.size()){
					iteraterDownload(pNeedDownloadFiles, nextPosition, pCallback);
				}
			}
		});
	}

	/**
	 * 指定连续上涨的日期。
	 * 
	 * @param pBeginDate
	 * @param pEndingDate
	 * @param pCallback
	 */
	public void caculateCustomDatesContinousRiseStocks(final Date pBeginDate,
			final Date pEndingDate,
			final IContinousStateStocksCallBack pCallback) {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				try {
					pCallback.onProgressInfo("检查股票数据完整性……");
					final Date[] noDownloadFile = checkNoDownloadFileDate(
							pBeginDate, pEndingDate);
					if (noDownloadFile.length > 0) {
						// 获取下载地址。
						pCallback.onProgressInfo("查询股票数据信息……");
						requestDownloadFiles(pBeginDate, pEndingDate,
								noDownloadFile,
								new RequestDownloadNetFileDataCallback() {

									@Override
									public void onNetFilesData(
											List<NetFileData> pNetFileData) {
										// TODO Auto-generated method stub
										if(pNetFileData.size() > 0){											
											pCallback.onProgressInfo("下载股票数据……");
											downloadFiles(noDownloadFile,
													pNetFileData,
													new NetDataCallback() {
												
												@Override
												public void onSuccess() {
													// TODO Auto-generated
													// method stub
													pCallback.onProgressInfo("开始分析股票数据……");
													continuousRiseFromLocal(
															pEndingDate,
															pBeginDate, pCallback);
												}
												
												@Override
												public void onFailure(
														int pCode,
														String pMsg) {
													// TODO Auto-generated
													// method stub
													pCallback.onProgressInfo("开始分析股票数据……");
													continuousRiseFromLocal(new Date(pEndingDate.getTime() - (24*60*60*1000)), new Date(pBeginDate.getTime() - (24*60*60*1000)), pCallback);
												}

												@Override
												public void onSkip() {
													// TODO Auto-generated method stub
													
												}
											});
										} else{
											pCallback.onProgressInfo("开始分析股票数据……");
											continuousRiseFromLocal(new Date(pEndingDate.getTime() - (24*60*60*1000)), new Date(pBeginDate.getTime() - (24*60*60*1000)), pCallback);
										}
										}
								});
					} else {
						// 本地获取相应的数据开始分析。
						pCallback.onProgressInfo("开始分析股票数据……");
						continuousRiseFromLocal(
								pEndingDate,
								pBeginDate, pCallback);
					}
				} catch (NetworkErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					pCallback.onFailure(e);
				}
				return null;
			}

		}.execute();
	}

	/**
	 * 请求下载文件的URL地址
	 * @param pBeginDate
	 * @param pEndingDate
	 * @param pNoDownloadFile
	 */
	private void requestDownloadFiles(final Date pBeginDate,
			final Date pEndingDate, final Date[] pNoDownloadFile,final RequestDownloadNetFileDataCallback callBack) {
		m_NetService.requestNetFiles(pBeginDate, pEndingDate,
				new AllNetFilesCallback() {

					@Override
					public void onNetFilesData(
							final List<NetFileData> pNetFileData) {
						// TODO Auto-generated method stub
						callBack.onNetFilesData(pNetFileData);
					}

					@Override
					public void onError(int pCode, String pMsg) {
						// TODO Auto-generated method stub

					}
				});
	}
	
	interface RequestDownloadNetFileDataCallback{
		public void onNetFilesData(List<NetFileData> pNetFileData);
	}

	/**
	 * 连续下跌的股票（数据源都在本地）
	 * 
	 * @param days
	 * @return
	 */
	private void continuousFallingFromLocal(final Date pEndingDay, Date pBeginDate,
			final IContinousStateStocksCallBack pCallBack) {
		new AsyncTask<Date, Void, Stock[]>() {

			Throwable mThrowable = null;

			@Override
			protected Stock[] doInBackground(Date... params) {
				// TODO Auto-generated method stub
				List<Stock[]> infoList = null;
				try {
					infoList = getStockInfoListFromLocal(pEndingDay, params[0]);
				} catch (NetworkErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Stock[] stocks = caculateContinousFallingStocks(infoList);
				return stocks;
			}

			protected void onPostExecute(Stock[] result) {
				if (mThrowable != null) {
					pCallBack.onFailure(mThrowable);
					return;
				}
				pCallBack.continusStatesStocks(result);
			};

		}.execute(pBeginDate);
	}

	/**
	 * 连续下跌的股票
	 * 
	 * @param days
	 * @return
	 */
	private void continuousRiseFromLocal(final Date pEndingDay, Date pBeginDay,
			final IContinousStateStocksCallBack pCallBack) {
		new AsyncTask<Date, Void, Stock[]>() {

			@Override
			protected Stock[] doInBackground(Date... params) {
				// TODO Auto-generated method stub
				List<Stock[]> infoList = null;
				try {
					infoList = getStockInfoListFromLocal(pEndingDay, params[0]);
				} catch (NetworkErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Stock[] stocks = caculateContinousRiseStocks(infoList);
				return stocks;
			}

			protected void onPostExecute(Stock[] result) {
				pCallBack.continusStatesStocks(result);
			};

		}.execute(pBeginDay);
	}

	/**
	 * 数据更新
	 * 
	 * @throws NetworkErrorException
	 */
	public void updateData(final IUpdateProgress pUpdateCallback) {
		m_UpdateProgressCallBack = pUpdateCallback;
		new AsyncTask<Void, Void, Void>() {

			Throwable mThrowable;
			
			boolean noNeedDL;

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				try {
					if (!isHolidayDay(new Date()) && m_NetService.isDealTime()) {
						downloadAllBaseStocksInfo();
					} else{
						noNeedDL = true;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mThrowable = e;
				} catch (NetworkErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mThrowable = e;
				}
				return null;
			}

			protected void onPostExecute(Void result) {
				if (mThrowable != null) {
					pUpdateCallback.onFailure(mThrowable);
					return;
				} else if(noNeedDL){
					pUpdateCallback.onFinish();
				}
			};

		}.execute();

	}

	/**
	 * 是否为交易日
	 * 
	 * @param date
	 * @return
	 * @throws NetworkErrorException
	 */
	private boolean isHolidayDay(Date date) throws NetworkErrorException {
		return m_NetService.isHoliday(date);
	}

	/**
	 * 计算连续下跌的股票
	 * 
	 * @param pStockInfoList
	 */
	private Stock[] caculateContinousFallingStocks(List<Stock[]> pStockInfoList) {
		List<BaseStock> list = new ArrayList<BaseStock>();
		int size = pStockInfoList.size();
		Map<String, Stock[]> gidStockMap = new HashMap<String, Stock[]>();
		for (int i = 0; i < size; i++) {
			Stock[] stocks = pStockInfoList.get(i);
			if (stocks == null) {
				continue;
			}
			int length = stocks.length;
			for (int j = 0; j < length; j++) {
				String gid = stocks[j].getGid();
				if (gidStockMap.get(gid) == null) {
					Stock[] sameGidStocks = new Stock[size];
					gidStockMap.put(gid, sameGidStocks);
				}
				gidStockMap.get(gid)[i] = stocks[j];
			}
		}
		Set<String> gidKeySet = gidStockMap.keySet();
		for (String string : gidKeySet) {
			boolean isContinueFallingFlag = true;
			Stock[] stocks = gidStockMap.get(string);
			int length = stocks.length;
			for (int i = 0; i < length; i++) {
				if (stocks[i] == null) {
					isContinueFallingFlag = false;
					break;
				}
				double result = stocks[i].getYestodEndPri()
						- stocks[i].getNowPri();
				// 跌且不停牌
				if (result > 0 && stocks[i].getNowPri() > 0) {
					continue;
				}
				isContinueFallingFlag = false;
				break;
			}
			if (isContinueFallingFlag) {
				Stock stock = new Stock(string, stocks[stocks.length-1].getName(),
						stocks[stocks.length-1].getTodayStartPri(),
						stocks[stocks.length-1].getYestodEndPri(), stocks[0].getNowPri(),
						stocks[stocks.length-1].getTodayMax(), stocks[0].getTodayMin(),
						stocks[stocks.length-1].getCompetitivePri(),
						stocks[stocks.length-1].getReservePri(), stocks[0].getTraNumber(),
						stocks[stocks.length-1].getTraAmount(), stocks[0].getDate());
				list.add(stock);
			}
			isContinueFallingFlag = true;
		}
		return list.toArray(new Stock[list.size()]);
	}

	/**
	 * 计算连续上涨的股票
	 * 
	 * @param pStockInfoList
	 */
	private Stock[] caculateContinousRiseStocks(List<Stock[]> pStockInfoList) {
		// TODO Auto-generated method stub
		List<BaseStock> list = new ArrayList<BaseStock>();
		int size = pStockInfoList.size();
		Map<String, Stock[]> gidStockMap = new HashMap<String, Stock[]>();
		for (int i = 0; i < size; i++) {
			Stock[] stocks = pStockInfoList.get(i);
			if (stocks == null) {
				continue;
			}
			int length = stocks.length;
			for (int j = 0; j < length; j++) {
				String gid = stocks[j].getGid();
				if (gidStockMap.get(gid) == null) {
					Stock[] sameGidStocks = new Stock[size];
					gidStockMap.put(gid, sameGidStocks);
				}
				gidStockMap.get(gid)[i] = stocks[j];
			}
		}
		Set<String> gidKeySet = gidStockMap.keySet();
		for (String string : gidKeySet) {
			boolean isContinueFallingFlag = true;
			Stock[] stocks = gidStockMap.get(string);
			int length = stocks.length;
			for (int i = 0; i < length; i++) {
				if (stocks[i] == null) {
					isContinueFallingFlag = false;
					break;
				}
				double result = stocks[i].getYestodEndPri()
						- stocks[i].getNowPri();
				if (result < 0) {
					continue;
				}
				isContinueFallingFlag = false;
				break;
			}
			if (isContinueFallingFlag) {
				Stock stock = new Stock(string, stocks[stocks.length-1].getName(),
						stocks[stocks.length-1].getTodayStartPri(),
						stocks[stocks.length-1].getYestodEndPri(), stocks[0].getNowPri(),
						stocks[stocks.length-1].getTodayMax(), stocks[0].getTodayMin(),
						stocks[stocks.length-1].getCompetitivePri(),
						stocks[stocks.length-1].getReservePri(), stocks[0].getTraNumber(),
						stocks[stocks.length-1].getTraAmount(), stocks[0].getDate());
				list.add(stock);
			}
			isContinueFallingFlag = true;
		}
		return list.toArray(new Stock[list.size()]);
	}

	/**
	 * 得到最近days天的数据。
	 * 
	 * @param pDays
	 * @return
	 * @throws NetworkErrorException
	 * @throws FileNotFoundException
	 */
	private List<Stock[]> getStockInfoListFromLocal(Date pEndingDay, int pDays)
			throws NetworkErrorException, FileNotFoundException {
		List<Stock[]> list = new ArrayList<Stock[]>();
		int invailDay = 0;
		for (int i = 0; i < pDays; i++) {
			Stock[] stock = null;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = df.format(new Date(pEndingDay.getTime()
					- ((i + invailDay) * 24 * 60 * 60 * 1000)));
			Date date2;
			try {
				date2 = df.parse(strDate);
				boolean vaildDay = !isHolidayDay(date2);
				while (!vaildDay) {
					invailDay++;
					strDate = df.format(new Date(pEndingDay.getTime()
							- ((i + invailDay) * 24 * 60 * 60 * 1000)));
					date2 = df.parse(strDate);
					vaildDay = !isHolidayDay(date2);
				}
				stock = readDetailStocksInfo(date2);
				list.add(stock);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	private List<Stock[]> getStockInfoListFromLocal(Date pEndingDay, Date pBeginDay) throws NetworkErrorException{
		List<Stock[]> list = new ArrayList<Stock[]>();
		long millsecond = 24*60*60*1000;
		Date curDate = pBeginDay;
		while((curDate = new Date(millsecond + curDate.getTime())).compareTo(pEndingDay) <= 0){
			if(!isHolidayDay(curDate)){
				Stock[] stocks;
				try {
					stocks = readDetailStocksInfo(curDate);
					list.add(stocks);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	private void downloadAllBaseStocksInfo() throws IOException {
		m_NetService.queryAllStockID(new BaseStockInfoCallBack() {

			@SuppressLint("NewApi")
			@Override
			public void onSuccess(BaseStock[] pBaseStocks) {
				// TODO Auto-generated method stub
				m_PersistenceService.saveAllBaseStockInfo(pBaseStocks);
				final int length = pBaseStocks.length;
				final List<Stock> list = new ArrayList<Stock>();
				int splitNum = 7;
				int avgNum = length / splitNum;
				for (int i = 0; i < splitNum - 1; i++) {
					BaseStock[] stocks = Arrays.copyOfRange(pBaseStocks, avgNum
							* i, avgNum * (i + 1));
					new AsyncTask<BaseStock[], Void, Void>() {

						@Override
						protected Void doInBackground(BaseStock[]... params) {
							// TODO Auto-generated method stub
							downloadAllStockDetailInfo(params[0], list, length);
							return null;
						}

					}.execute(stocks);
				}
				BaseStock[] stocks2 = Arrays.copyOfRange(pBaseStocks, avgNum
						* (splitNum - 1), length);	
				downloadAllStockDetailInfo(stocks2, list, length);
			}
		});
	}

	private void downloadAllStockDetailInfo(BaseStock[] pBaseStocks,
			final List<Stock> pList, final int pBaseStocksLength) {
		DataNetService netService = new DataNetService(m_Context);
		for (BaseStock baseStock : pBaseStocks) {
			String gid = baseStock.getGid();
			netService.queryStock(gid, new StockInfoCallBack() {

				@Override
				public void onSuccess(Stock pStock) {
					// TODO Auto-generated method stub
					pList.add(pStock);
				}

				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					int progress = caculateProgress(pList.size(),
							pBaseStocksLength);
					m_UpdateProgressCallBack.update(progress);
					if (pList.size() == pBaseStocksLength) {
						m_PersistenceService.saveAllStocksDetailInfo(pList
								.toArray(new Stock[pBaseStocksLength]));
						m_UpdateProgressCallBack.onFinish();
					}
				}

				private int caculateProgress(int size, int length) {
					// TODO Auto-generated method stub
					return (int) ((size / (double) length) * 100);
				}

				@Override
				public void onFailure(Throwable pThrowable) {
					// TODO Auto-generated method stub
					m_UpdateProgressCallBack.onFailure(pThrowable);
				}
			});
		}
	}
	
	/**
	 * 上传文件
	 * @param pfile
	 * @param pCallback
	 */
	public void uploadFile(final File pfile, final AsyncFileCallback pCallback) {
		
		
		m_NetService.uploadFile(pfile, new AsyncFileCallback() {

			@Override
			public void onSuccess(final String pFileName, final String url) {
				// TODO Auto-generated method stub
				m_NetService.queryNetFile(url, new QueryNetFileCallback() {
					
					@Override
					public void onUnknow() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onSuccess(List<NetFileData> pNetFileDatas) {
						// TODO Auto-generated method stub
						if(pNetFileDatas.size() == 0){
							NetFileData netFileData = new NetFileData();
							netFileData.setLocalFile(pfile);
							netFileData.setFileName(pFileName);
							netFileData.setOriFileName(pfile.getName());
							netFileData.setUrl(url);
							m_NetService.uploadNetFilesInfo(netFileData, pCallback);
						}
					}
					
					@Override
					public void onFail(int pCode, String pReason) {
						// TODO Auto-generated method stub
						
					}
				});
			}

			@Override
			public void onProgress(int pRatio) {
				// TODO Auto-generated method stub
				pCallback.onProgress(pRatio);
			}

			@Override
			public void onError(int pCode, String pMsg) {
				// TODO Auto-generated method stub
				pCallback.onError(pCode, pMsg);
			}
		});
	}

	
	/**
	 * 持续上涨
	 * @param pDays
	 * @param pContinousStateStocksCallBack
	 * @throws NetworkErrorException 
	 */
	public void continuousRise(final int pDays,
			final IContinousStateStocksCallBack pCallback){
		// TODO Auto-generated method stub
		new AsyncTask<Integer, Void, Void>(){

			@Override
			protected Void doInBackground(Integer... params) {
				// TODO Auto-generated method stub
				Date endingDate = new Date();
				Date beginDate = null;
				try {
					beginDate = getBeginDay(pDays, endingDate);
				} catch (NetworkErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					pCallback.onFailure(e);
				}
				caculateCustomDatesContinousRiseStocks(beginDate, endingDate, pCallback);
				return null;
			}
		}.execute(pDays);
	}

	
	
	/**
	 * 持续下跌
	 * @param pDays
	 * @param pCallback
	 * @throws NetworkErrorException 
	 */
	public void continuousFalling(final int pDays,
			final IContinousStateStocksCallBack pCallback){
		// TODO Auto-generated method stub
		new AsyncTask<Integer, Void, Void>(){

			@Override
			protected Void doInBackground(Integer... params) {
				// TODO Auto-generated method stub
				Date endingDate = new Date();
				Date beginDate = null;
				try {
					beginDate = getBeginDay(pDays, endingDate);
				} catch (NetworkErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					pCallback.onFailure(e);
				}
				caculateCustomDatesContinousFallingStocks(beginDate, endingDate, pCallback);
				return null;
			}
			
		}.execute(pDays);
	}

	private Date getBeginDay(int pDays, Date endingDate)
			throws NetworkErrorException {
		int invailDay = 0;
		for (int i = 0; i < pDays; i++) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = df.format(new Date(endingDate.getTime()
					- ((i + invailDay) * 24 * 60 * 60 * 1000)));
			Date date2;
			try {
				date2 = df.parse(strDate);
				boolean vaildDay = !isHolidayDay(date2);
				while(!vaildDay) {
					invailDay++;
					strDate = df.format(new Date(endingDate.getTime()
							- ((i + invailDay) * 24 * 60 * 60 * 1000)));
					date2 = df.parse(strDate);
					vaildDay = !isHolidayDay(date2);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int millseconds = 24*60*60*1000*(pDays+invailDay);
		Date beginDate = new Date(endingDate.getTime()-millseconds);
		return beginDate;
	}
}
