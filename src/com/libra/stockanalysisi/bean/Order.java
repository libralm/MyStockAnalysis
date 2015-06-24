package com.libra.stockanalysisi.bean;

public class Order {
	
	/**
	 * 订单或商品名称
	 */
	private String m_Name;
	
	/**
	 * 订单的总金额
	 */
	private double m_Price;
	
	/**
	 * 商品详情
	 */
	private String m_Detail;
	
	/**
	 * 订单号
	 */
	private String m_OrderId;
	
	/**
	 * 支付创建时间
	 */
	private String m_CreateTime;
	
	/**
	 * 支付类型
	 */
	private String m_PayType;
	
	/**
	 * 支付状态
	 */
	private String m_TradeState;

	public String getName() {
		return m_Name;
	}

	public void setName(String pName) {
		this.m_Name = pName;
	}

	public double getPrice() {
		return m_Price;
	}

	public void setPrice(double pPrice) {
		this.m_Price = pPrice;
	}

	public String getDetail() {
		return m_Detail;
	}

	public void setDetail(String pDetail) {
		this.m_Detail = pDetail;
	}

	public String getOrderId() {
		return m_OrderId;
	}

	public void setOrderId(String pOrderId) {
		this.m_OrderId = pOrderId;
	}

	public String getCreateTime() {
		return m_CreateTime;
	}

	public void setCreateTime(String pCreateTime) {
		this.m_CreateTime = pCreateTime;
	}

	public String getPayType() {
		return m_PayType;
	}

	public void setPayType(String pPayType) {
		this.m_PayType = pPayType;
	}

	public String getTradeState() {
		return m_TradeState;
	}

	public void setTradeState(String pTradeState) {
		this.m_TradeState = pTradeState;
	}
	
	
}
