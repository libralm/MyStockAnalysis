package com.libra.stockanalysisi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.libra.stockanalysisi.control.UserManagerControl;
import com.libra.stockanalysisi.engine.impl.AppBussinessFacdeService;
import com.libra.stockanalysisi.engine.impl.StockBussisceFacde;
import com.libra.stockanalysisi.engine.impl.UserBussinessFacde;

public class LoginActivity extends Activity implements OnClickListener {
	
	EditText m_ETUserName,m_ETPassword;
	
	View m_BtnRegister;

	private StockBussisceFacde m_SBF;
	
	private UserManagerControl m_UserManagerControl;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		AppBussinessFacdeService abf = new AppBussinessFacdeService(this);
		m_UserManagerControl = new UserManagerControl(this, (UserBussinessFacde) abf.getFacdeService(AppBussinessFacdeService.USER_FACDE_SERVICE));
		m_SBF = (StockBussisceFacde) abf.getFacdeService(AppBussinessFacdeService.STOCK_FACDE_SERVICE);
//		initView();
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
//			m_UserManagerControl.bingPhoneNum("", "");
			break;

		default:
			break;
		}
	}

	


	
	
	
}
