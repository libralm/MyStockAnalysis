package com.libra.stockanalysisi.engine;

import com.libra.stockanalysisi.bean.Stock;

public interface IContinousStateStocksCallBack {

	void continusFallingStocks(Stock[] result);
	
	void onFailure(Throwable pThrowable);
}
