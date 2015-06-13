package com.libra.stockanalysisi.engine;


public interface IStockInfoService {
	
	/**
	 * 根据股票代码查询当日基本信息。
	 * @param pStockID
	 * @param pCallBack
	 */
	void queryStock(String pStockID,StockInfoCallBack pCallBack);
}
