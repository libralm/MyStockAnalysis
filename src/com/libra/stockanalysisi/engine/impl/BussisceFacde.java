package com.libra.stockanalysisi.engine.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

	public BussisceFacde(Context pContext) {
		super();
		m_NetService = new DataNetService(pContext);
		m_PersistenceService = new PersistenceServiceImpl();
	}

	private Stock[] readDetailStocksInfo(Date pDate) {
		return m_PersistenceService.readAllStocksDetailInfo(pDate);
	}

	private BaseStock[] readAllBaseStockInfo() {
		return m_PersistenceService.readAllBaseStockInfo();
	}
	
	public int getStocksNumber(){
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
		new AsyncTask<Integer, Void, BaseStock[]>() {

			@Override
			protected BaseStock[] doInBackground(Integer... params) {
				// TODO Auto-generated method stub
				List<Stock[]> infoList = null;
				try {
					infoList = getStockInfoList(params[0]);
				} catch (NetworkErrorException e) {
					// TODO Auto-generated catch block
					pCallBack.onFailure(e);
				}
				BaseStock[] stocks = caculateContinousFallingStocks(infoList);
				return stocks;
			}

			protected void onPostExecute(BaseStock[] result) {
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
		new AsyncTask<Integer, Void, BaseStock[]>() {

			@Override
			protected BaseStock[] doInBackground(Integer... params) {
				// TODO Auto-generated method stub
				List<Stock[]> infoList = null;
				try {
					infoList = getStockInfoList(params[0]);
				} catch (NetworkErrorException e) {
					// TODO Auto-generated catch block
					pCallBack.onFailure(e);
				}
				BaseStock[] stocks = caculateContinousRiseStocks(infoList);
				return stocks;
			}

			protected void onPostExecute(BaseStock[] result) {
				pCallBack.continusFallingStocks(result);
			};

		}.execute(days);
	}
	
	/**
	 * 数据更新
	 * @throws NetworkErrorException 
	 */
	public void updateData(final IUpdateProgress pUpdateCallback){
		m_UpdateProgressCallBack = pUpdateCallback;
		new AsyncTask<Void, Void, Void>(){

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				try {
					try {
						if(!isHolidayDay(new Date())){				
							downloadAllBaseStocksInfo();
						} else{
							pUpdateCallback.onFinish();
						}
					} catch (NetworkErrorException e) {
						// TODO Auto-generated catch block
						pUpdateCallback.onFailure(e);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
		}.execute();
		
	}
	
	/**
	 * 是否为交易日
	 * @param date
	 * @return
	 * @throws NetworkErrorException 
	 */
	private boolean isHolidayDay(Date date) throws NetworkErrorException{
		return m_NetService.isHoliday(date);
	}

	/**
	 * 计算连续下跌的股票
	 * 
	 * @param pStockInfoList
	 */
	private BaseStock[] caculateContinousFallingStocks(List<Stock[]> pStockInfoList) {
		// TODO Auto-generated method stub
		List<BaseStock> list = new ArrayList<BaseStock>();
		int size = pStockInfoList.size();
		Map<String, Stock[]> gidStockMap = new HashMap<String, Stock[]>();
		for (int i = 0; i < size; i++) {
			Stock[] stocks = pStockInfoList.get(i);
			if(stocks == null){
				continue;
			}
			int length = stocks.length;
			for(int j =0; j < length; j++){
				String gid = stocks[j].getGid();
				if(gidStockMap.get(gid) == null){
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
			for(int i = 0; i < length; i++){
				if(stocks[i] == null){
					isContinueFallingFlag = false;
					break;
				}
				double result = stocks[i].getYestodEndPri() - stocks[i].getNowPri();
				if(result > 0 ){
					continue;
				}
				isContinueFallingFlag = false;
				break;
			}
			if(isContinueFallingFlag){
				BaseStock baseStock = new BaseStock();
				baseStock.setGid(string);
				baseStock.setName(stocks[0].getName());
				list.add(baseStock);
			}
			isContinueFallingFlag = true;
		}
		return list.toArray(new BaseStock[list.size()]);
	}
	
	/**
	 * 计算连续上涨的股票
	 * 
	 * @param pStockInfoList
	 */
	private BaseStock[] caculateContinousRiseStocks(List<Stock[]> pStockInfoList) {
		// TODO Auto-generated method stub
		List<BaseStock> list = new ArrayList<BaseStock>();
		int size = pStockInfoList.size();
		Map<String, Stock[]> gidStockMap = new HashMap<String, Stock[]>();
		for (int i = 0; i < size; i++) {
			Stock[] stocks = pStockInfoList.get(i);
			if(stocks == null){
				continue;
			}
			int length = stocks.length;
			for(int j =0; j < length; j++){
				String gid = stocks[j].getGid();
				if(gidStockMap.get(gid) == null){
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
			for(int i = 0; i < length; i++){
				if(stocks[i] == null){
					isContinueFallingFlag = false;
					break;
				}
				double result = stocks[i].getYestodEndPri() - stocks[i].getNowPri();
				if(result < 0 ){
					continue;
				}
				isContinueFallingFlag = false;
				break;
			}
			if(isContinueFallingFlag){
				BaseStock baseStock = new BaseStock();
				baseStock.setGid(string);
				baseStock.setName(stocks[0].getName());
				list.add(baseStock);
			}
			isContinueFallingFlag = true;
		}
		return list.toArray(new BaseStock[list.size()]);
	}

	/**
	 * 得到最近days天的数据。
	 * 
	 * @param pDays
	 * @return
	 * @throws NetworkErrorException 
	 */
	private List<Stock[]> getStockInfoList(int pDays) throws NetworkErrorException {
		List<Stock[]> list = new ArrayList<Stock[]>();
		int invailDay = 0;
		for (int i = 0; i < pDays; i++) {
			Date d = new Date();
			Stock[] stock = null;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = df.format(new Date(d.getTime()
					- ((i+invailDay) * 24 * 60 * 60 * 1000)));
			Date date2;
			try {
				date2 = df.parse(strDate);
				boolean vaildDay = !isHolidayDay(date2);
				while(!vaildDay){
					invailDay++;
					strDate = df.format(new Date(d.getTime()
							- ((i+invailDay) * 24 * 60 * 60 * 1000)));
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

			@Override
			public void onSuccess(BaseStock[] pBaseStocks) {
				// TODO Auto-generated method stub
				m_PersistenceService.saveAllBaseStockInfo(pBaseStocks);
				downloadAllStockDetailInfo(pBaseStocks);
			}
		});
	}

	private void downloadAllStockDetailInfo(BaseStock[] pBaseStocks) {
		final int length = pBaseStocks.length;
		final List<Stock> list = new ArrayList<Stock>();
		for (BaseStock baseStock : pBaseStocks) {
			String gid = baseStock.getGid();
			m_NetService.queryStock(gid, new StockInfoCallBack() {

				@Override
				public void onSuccess(Stock pStock) {
					// TODO Auto-generated method stub
					list.add(pStock);
				}

				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					int progress = caculateProgress(list.size(),length);
					m_UpdateProgressCallBack.update(progress);
					if (list.size() == length) {
						m_PersistenceService.saveAllStocksDetailInfo(list
								.toArray(new Stock[length]));
					}
				}

				private int caculateProgress(int size, int length) {
					// TODO Auto-generated method stub
					return (int) ((size/(double)length)*100);
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