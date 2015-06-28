package com.libra.stockanalysisi.bean;

import java.util.Date;

public class Stock extends BaseStock {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4173556025373242718L;
	private double m_TodayStartPri; /* 今日开盘价 */
	private double m_YestodEndPri; /* 昨日收盘价 */
	private double m_NowPri; /* 当前价格 */
	private double m_TodayMax; /* 今日最高价 */
	private double m_TodayMin; /* 今日最低价 */
	private double m_CompetitivePri; /* 竞买价 */
	private double m_ReservePri; /* 竞卖价 */
	private Double m_TraNumber; /* 成交量 */
	private Double m_TraAmount; /* 成交金额 */
	// 2012-12-11
	private Date m_Date; /* 日期 */

	public double getTodayStartPri() {
		return m_TodayStartPri;
	}

	public void setTodayStartPri(double pTodayStartPri) {
		this.m_TodayStartPri = pTodayStartPri;
	}

	public double getYestodEndPri() {
		return m_YestodEndPri;
	}

	public void setYestodEndPri(double pYestodEndPri) {
		this.m_YestodEndPri = pYestodEndPri;
	}

	public double getNowPri() {
		return m_NowPri;
	}

	public void setNowPri(double pNowPri) {
		this.m_NowPri = pNowPri;
	}

	public double getTodayMax() {
		return m_TodayMax;
	}

	public void setTodayMax(double pTodayMax) {
		this.m_TodayMax = pTodayMax;
	}

	public double getTodayMin() {
		return m_TodayMin;
	}

	public void setTodayMin(double pTodayMin) {
		this.m_TodayMin = pTodayMin;
	}

	public double getCompetitivePri() {
		return m_CompetitivePri;
	}

	public void setCompetitivePri(double pCompetitivePri) {
		this.m_CompetitivePri = pCompetitivePri;
	}

	public double getReservePri() {
		return m_ReservePri;
	}

	public void setReservePri(double pReservePri) {
		this.m_ReservePri = pReservePri;
	}

	public Double getTraNumber() {
		return m_TraNumber;
	}

	public void setTraNumber(Double pTraNumber) {
		this.m_TraNumber = pTraNumber;
	}

	public Double getTraAmount() {
		return m_TraAmount;
	}

	public void setTraAmount(Double pTraAmount) {
		this.m_TraAmount = pTraAmount;
	}

	public Date getDate() {
		return m_Date;
	}

	public void setDate(Date pDate) {
		this.m_Date = pDate;
	}

	public Stock(String m_Gid, String m_Name, double m_TodayStartPri,
			double m_YestodEndPri, double m_NowPri, double m_TodayMax,
			double m_TodayMin, double m_CompetitivePri, double m_ReservePri,
			Double m_TraNumber, Double m_TraAmount, Date m_Date) {
		super();
		this.m_Gid = m_Gid;
		this.m_Name = m_Name;
		this.m_TodayStartPri = m_TodayStartPri;
		this.m_YestodEndPri = m_YestodEndPri;
		this.m_NowPri = m_NowPri;
		this.m_TodayMax = m_TodayMax;
		this.m_TodayMin = m_TodayMin;
		this.m_CompetitivePri = m_CompetitivePri;
		this.m_ReservePri = m_ReservePri;
		this.m_TraNumber = m_TraNumber;
		this.m_TraAmount = m_TraAmount;
		this.m_Date = m_Date;
	}

}
