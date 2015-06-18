package com.libra.stockanalysisi.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class BaseStock extends BmobObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2854914187926009369L;

	protected String m_Gid; /* 股票编号 */

	protected String m_Name; /* 股票名称 */

	public String getGid() {
		return m_Gid;
	}

	public void setGid(String pGid) {
		this.m_Gid = pGid;
	}

	public String getName() {
		return m_Name;
	}

	public void setName(String pName) {
		this.m_Name = pName;
	}
}
