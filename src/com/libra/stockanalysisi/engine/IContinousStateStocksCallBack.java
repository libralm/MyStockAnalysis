package com.libra.stockanalysisi.engine;

import com.libra.stockanalysisi.bean.Stock;

/**
 * 持续状态计算后的回调。
 * @author liaomin
 *
 */
public interface IContinousStateStocksCallBack {

	void continusStatesStocks(Stock[] result);
	
	void onFailure(Throwable pThrowable);
	
	void onProgressInfo(String pMsg);
}
