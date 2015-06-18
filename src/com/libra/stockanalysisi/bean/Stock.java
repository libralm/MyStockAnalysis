package com.libra.stockanalysisi.bean;

import java.util.Date;

public class Stock extends BaseStock {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4173556025373242718L;
	private Double m_TodayStartPri; /* 今日开盘价 */
	private Double m_YestodEndPri; /* 昨日收盘价 */
	private Double m_NowPri; /* 当前价格 */
	private Double m_TodayMax; /* 今日最高价 */
	private Double m_TodayMin; /* 今日最低价 */
	private Double m_CompetitivePri; /* 竞买价 */
	private Double m_ReservePri; /* 竞卖价 */
	private Integer m_TraNumber; /* 成交量 */
	private Integer m_TraAmount; /* 成交金额 */
	// 2012-12-11
	private Date m_Date; /* 日期 */

	public Double getTodayStartPri() {
		return m_TodayStartPri;
	}

	public void setTodayStartPri(Double pTodayStartPri) {
		this.m_TodayStartPri = pTodayStartPri;
	}

	public Double getYestodEndPri() {
		return m_YestodEndPri;
	}

	public void setYestodEndPri(Double pYestodEndPri) {
		this.m_YestodEndPri = pYestodEndPri;
	}

	public Double getNowPri() {
		return m_NowPri;
	}

	public void setNowPri(Double pNowPri) {
		this.m_NowPri = pNowPri;
	}

	public Double getTodayMax() {
		return m_TodayMax;
	}

	public void setTodayMax(Double pTodayMax) {
		this.m_TodayMax = pTodayMax;
	}

	public Double getTodayMin() {
		return m_TodayMin;
	}

	public void setTodayMin(Double pTodayMin) {
		this.m_TodayMin = pTodayMin;
	}

	public Double getCompetitivePri() {
		return m_CompetitivePri;
	}

	public void setCompetitivePri(Double pCompetitivePri) {
		this.m_CompetitivePri = pCompetitivePri;
	}

	public Double getReservePri() {
		return m_ReservePri;
	}

	public void setReservePri(Double pReservePri) {
		this.m_ReservePri = pReservePri;
	}

	public Integer getTraNumber() {
		return m_TraNumber;
	}

	public void setTraNumber(Integer pTraNumber) {
		this.m_TraNumber = pTraNumber;
	}

	public Integer getTraAmount() {
		return m_TraAmount;
	}

	public void setTraAmount(Integer pTraAmount) {
		this.m_TraAmount = pTraAmount;
	}

	public Date getDate() {
		return m_Date;
	}

	public void setDate(Date pDate) {
		this.m_Date = pDate;
	}

	public Stock(String m_Gid, String m_Name, Double m_TodayStartPri,
			Double m_YestodEndPri, Double m_NowPri, Double m_TodayMax,
			Double m_TodayMin, Double m_CompetitivePri, Double m_ReservePri,
			Integer m_TraNumber, Integer m_TraAmount, Date m_Date) {
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
