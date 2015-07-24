package com.libra.stockanalysisi;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import cn.bmob.v3.Bmob;

import com.libra.stockanalysisi.receiver.AutoUpdateDataRecevier;
import com.thinkland.sdk.android.JuheSDKInitializer;

public class MyApplication extends Application {
	
	
	private static final int INTERVAL = 1000 * 60 * 60 * 24;// 24h
	private static final int REQUEST_CODE = 0;

	//...


	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		JuheSDKInitializer.initialize(getApplicationContext());
		Bmob.initialize(this, "a4622d0cf8aa4831957757e871f0deba");
		if(BuildConfig.DEBUG){
			setAlarm();
		}
	}
	
	private void setAlarm(){
		Intent intent = new Intent(this, AutoUpdateDataRecevier.class);
		intent.setAction(AutoUpdateDataRecevier.ACTION_AUTO_UPDATE_DATA);
        PendingIntent sender = PendingIntent.getBroadcast(this,
                REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Schedule the alarm!
        AlarmManager am = (AlarmManager) this
                .getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 10);
        calendar.set(Calendar.MILLISECOND, 0);

        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                INTERVAL, sender);
	}
}
