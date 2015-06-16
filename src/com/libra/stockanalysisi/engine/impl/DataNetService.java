package com.libra.stockanalysisi.engine.impl;

import java.io.IOException;
import java.util.Date;

import android.accounts.NetworkErrorException;
import android.content.Context;

import com.libra.stockanalysisi.engine.BaseStockInfoCallBack;
import com.libra.stockanalysisi.engine.HolidayCallBack;
import com.libra.stockanalysisi.engine.IAllStockIDService;
import com.libra.stockanalysisi.engine.IHolidayService;
import com.libra.stockanalysisi.engine.IStockInfoService;
import com.libra.stockanalysisi.engine.StockInfoCallBack;

class DataNetService implements IAllStockIDService,IStockInfoService,IHolidayService{
	
	private IAllStockIDService m_AllStockID;
	
	private IStockInfoService m_StockInfo;
	
	private IHolidayService m_HolidayService;

	DataNetService(Context pContext){
		super();
		m_AllStockID = new EastRichesAllStockIdServiceImpl();
		m_StockInfo = new FreeStockInfoServiceImpl();
		m_HolidayService = new EasyBotHolidayServiceImpl();
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

	@Override
	public boolean isHoliday(Date pDate) throws NetworkErrorException {
		// TODO Auto-generated method stub
		return m_HolidayService.isHoliday(pDate);
	}
	
	
}