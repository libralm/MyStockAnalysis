package com.libra.stockanalysisi.control;

import android.content.Context;
import android.widget.Toast;

import com.libra.stockanalysisi.engine.NetDataCallback;
import com.libra.stockanalysisi.engine.impl.UserBussinessFacde;

public class UserManagerControl {
	
	private UserBussinessFacde m_Facde;
	
	private Context m_Context;

	public UserManagerControl(Context pContext,UserBussinessFacde pFacde){
		m_Facde = pFacde;
		m_Context = pContext;
	}
	
	/**
	 * 绑定手机号
	 * @param pPhoneNum
	 * @param pSmsCode
	 */
	public void bingPhoneNum(String pPhoneNum,String pSmsCode) {
		m_Facde.bindingPhoneNum(pPhoneNum, pPhoneNum, new NetDataCallback() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(m_Context, "绑定成功", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onFailure(int pCode, String pMsg) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void requestSMSCode(String phonenum) {
		// TODO Auto-generated method stub
		m_Facde.requestSMSCode(phonenum, new NetDataCallback() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(m_Context, "请求验证码成功", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onFailure(int pCode, String pMsg) {
				// TODO Auto-generated method stub
				Toast.makeText(m_Context, pMsg, Toast.LENGTH_SHORT).show();
			}
		});
	}


	public void xiugaimima(String email) {
		// TODO Auto-generated method stub
		m_Facde.resetPasswordByEmail(email, new NetDataCallback() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(m_Context, "", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onFailure(int pCode, String pMsg) {
				// TODO Auto-generated method stub
				Toast.makeText(m_Context, pMsg, Toast.LENGTH_SHORT).show();
			}
		});
	}


	public void login(String username, String psd) {
		// TODO Auto-generated method stub
		m_Facde.login(username, psd, new NetDataCallback() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(m_Context, "登陆成功", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onFailure(int pCode, String pMsg) {
				// TODO Auto-generated method stub
				Toast.makeText(m_Context, pMsg, Toast.LENGTH_SHORT).show();

			}
		});
	}


	public void register(String pUsername, String pPSD,String pEmail) {
		m_Facde.regiester(pUsername, pPSD, pEmail, new NetDataCallback() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(m_Context, "注册成功", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onFailure(int pCode, String pMsg) {
				// TODO Auto-generated method stub
				Toast.makeText(m_Context, "注册失败", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
