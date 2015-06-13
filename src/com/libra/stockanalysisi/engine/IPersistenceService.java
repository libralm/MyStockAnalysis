package com.libra.stockanalysisi.engine;

import java.util.Date;

import com.libra.stockanalysisi.bean.BaseStock;
import com.libra.stockanalysisi.bean.Stock;

public interface IPersistenceService {

	void saveAllBaseStockInfo(BaseStock[] pStocks);
	
	void saveAllStocksDetailInfo(Stock[] pStocks);
	
	BaseStock[] readAllBaseStockInfo();
	
	Stock[] readAllStocksDetailInfo(Date pStockDate);
}
