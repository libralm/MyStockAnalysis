package com.libra.stockanalysisi;

import cn.bmob.v3.Bmob;

import com.thinkland.sdk.android.JuheSDKInitializer;

import android.app.Application;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		JuheSDKInitializer.initialize(getApplicationContext());
		Bmob.initialize(this, "a4622d0cf8aa4831957757e871f0deba");
	}
}
