package com.libra.stockanalysisi.engine.pay.impl;

import android.app.Activity;

import com.bmob.pay.tool.BmobPay;
import com.bmob.pay.tool.PayListener;
import com.libra.stockanalysisi.engine.pay.IPayListener;
import com.libra.stockanalysisi.engine.pay.IPayService;

public class BmobPayServiceImpl implements IPayService {

	private final String APP_ID = "a4622d0cf8aa4831957757e871f0deba";

	private BmobPay m_BmobPay;

	private final String NAME = "股票抄底";

	private final int PRICE = 2;

	public BmobPayServiceImpl(Activity pActivity) {
		BmobPay.init(pActivity, APP_ID);
		m_BmobPay = new BmobPay(pActivity);
	}

	@Override
	public void payZhifubao(final IPayListener pPayListener) {
		// TODO Auto-generated method stub
		m_BmobPay.pay(PRICE, NAME, null, new PayListener() {

			// 因为网络等原因,支付结果未知(小概率事件),出于保险起见稍后手动查询
			@Override
			public void unknow() {
				pPayListener.unknow();
			}

			// 支付成功,如果金额较大请手动查询确认
			@Override
			public void succeed() {
				pPayListener.succeed();
			}

			// 无论成功与否,返回订单号
			@Override
			public void orderId(String orderId) {
				// 此处应该保存订单号,比如保存进数据库等,以便以后查询
				pPayListener.orderId(orderId);
			}

			// 支付失败,原因可能是用户中断支付操作,也可能是网络原因
			@Override
			public void fail(int code, String reason) {
				pPayListener.fail(code, reason);
			}
		});
	}

	@Override
	public void payWeixin(final IPayListener pPayListener) {
		// TODO Auto-generated method stub
		m_BmobPay.payByWX(PRICE, NAME, new PayListener() {

			// 因为网络等原因,支付结果未知(小概率事件),出于保险起见稍后手动查询
			@Override
			public void unknow() {
				pPayListener.unknow();
			}

			// 支付成功,如果金额较大请手动查询确认
			@Override
			public void succeed() {
				pPayListener.succeed();
			}

			// 无论成功与否,返回订单号
			@Override
			public void orderId(String orderId) {
				// 此处应该保存订单号,比如保存进数据库等,以便以后查询
				pPayListener.orderId(orderId);
			}

			// 支付失败,原因可能是用户中断支付操作,也可能是网络原因
			@Override
			public void fail(int code, String reason) {
				pPayListener.fail(code, reason);
			}
		});
	}

}
