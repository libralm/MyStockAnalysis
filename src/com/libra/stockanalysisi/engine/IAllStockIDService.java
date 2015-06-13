package com.libra.stockanalysisi.engine;

import java.io.IOException;

public interface IAllStockIDService {
	
	/**
	 * 查询所有股票的基本信息
	 * @param pCallBack
	 * @throws IOException
	 */
	void queryAllStockID(BaseStockInfoCallBack pCallBack) throws IOException;
}
