package com.libra.stockanalysisi.engine;

import cn.bmob.v3.BmobUser;

import com.libra.stockanalysisi.bean.User;

public interface IUserManagerService {

	/**
	 * 用户注册
	 * @param pUserName
	 * @param pPassword
	 * @param pEmail
	 * @param pCallback
	 */
	void regiester(String pUserName,String pPassword,String pEmail, NetDataCallback pCallback);
	
	/**
	 * 用户登录
	 * @param pAccount
	 * @param pPassword
	 * @param pCallback
	 */
	void login(String pAccount,String pPassword, NetDataCallback pCallback);
	
	/**
	 * 手机加短信验证码登录
	 * @param pPhoneNum
	 * @param pSmscode
	 * @param pCallback
	 */
	void loginBySmsCode(String pPhoneNum, String pSmscode, NetDataCallback pCallback);
	
	/**
	 * 得到当前用户
	 * @return 
	 */
	BmobUser getCurrentUser();
	
	/**
	 * 退出登录
	 * @param pCallback
	 */
	void logout(NetDataCallback pCallback);
	
	/**
	 * 更新用户信息
	 * @param pCallback
	 */
	void updateUserInfo(User pUser, NetDataCallback pCallback);
	
	/**
	 * 查询用户
	 * @param pUser
	 * @param pCallback
	 */
	void queryUser(User pUser, NetDataCallback pCallback);
	
	/**
	 * 通过电子邮件重置密码
	 * @param pCallback
	 */
	void resetPasswordByEmail(String pEmail, NetDataCallback pCallback);
	
	/**
	 * 通过电子邮件验证
	 * @param pEmail
	 * @param pCallback
	 */
	void requestEmailVerify(String pEmail, NetDataCallback pCallback);
	
	/**
	 * 请求短信验证码
	 * @param pPhoneNum
	 * @param pCallback
	 */
	void requestSMSCode(String pPhoneNum, NetDataCallback pCallback);
	
	/**
	 * 验证验证码的合法性
	 * @param pPhoneNum
	 * @param pSMSCode
	 * @param pCallback
	 */
	void verifySmsCode(String pPhoneNum,String pSMSCode, NetDataCallback pCallback);
	
	/**
	 * 通过手机号码重置密码
	 * @param phoneNum
	 * @param pCallback
	 */
	void resetPasswordByPhone(String phoneNum, NetDataCallback pCallback);
	
	/**
	 * 绑定手机号码
	 * @param pPhoneNum
	 * @param pSmsCode
	 * @param pCallback
	 */
	void bindingPhoneNum(String pPhoneNum,String pSmsCode, NetDataCallback pCallback);
	
}
