package com.libra.stockanalysisi.engine.impl;

import android.app.Activity;

import com.libra.stockanalysisi.engine.FacdeService;

/**
 * 为整个应用的服务封装。界面层开发者以这个类作为初始入口。
 * @author liaomin
 *
 */
public class AppBussinessFacdeService {

	public static final int USER_FACDE_SERVICE = 0;
	
	public static final int STOCK_FACDE_SERVICE = 1;
	
	private StockBussisceFacde m_StockBussisceFacde;
	
	private UserBussinessFacde m_UserBussinessFacde;
	
	public AppBussinessFacdeService(Activity pContext){
		m_StockBussisceFacde = new StockBussisceFacde(pContext);
		m_UserBussinessFacde = new UserBussinessFacde(pContext);
	}
	
	public FacdeService getFacdeService(int pFacdeServiceName){
		switch (pFacdeServiceName) {
		case USER_FACDE_SERVICE:
			return m_UserBussinessFacde;
		case STOCK_FACDE_SERVICE:
			return m_StockBussisceFacde;
		default:
			break;
		}
		return null;
	}
}
