package com.libra.stockanalysisi.engine.impl;

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
import com.libra.stockanalysisi.bean.Stock;
import com.libra.stockanalysisi.engine.BaseStockInfoCallBack;
import com.libra.stockanalysisi.engine.IContinousStateStocksCallBack;
import com.libra.stockanalysisi.engine.IPersistenceService;
import com.libra.stockanalysisi.engine.IUpdateProgress;
import com.libra.stockanalysisi.engine.StockInfoCallBack;

@SuppressLint("SimpleDateFormat")
public class BussisceFacde {

	private DataNetService m_NetService;

	private IPersistenceService m_PersistenceService;

	private IUpdateProgress m_UpdateProgressCallBack;

	private Context m_Context;

	public BussisceFacde(Context pContext) {
		super();
		m_Context = pContext;
		m_NetService = new DataNetService(pContext);
		m_PersistenceService = new PersistenceServiceImpl();
	}

	private Stock[] readDetailStocksInfo(Date pDate) {
		return m_PersistenceService.readAllStocksDetailInfo(pDate);
	}

	private BaseStock[] readAllBaseStockInfo() {
		return m_PersistenceService.readAllBaseStockInfo();
	}

	public int getStocksNumber() {
		return readAllBaseStockInfo().length;
	}

	/**
	 * 连续下跌的股票
	 * 
	 * @param days
	 * @return
	 */
	public void continuousFalling(int days,
			final IContinousStateStocksCallBack pCallBack) {
		new AsyncTask<Integer, Void, Stock[]>() {

			Throwable mThrowable = null;

			@Override
			protected Stock[] doInBackground(Integer... params) {
				// TODO Auto-generated method stub
				List<Stock[]> infoList = null;
				try {
					if (!m_NetService.isDealTime()) {
						params[0]--;
					}
					infoList = getStockInfoList(params[0]);
				} catch (NetworkErrorException e) {
					// TODO Auto-generated catch block
					mThrowable = e;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mThrowable = e;
				}
				Stock[] stocks = caculateContinousFallingStocks(infoList);
				return stocks;
			}

			protected void onPostExecute(Stock[] result) {
				if (mThrowable != null) {
					pCallBack.onFailure(mThrowable);
					return;
				}
				pCallBack.continusFallingStocks(result);
			};

		}.execute(days);
	}

	/**
	 * 连续下跌的股票
	 * 
	 * @param days
	 * @return
	 */
	public void continuousRise(int days,
			final IContinousStateStocksCallBack pCallBack) {
		new AsyncTask<Integer, Void, Stock[]>() {

			@Override
			protected Stock[] doInBackground(Integer... params) {
				// TODO Auto-generated method stub
				List<Stock[]> infoList = null;
				try {
					if (!m_NetService.isDealTime()) {
						params[0]--;
					}
					infoList = getStockInfoList(params[0]);
				} catch (NetworkErrorException e) {
					// TODO Auto-generated catch block
					pCallBack.onFailure(e);
				} catch (IOException e) {
					// TODO: handle exception
				}
				Stock[] stocks = caculateContinousRiseStocks(infoList);
				return stocks;
			}

			protected void onPostExecute(Stock[] result) {
				pCallBack.continusFallingStocks(result);
			};

		}.execute(days);
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

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				try {
					if (!isHolidayDay(new Date()) && m_NetService.isDealTime()) {
						downloadAllBaseStocksInfo();
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
				// 跌且不停牌
				if (result > 0 && stocks[i].getNowPri() > 0) {
					continue;
				}
				isContinueFallingFlag = false;
				break;
			}
			if (isContinueFallingFlag) {
				Stock stock = new Stock(string, stocks[0].getName(),
						stocks[0].getTodayStartPri(),
						stocks[0].getYestodEndPri(), stocks[0].getNowPri(),
						stocks[0].getTodayMax(), stocks[0].getTodayMin(),
						stocks[0].getCompetitivePri(),
						stocks[0].getReservePri(), stocks[0].getTraNumber(),
						stocks[0].getTraAmount(), stocks[0].getDate());
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
				Stock stock = new Stock(string, stocks[0].getName(),
						stocks[0].getTodayStartPri(),
						stocks[0].getYestodEndPri(), stocks[0].getNowPri(),
						stocks[0].getTodayMax(), stocks[0].getTodayMin(),
						stocks[0].getCompetitivePri(),
						stocks[0].getReservePri(), stocks[0].getTraNumber(),
						stocks[0].getTraAmount(), stocks[0].getDate());
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
	 */
	private List<Stock[]> getStockInfoList(int pDays)
			throws NetworkErrorException {
		List<Stock[]> list = new ArrayList<Stock[]>();
		int invailDay = 0;
		for (int i = 0; i < pDays; i++) {
			Date d = new Date();
			Stock[] stock = null;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = df.format(new Date(d.getTime()
					- ((i + invailDay) * 24 * 60 * 60 * 1000)));
			Date date2;
			try {
				date2 = df.parse(strDate);
				boolean vaildDay = !isHolidayDay(date2);
				while (!vaildDay) {
					invailDay++;
					strDate = df.format(new Date(d.getTime()
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
					System.out.println(pList.size());
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
}
