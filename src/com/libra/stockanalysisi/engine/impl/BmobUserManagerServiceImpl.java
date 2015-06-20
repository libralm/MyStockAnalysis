package com.libra.stockanalysisi.engine.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;

import android.content.Context;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.EmailVerifyListener;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.ResetPasswordByEmailListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

import com.libra.stockanalysisi.bean.User;
import com.libra.stockanalysisi.engine.IUserManagerService;
import com.libra.stockanalysisi.engine.NetDataCallback;

/**
 * Bmob用户管理服务类实现
 * @author liaomin
 *
 */
class BmobUserManagerServiceImpl implements IUserManagerService {

	private Context m_Context;

	public BmobUserManagerServiceImpl(Context pContext) {
		m_Context = pContext;
	}

	@Override
	public void regiester(String pUserName, String pPassword, String email,
			final NetDataCallback pCallback) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setUsername(pUserName);
		user.setPassword(pPassword);
		user.setEmail(email);
		user.signUp(m_Context, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				pCallback.onSuccess();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				pCallback.onFailure(arg0, arg1);
			}
		});
	}

	@Override
	public void login(String pAccount, String pPassword,
			final NetDataCallback pCallback) {
		// TODO Auto-generated method stub
		if(isEmailFormat(pAccount)){			
			loginByEmail(pAccount,pPassword,pCallback);
		} else if(isPhoneNum(pAccount)){			
			loginByPhoneNum(pAccount,pPassword,pCallback);
		} else{			
			loginByUserName(pAccount,pPassword,pCallback);
		}
		
	}

	private void loginByUserName(String pAccount, String pPassword,
			final NetDataCallback pCallback) {
		// TODO Auto-generated method stub
		User bu2 = new User();
		bu2.setUsername(pAccount);
		bu2.setPassword(pPassword);
		bu2.login(m_Context, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				pCallback.onSuccess();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				pCallback.onFailure(arg0, arg1);
			}
		});
	}

	private void loginByPhoneNum(String pAccount, String pPassword,
			final NetDataCallback pCallback) {
		// TODO Auto-generated method stub
		User.loginByAccount(m_Context, pAccount, pPassword, new LogInListener<User>() {

		    @Override
		    public void done(User user, BmobException e) {
		        // TODO Auto-generated method stub
		    	if(user != null){
					pCallback.onSuccess();
				} else{
					pCallback.onFailure(e.getErrorCode(), e.getLocalizedMessage());
				}
		    }
		});
	}

	private void loginByEmail(String pAccount, String pPassword,
			final NetDataCallback pCallback) {
		// TODO Auto-generated method stub
		User.loginByAccount(m_Context, pAccount, pPassword, new LogInListener<User>() {

			@Override
			public void done(User arg0, BmobException arg1) {
				// TODO Auto-generated method stub
				if(arg0 != null){
					pCallback.onSuccess();
				} else{
					pCallback.onFailure(arg1.getErrorCode(), arg1.getMessage());
				}
			}
		});
	}

	private boolean isEmailFormat(String pAccount) {
		Pattern pattern = Pattern
				.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(pAccount);
		return matcher.matches();
	}

	private boolean isPhoneNum(String pPhoneNum){
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
		Matcher m = p.matcher(pPhoneNum);
		return m.matches();
	}

	@Override
	public void loginBySmsCode(int pPhoneNum, String pSmscode,
			final NetDataCallback pCallback) {
		// TODO Auto-generated method stub
		BmobUser.loginBySMSCode(m_Context, pPhoneNum+"", pSmscode, new LogInListener<User>() {

	        @Override
	        public void done(User user, BmobException e) {
	            // TODO Auto-generated method stub
	        	if(user != null){
					pCallback.onSuccess();
				} else{
					pCallback.onFailure(e.getErrorCode(), e.getMessage());
				}
	        }
	    });
	}

	@Override
	public User getCurrentUser() {
		// TODO Auto-generated method stub
		return (User) User.getCurrentUser(m_Context);
	}

	@Override
	public void logout(NetDataCallback pCallback) {
		// TODO Auto-generated method stub
		BmobUser.logOut(m_Context);   //清除缓存用户对象
		BmobUser currentUser = BmobUser.getCurrentUser(m_Context);
		if(currentUser == null){
			pCallback.onSuccess();
		}
	}

	@Override
	public void updateUserInfo(User pUser, final NetDataCallback pCallback) {
		// TODO Auto-generated method stub
		BmobUser bmobUser = BmobUser.getCurrentUser(m_Context);
		pUser.update(m_Context,bmobUser.getObjectId(),new UpdateListener() {
		    @Override
		    public void onSuccess() {
		        // TODO Auto-generated method stub
		    	pCallback.onSuccess();
		    }
		    @Override
		    public void onFailure(int code, String msg) {
		        // TODO Auto-generated method stub
		    	pCallback.onFailure(code, msg);
		    }
		});
	}

	@Override
	public void queryUser(User pUser, final NetDataCallback pCallback) {
		// TODO Auto-generated method stub
		BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
		query.addWhereEqualTo("username", pUser.getUsername());
		query.findObjects(m_Context, new FindCallback() {
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				pCallback.onFailure(arg0, arg1);
			}
			
			@Override
			public void onSuccess(JSONArray arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void resetPasswordByEmail(String pEmail, final NetDataCallback pCallback) {
		// TODO Auto-generated method stub
		BmobUser.resetPasswordByEmail(m_Context, pEmail,new ResetPasswordByEmailListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				pCallback.onSuccess();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				pCallback.onFailure(arg0, arg1);
			}
		});
	}

	@Override
	public void requestEmailVerify(String pEmail, final NetDataCallback pCallback) {
		// TODO Auto-generated method stub
		BmobUser.requestEmailVerify(m_Context, pEmail, new EmailVerifyListener() {
		    @Override
		    public void onSuccess() {
		        // TODO Auto-generated method stub
		    	pCallback.onSuccess();
		    }
		    @Override
		    public void onFailure(int code, String e) {
		        // TODO Auto-generated method stub
		    	pCallback.onFailure(code, e);
		    }
		});
	}

	@Override
	public void requestSMSCode(int pPhoneNum, final NetDataCallback pCallback) {
		// TODO Auto-generated method stub
		BmobSMS.requestSMSCode(m_Context, pPhoneNum+"", "股票抄底", new RequestSMSCodeListener() {
			
			@Override
			public void done(Integer arg0, BmobException arg1) {
				// TODO Auto-generated method stub
				if(arg0 != null){
					pCallback.onSuccess();
				} else{
					pCallback.onFailure(arg1.getErrorCode(), arg1.getMessage());
				}
			}
		});
	}

	@Override
	public void verifySmsCode(int pPhoneNum, String pSMSCode,
			final NetDataCallback pCallback) {
		// TODO Auto-generated method stub
		BmobSMS.verifySmsCode(m_Context, pPhoneNum+"", pSMSCode, new VerifySMSCodeListener() {

		    @Override
		    public void done(BmobException ex) {
		        // TODO Auto-generated method stub
		        if(ex==null){//短信验证码已验证成功
		            pCallback.onSuccess();
		        }else{
		        	pCallback.onFailure(ex.getErrorCode(), ex.getLocalizedMessage());
		        }
		    }
		});
	}

	@Override
	public void resetPasswordByPhone(int phoneNum, final NetDataCallback pCallback) {
		// TODO Auto-generated method stub
		BmobSMS.requestSMSCode(m_Context, phoneNum+"","股票抄底", new RequestSMSCodeListener() {
			
			@Override
			public void done(Integer arg0, BmobException arg1) {
				// TODO Auto-generated method stub
				if(arg1 == null){
					pCallback.onSuccess();
				} else{
					pCallback.onFailure(arg0, arg1.getLocalizedMessage());
				}
			}
		});
	}

	@Override
	public void bindingPhoneNum(final int pPhoneNum, String pSmsCode,
			final NetDataCallback pCallback) {
		// TODO Auto-generated method stub
		verifySmsCode(pPhoneNum, pSmsCode, new NetDataCallback() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				User user =new User();
				user.setMobilePhoneNumber(pPhoneNum+"");
				user.setMobilePhoneNumberVerified(true);
				User cur = BmobUser.getCurrentUser(m_Context,User.class);
				user.update(m_Context, cur.getObjectId(),new UpdateListener() {

				    @Override
				    public void onSuccess() {
				        // TODO Auto-generated method stub
				    	pCallback.onSuccess();
				    }

				    @Override
				    public void onFailure(int arg0, String arg1) {
				        // TODO Auto-generated method stub
				    	pCallback.onFailure(arg0, arg1);
				    }
				});
			}
			
			@Override
			public void onFailure(int pCode, String pMsg) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
