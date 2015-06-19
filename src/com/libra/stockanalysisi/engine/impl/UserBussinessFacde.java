package com.libra.stockanalysisi.engine.impl;

import android.content.Context;

import com.libra.stockanalysisi.bean.User;
import com.libra.stockanalysisi.engine.FacdeService;
import com.libra.stockanalysisi.engine.IUserManagerService;
import com.libra.stockanalysisi.engine.UserManagerCallback;

public class UserBussinessFacde implements IUserManagerService, FacdeService{
	
	private IUserManagerService m_UserManagerService;
	
	public UserBussinessFacde(Context pContext){
		m_UserManagerService = new BmobUserManagerServiceImpl(pContext);
	}

	@Override
	public void regiester(String pUserName, String pPassword, String email,
			UserManagerCallback pCallback) {
		// TODO Auto-generated method stub
		m_UserManagerService.regiester(pUserName, pPassword, email, pCallback);
	}

	@Override
	public void loginBySmsCode(int pPhoneNum, String pSmscode,
			UserManagerCallback pCallback) {
		// TODO Auto-generated method stub
		m_UserManagerService.loginBySmsCode(pPhoneNum, pSmscode, pCallback);
	}

	@Override
	public User getCurrentUser() {
		// TODO Auto-generated method stub
		return m_UserManagerService.getCurrentUser();
	}

	@Override
	public void logout(UserManagerCallback pCallback) {
		// TODO Auto-generated method stub
		m_UserManagerService.logout(pCallback);
	}

	@Override
	public void updateUserInfo(User pUser, UserManagerCallback pCallback) {
		// TODO Auto-generated method stub
		m_UserManagerService.updateUserInfo(pUser, pCallback);
	}

	@Override
	public void queryUser(User pUser, UserManagerCallback pCallback) {
		// TODO Auto-generated method stub
		m_UserManagerService.queryUser(pUser, pCallback);
	}

	@Override
	public void resetPasswordByEmail(String pEmail,
			UserManagerCallback pCallback) {
		// TODO Auto-generated method stub
		m_UserManagerService.resetPasswordByEmail(pEmail, pCallback);
	}

	@Override
	public void requestEmailVerify(String pEmail, UserManagerCallback pCallback) {
		// TODO Auto-generated method stub
		m_UserManagerService.requestEmailVerify(pEmail, pCallback);
	}

	@Override
	public void requestSMSCode(int pPhoneNum, UserManagerCallback pCallback) {
		// TODO Auto-generated method stub
		m_UserManagerService.requestSMSCode(pPhoneNum, pCallback);
	}

	@Override
	public void verifySmsCode(int pPhoneNum, String pSMSCode,
			UserManagerCallback pCallback) {
		// TODO Auto-generated method stub
		m_UserManagerService.verifySmsCode(pPhoneNum, pSMSCode, pCallback);
	}

	@Override
	public void resetPasswordByPhone(int phoneNum, UserManagerCallback pCallback) {
		// TODO Auto-generated method stub
		m_UserManagerService.resetPasswordByPhone(phoneNum, pCallback);
	}

	@Override
	public void bindingPhoneNum(int pPhoneNum, String pSmsCode,
			UserManagerCallback pCallback) {
		// TODO Auto-generated method stub
		m_UserManagerService.bindingPhoneNum(pPhoneNum, pSmsCode, pCallback);
	}

	@Override
	public void login(String pAccount, String pPassword,
			UserManagerCallback pCallback) {
		// TODO Auto-generated method stub
		m_UserManagerService.login(pAccount, pPassword, pCallback);
	}
}
