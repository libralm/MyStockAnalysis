package com.libra.stockanalysisi.engine.impl;

import java.io.IOException;

import android.os.AsyncTask;

import com.libra.stockanalysisi.bean.BaseStock;
import com.libra.stockanalysisi.engine.BaseStockInfoCallBack;
import com.libra.stockanalysisi.engine.IAllStockIDService;
import com.libra.stockanalysisi.engine.serialization.IAllStockSerialization;
import com.libra.stockanalysisi.engine.serialization.impl.EastRichesAllStockSerialization;

class EastRichesAllStockIdServiceImpl implements IAllStockIDService {
	
	private final String URI = "http://quote.eastmoney.com/stocklist.html";
	
	
	private IAllStockSerialization m_Serialization;
	
	private BaseStockInfoCallBack m_CallBack;
	
	public EastRichesAllStockIdServiceImpl(){
		super();
		m_Serialization = new EastRichesAllStockSerialization();
	}

	@Override
	public void queryAllStockID(BaseStockInfoCallBack pCallBack) throws IOException {
		// TODO Auto-generated method stub
		MyAsync async = new MyAsync();
		async.execute();
		m_CallBack = pCallBack;
	}
	
	class MyAsync extends AsyncTask<Void, Void, BaseStock[]>{

		@Override
		protected BaseStock[] doInBackground(Void... params) {
			// TODO Auto-generated method stub
			BaseStock[] stocks = m_Serialization.deserializationStockInfo(URI);
			return stocks;
		}
		
		@Override
		protected void onPostExecute(BaseStock[] result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			m_CallBack.onSuccess(result);
		}
	}
}
