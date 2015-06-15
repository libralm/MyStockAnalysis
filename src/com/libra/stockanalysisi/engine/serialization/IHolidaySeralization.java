package com.libra.stockanalysisi.engine.serialization;

import com.libra.stockanalysisi.bean.BaseStock;
import com.libra.stockanalysisi.bean.Holiday;

public interface IHolidaySeralization {

	Holiday deserializationStockInfo(String pData);
}
