package com.libra.stockanalysisi.engine;

import com.libra.stockanalysisi.bean.User;

public interface IUserManagerService {

	/**
	 * 用户注册
	 * @param pUserName
	 * @param pPassword
	 * @param email
	 * @param pCallback
	 */
	void regiester(String pUserName,String pPassword,String email, UserManagerCallback pCallback);
	
	/**
	 * 用户登录
	 * @param pAccount
	 * @param pPassword
	 * @param pCallback
	 */
	void login(String pAccount,String pPassword, UserManagerCallback pCallback);
	
	/**
	 * 手机加短信验证码登录
	 * @param pPhoneNum
	 * @param pSmscode
	 * @param pCallback
	 */
	void loginBySmsCode(int pPhoneNum, String pSmscode, UserManagerCallback pCallback);
	
	/**
	 * 得到当前用户
	 * @return 
	 */
	User getCurrentUser();
	
	/**
	 * 退出登录
	 * @param pCallback
	 */
	void logout(UserManagerCallback pCallback);
	
	/**
	 * 更新用户信息
	 * @param pCallback
	 */
	void updateUserInfo(User pUser, UserManagerCallback pCallback);
	
	/**
	 * 查询用户
	 * @param pUser
	 * @param pCallback
	 */
	void queryUser(User pUser, UserManagerCallback pCallback);
	
	/**
	 * 通过电子邮件重置密码
	 * @param pCallback
	 */
	void resetPasswordByEmail(String pEmail, UserManagerCallback pCallback);
	
	/**
	 * 通过电子邮件验证
	 * @param pEmail
	 * @param pCallback
	 */
	void requestEmailVerify(String pEmail, UserManagerCallback pCallback);
	
	/**
	 * 请求短信验证码
	 * @param pPhoneNum
	 * @param pCallback
	 */
	void requestSMSCode(int pPhoneNum, UserManagerCallback pCallback);
	
	/**
	 * 验证验证码的合法性
	 * @param pPhoneNum
	 * @param pSMSCode
	 * @param pCallback
	 */
	void verifySmsCode(int pPhoneNum,String pSMSCode, UserManagerCallback pCallback);
	
	/**
	 * 通过手机号码重置密码
	 * @param phoneNum
	 * @param pCallback
	 */
	void resetPasswordByPhone(int phoneNum, UserManagerCallback pCallback);
	
	/**
	 * 绑定手机号码
	 * @param pPhoneNum
	 * @param pSmsCode
	 * @param pCallback
	 */
	void bindingPhoneNum(int pPhoneNum,String pSmsCode, UserManagerCallback pCallback);
	
}
