package com.libra.stockanalysisi.engine;

public interface UserManagerCallback{
	
	void onSuccess();
	
	void onFailure(int pCode, String pMsg);
}