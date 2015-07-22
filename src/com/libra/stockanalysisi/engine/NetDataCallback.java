package com.libra.stockanalysisi.engine;

public interface NetDataCallback{
	
	/**
	 * 数据成功
	 */
	void onSuccess();
	
	/**
	 * 失败
	 * @param pCode
	 * @param pMsg
	 */
	void onFailure(int pCode, String pMsg);
	
	/**
	 * 无需请求网络
	 */
	void onSkip();
}