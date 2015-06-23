package com.libra.stockanalysisi.engine.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import android.accounts.NetworkErrorException;
import android.content.Context;

import com.libra.stockanalysisi.bean.NetFileData;
import com.libra.stockanalysisi.engine.BaseStockInfoCallBack;
import com.libra.stockanalysisi.engine.IAllStockIDService;
import com.libra.stockanalysisi.engine.IDataSyncService;
import com.libra.stockanalysisi.engine.IHolidayService;
import com.libra.stockanalysisi.engine.IStockInfoService;
import com.libra.stockanalysisi.engine.ITimeService;
import com.libra.stockanalysisi.engine.StockInfoCallBack;
import com.libra.stockanalysisi.engine.IDataSyncService.AllNetFilesCallback;

class DataNetService implements IAllStockIDService,IStockInfoService,IHolidayService,ITimeService,IDataSyncService{
	
	private IAllStockIDService m_AllStockID;
	
	private IStockInfoService m_StockInfo;
	
	private IHolidayService m_HolidayService;
	
	private ITimeService m_TimeService;
	
	private IDataSyncService m_DataSyncService;

	DataNetService(Context pContext){
		super();
		m_AllStockID = new EastRichesAllStockIdServiceImpl();
		m_StockInfo = new FreeStockInfoServiceImpl();
		m_HolidayService = new EasyBotHolidayServiceImpl();
		m_TimeService = new BeijingTimeSeviceImpl();
		m_DataSyncService = new BmobDataSyncServiceImpl(pContext);
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

	@Override
	public boolean isDealTime() throws IOException {
		// TODO Auto-generated method stub
		return m_TimeService.isDealTime();
	}

	@Override
	public void uploadFile(File pfile, AsyncFileCallback pCallback) {
		// TODO Auto-generated method stub
		m_DataSyncService.uploadFile(pfile, pCallback);
	}

	@Override
	public void downFile(String pUrl, AsyncFileCallback pCallback) {
		// TODO Auto-generated method stub
		m_DataSyncService.downFile(pUrl, pCallback);
	}

	@Override
	public void requestNetFiles(Date pBeginDate, Date pEndDate, final AllNetFilesCallback pCallback) {
		// TODO Auto-generated method stub
		m_DataSyncService.requestNetFiles(pBeginDate, pEndDate, pCallback);
	}

	@Override
	public void uploadNetFilesInfo(NetFileData pNetFileData,
			AsyncFileCallback pCallback) {
		// TODO Auto-generated method stub
		m_DataSyncService.uploadNetFilesInfo(pNetFileData, pCallback);
	}
}
