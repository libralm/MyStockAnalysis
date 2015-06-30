package com.libra.stockanalysisi.engine.pay;


public interface IPayService {
	
	/**
	 * 支付宝支付
	 * @param pPayListener
	 */
	void payZhifubao(IPayListener pPayListener);
	
	/**
	 * 微信支付
	 * @param pPayListener
	 */
	void payWeixin(IPayListener pPayListener);
	
	/**
	 * 查询订单信息
	 * @param pOrderId
	 * @param pCallback
	 */
	void queryOrderInfo(String pOrderId, QueryOrderInfoCallback pCallback);
	
}
