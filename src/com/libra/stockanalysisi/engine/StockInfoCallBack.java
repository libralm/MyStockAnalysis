package com.libra.stockanalysisi.engine;

import com.libra.stockanalysisi.bean.Stock;

public interface StockInfoCallBack {
	
	void onSuccess(Stock pStock);
	
	void onFinish();
	
	void onFailure(Throwable pThrowable);
}
