package com.libra.stockanalysisi.engine.impl;

import java.util.Date;

import android.os.AsyncTask;

import com.libra.stockanalysisi.bean.BaseStock;
import com.libra.stockanalysisi.bean.Stock;
import com.libra.stockanalysisi.engine.IPersistenceService;

class BmobPersistenceServiceImpl implements IPersistenceService {
	
	

	@Override
	public void saveAllBaseStockInfo(BaseStock[] pStocks) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveAllStocksDetailInfo(Stock[] pStocks) {
		// TODO Auto-generated method stub
		new AsyncTask<Stock[], Void, Void>() {

			@Override
			protected Void doInBackground(Stock[]... params) {
				// TODO Auto-generated method stub
				
				return null;
			}
		}.execute(pStocks);
	}

	@Override
	public BaseStock[] readAllBaseStockInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stock[] readAllStocksDetailInfo(Date pStockDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
