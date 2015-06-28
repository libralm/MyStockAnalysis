package com.libra.stockanalysisi;

import com.libra.stockanalysisi.engine.FacdeService;
import com.libra.stockanalysisi.engine.NetDataCallback;
import com.libra.stockanalysisi.engine.impl.AppBussinessFacdeService;
import com.libra.stockanalysisi.engine.impl.UserBussinessFacde;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	
	EditText m_ETUserName,m_ETPassword;
	
	View m_BtnRegister;

	private UserBussinessFacde m_UBF;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		AppBussinessFacdeService abf = new AppBussinessFacdeService(this);
		m_UBF = (UserBussinessFacde) abf.getFacdeService(AppBussinessFacdeService.USER_FACDE_SERVICE);
		initView();
	}


	private void initView() {
		// TODO Auto-generated method stub
		m_ETPassword = (EditText) findViewById(R.id.et_password);
		m_ETUserName = (EditText) findViewById(R.id.et_username);
		m_BtnRegister = findViewById(R.id.btn_register);
		m_BtnRegister.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.btn_register:
			String username = m_ETUserName.getText().toString();
			String psd = m_ETPassword.getText().toString();
//			register(username, psd);
//			login(username,psd);
//			xiugaimima("63077217@qq.com");
//			requestSMSCode("18810812590");
			bingPhoneNum();
			break;

		default:
			break;
		}
	}


	private void bingPhoneNum() {
		m_UBF.bindingPhoneNum("18810812590", "127017", new NetDataCallback() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onFailure(int pCode, String pMsg) {
				// TODO Auto-generated method stub
				
			}
		});
	}


	private void requestSMSCode(String phonenum) {
		// TODO Auto-generated method stub
		m_UBF.requestSMSCode(phonenum, new NetDataCallback() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, "请求验证码成功", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onFailure(int pCode, String pMsg) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, pMsg, Toast.LENGTH_SHORT).show();
			}
		});
	}


	private void xiugaimima(String email) {
		// TODO Auto-generated method stub
		m_UBF.resetPasswordByEmail(email, new NetDataCallback() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onFailure(int pCode, String pMsg) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, pMsg, Toast.LENGTH_SHORT).show();
			}
		});
	}


	private void login(String username, String psd) {
		// TODO Auto-generated method stub
		m_UBF.login(username, psd, new NetDataCallback() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onFailure(int pCode, String pMsg) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, pMsg, Toast.LENGTH_SHORT).show();

			}
		});
	}


	private void register(String username, String psd) {
		m_UBF.regiester(username, psd, "63077217@qq.com", new NetDataCallback() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onFailure(int pCode, String pMsg) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
