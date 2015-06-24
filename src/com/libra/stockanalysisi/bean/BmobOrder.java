package com.libra.stockanalysisi.bean;

public class BmobOrder extends Order {

	/**
	 * Bmob系统订单号
	 */
	private String m_OutTradeNo;

	public String getOutTradeNo() {
		return m_OutTradeNo;
	}

	public void setOutTradeNo(String pOutTradeNo) {
		this.m_OutTradeNo = pOutTradeNo;
	}
	
	
}
