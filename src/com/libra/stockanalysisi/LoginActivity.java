package com.libra.stockanalysisi;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.libra.stockanalysisi.engine.IDataSyncService.AsyncFileCallback;
import com.libra.stockanalysisi.engine.IPersistenceService;
import com.libra.stockanalysisi.engine.NetDataCallback;
import com.libra.stockanalysisi.engine.impl.AppBussinessFacdeService;
import com.libra.stockanalysisi.engine.impl.StockBussisceFacde;
import com.libra.stockanalysisi.engine.impl.UserBussinessFacde;

public class LoginActivity extends Activity implements OnClickListener {
	
	EditText m_ETUserName,m_ETPassword;
	
	View m_BtnRegister;

	private UserBussinessFacde m_UBF;

	private StockBussisceFacde m_SBF;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		AppBussinessFacdeService abf = new AppBussinessFacdeService(this);
		m_UBF = (UserBussinessFacde) abf.getFacdeService(AppBussinessFacdeService.USER_FACDE_SERVICE);
		m_SBF = (StockBussisceFacde) abf.getFacdeService(AppBussinessFacdeService.STOCK_FACDE_SERVICE);
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
//			bingPhoneNum();
			uploadFile();
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
	
	private void uploadFile(){
		String path =Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+IPersistenceService.M_DIRNAME;
		File dir = new File(path);
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if(files[i].isFile()){				
				m_SBF.uploadFile(files[i], new AsyncFileCallback() {
					
					@Override
					public void onSuccess(String pFileName, String url) {
						// TODO Auto-generated method stub
						Toast.makeText(LoginActivity.this, "上传成功:"+pFileName, Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onProgress(int pRatio) {
						// TODO Auto-generated method stub
					}
					
					@Override
					public void onError(int pCode, String pMsg) {
						// TODO Auto-generated method stub
						Toast.makeText(LoginActivity.this, "上传错误:"+pMsg, Toast.LENGTH_SHORT).show();
					}
				});
			}
		}
	}
}
