package com.libra.stockanalysisi.engine.impl;

import java.io.IOException;

import android.accounts.NetworkErrorException;
import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.libra.stockanalysisi.bean.Stock;
import com.libra.stockanalysisi.engine.IStockInfoService;
import com.libra.stockanalysisi.engine.StockInfoCallBack;
import com.libra.stockanalysisi.engine.serialization.IStockInfoSerializtion;
import com.libra.stockanalysisi.engine.serialization.impl.FreeStockInfoSerialization;
import com.libra.stockanalysisi.netUtils.HttpsClientUtils;

class FreeStockInfoServiceImpl implements IStockInfoService {
	
	HttpsClientUtils m_HTTPClient;
	
	private String m_URL = "http://hq.sinajs.cn/list=&";
	
	private IStockInfoSerializtion m_Serialization;
	
	private StockInfoCallBack m_CallBack;
	
	private Throwable m_Throwable;
	
	public FreeStockInfoServiceImpl(){
		m_HTTPClient = new HttpsClientUtils(5000, 100000);
		m_Serialization = new FreeStockInfoSerialization();
	}
	
	private String getStockURL(String pGID){
		String url = new String(m_URL);
		return url.replace("&", pGID);
	}

	@SuppressLint("NewApi")
	@Override
	public void queryStock(String pStockID, StockInfoCallBack pCallBack) {
		// TODO Auto-generated method stub
		m_CallBack = pCallBack;
		MyAsync async = new MyAsync();
		async.execute(pStockID);
	}
	
	class MyAsync extends AsyncTask<String, Void, Stock>{

		@Override
		protected Stock doInBackground(String... params) {
		try {
				// TODO Auto-generated method stub
				String url = getStockURL(params[0]);
				String pDate = m_HTTPClient.get(url);
				System.out.println(pDate);
				System.out.println("===============");
				 Stock stock = m_Serialization.deserializationStockInfo(pDate);
				return stock;
		} catch (NetworkErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			m_Throwable = e;
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			m_Throwable = e;
		}
		return null;
	}
		
		@Override
		protected void onPostExecute(Stock result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result == null){
				m_CallBack.onFailure(m_Throwable);
				return;
			}
			m_CallBack.onSuccess(result);
			m_CallBack.onFinish();
		}
	}

}
