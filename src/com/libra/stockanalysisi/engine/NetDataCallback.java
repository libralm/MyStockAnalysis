package com.libra.stockanalysisi.engine;

public interface NetDataCallback{
	
	void onSuccess();
	
	void onFailure(int pCode, String pMsg);
}