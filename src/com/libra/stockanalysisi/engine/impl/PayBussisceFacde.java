package com.libra.stockanalysisi.engine.impl;

import android.app.Activity;

import com.libra.stockanalysisi.engine.FacdeService;
import com.libra.stockanalysisi.engine.pay.IPayListener;
import com.libra.stockanalysisi.engine.pay.IPayService;
import com.libra.stockanalysisi.engine.pay.impl.BmobPayServiceImpl;

public class PayBussisceFacde implements FacdeService{
	
	private IPayService m_PayService;
	
	public PayBussisceFacde(Activity pActivity){
		m_PayService = new BmobPayServiceImpl(pActivity);
	}
	
	public void payZhifubao(IPayListener pPayListener) {
		m_PayService.payZhifubao(pPayListener);
	}

	public void payWeixin(IPayListener pPayListener) {
		m_PayService.payWeixin(pPayListener);
	}
}
