package com.libra.stockanalysisi.engine;

import com.libra.stockanalysisi.bean.BaseStock;

public interface BaseStockInfoCallBack {

	void onSuccess(BaseStock[] pBaseStocks);
}
