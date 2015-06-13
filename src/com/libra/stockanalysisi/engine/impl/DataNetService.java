package com.libra.stockanalysisi.engine.impl;

import java.io.IOException;

import android.content.Context;

import com.libra.stockanalysisi.engine.BaseStockInfoCallBack;
import com.libra.stockanalysisi.engine.IAllStockIDService;
import com.libra.stockanalysisi.engine.IStockInfoService;
import com.libra.stockanalysisi.engine.StockInfoCallBack;

class DataNetService implements IAllStockIDService,IStockInfoService{
	
	private IAllStockIDService m_AllStockID;
	
	private IStockInfoService m_StockInfo;

	DataNetService(Context pContext){
		super();
		m_AllStockID = new EastRichesAllStockIdServiceImpl();
		m_StockInfo = new FreeStockInfoServiceImpl();
	}

	@Override
	public void queryStock(String pStockID, StockInfoCallBack pCallBack) {
		// TODO Auto-generated method stub
		m_StockInfo.queryStock(pStockID, pCallBack);
	}

	@Override
	public void queryAllStockID(BaseStockInfoCallBack pCallBack)
			throws IOException {
		// TODO Auto-generated method stub
		m_AllStockID.queryAllStockID(pCallBack);
	}
	
	
}
