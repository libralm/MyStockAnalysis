package com.libra.stockanalysisi.engine.impl;

import android.app.Activity;
import android.content.Context;

import com.libra.stockanalysisi.engine.FacdeService;

/**
 * 为整个应用的服务封装。界面层开发者以这个类作为初始入口。
 * @author liaomin
 *
 */
public class AppBussinessFacdeService {

	public static final int USER_FACDE_SERVICE = 0;
	
	public static final int STOCK_FACDE_SERVICE = 1;
	
	public static final int PAY_FACDE_SERVICE = 2;
	
	private StockBussisceFacde m_StockBussisceFacde;
	
	private UserBussinessFacde m_UserBussinessFacde;
	
	private PayBussisceFacde m_PayBussisceFacde;
	
	public AppBussinessFacdeService(Context pContext){
		m_StockBussisceFacde = new StockBussisceFacde(pContext);
		m_UserBussinessFacde = new UserBussinessFacde(pContext);
		if(pContext instanceof Activity){			
			m_PayBussisceFacde = new PayBussisceFacde((Activity)pContext);
		}
	}
	
	public FacdeService getFacdeService(int pFacdeServiceName){
		switch (pFacdeServiceName) {
		case USER_FACDE_SERVICE:
			return m_UserBussinessFacde;
		case STOCK_FACDE_SERVICE:
			return m_StockBussisceFacde;
		case PAY_FACDE_SERVICE:
			if(m_PayBussisceFacde == null){
				throw new RuntimeException("支付服务只允许在前台使用，不允许在后台操作……应传入Activity实例对AppBussinessFacdeService进行初始化");
			}
			return m_PayBussisceFacde;
		default:
			break;
		}
		return null;
	}
}
