package com.libra.stockanalysisi.engine.serialization;

import com.libra.stockanalysisi.bean.BaseStock;

public interface IAllStockSerialization {

	BaseStock[] deserializationStockInfo(String pData);
}
