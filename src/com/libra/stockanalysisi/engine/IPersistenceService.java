package com.libra.stockanalysisi.engine;

import java.io.FileNotFoundException;
import java.util.Date;

import com.libra.stockanalysisi.bean.BaseStock;
import com.libra.stockanalysisi.bean.Stock;

public interface IPersistenceService {
	
	public static final String M_DIRNAME = "Stock";

	void saveAllBaseStockInfo(BaseStock[] pStocks);
	
	void saveAllStocksDetailInfo(Stock[] pStocks);
	
	BaseStock[] readAllBaseStockInfo();
	
	Stock[] readAllStocksDetailInfo(Date pStockDate) throws FileNotFoundException;
}
