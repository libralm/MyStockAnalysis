package com.libra.stockanalysisi.engine;

import com.libra.stockanalysisi.bean.BaseStock;

public interface IContinousFallingStocksCallBack {

	void continusFallingStocks(BaseStock[] result);
	
	void onFailure(Throwable pThrowable);
}
