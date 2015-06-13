package com.libra.stockanalysisi.engine.serialization;

import java.io.IOException;

import com.libra.stockanalysisi.bean.Stock;

public interface IStockInfoSerializtion {
	
	Stock deserializationStockInfo(String pData) throws IOException;
	
	String serializationStockInfo(Stock pStock);
}
