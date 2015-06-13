package com.libra.stockanalysisi.engine.impl;

import java.io.IOException;

import android.content.Context;

import com.libra.stockanalysisi.bean.Stock;
import com.libra.stockanalysisi.engine.IStockInfoService;
import com.libra.stockanalysisi.engine.StockInfoCallBack;
import com.libra.stockanalysisi.engine.serialization.IStockInfoSerializtion;
import com.libra.stockanalysisi.engine.serialization.impl.JuheStockInfoSerialization;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

class JuheStockInfoServiceImpl implements IStockInfoService {
	
	private final String APP_KEY = "6d3f5a6d61cd308827521b71e3bd57e5";
	
	private final String HS_URI = "http://web.juhe.cn:8080/finance/stock/hs";
	
	private Context m_Context;
	
	private IStockInfoSerializtion m_StockInfoSerializtion;
	
	public JuheStockInfoServiceImpl(Context pContext){
		m_Context = pContext;
		m_StockInfoSerializtion = new JuheStockInfoSerialization();
	}

	@Override
	public void queryStock(String pStockID, final StockInfoCallBack pCallBack) {
		// TODO Auto-generated method stub
		Parameters parameters = new Parameters();
		parameters.add("gid", pStockID);
		parameters.add("key", APP_KEY);
		JuheData.executeWithAPI(m_Context, 21, HS_URI, JuheData.GET, parameters, new DataCallBack() {
			
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Stock stock = null;
				try {
					stock = m_StockInfoSerializtion.deserializationStockInfo(arg1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pCallBack.onSuccess(stock);
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				pCallBack.onFinish();
			}
			
			@Override
			public void onFailure(int arg0, String arg1, Throwable arg2) {
				// TODO Auto-generated method stub
				pCallBack.onFailure(arg2);
			}
		});
	}
}
