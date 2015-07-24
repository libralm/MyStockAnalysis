package com.libra.stockanalysisi.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.libra.stockanalysisi.engine.IUpdateProgress;
import com.libra.stockanalysisi.engine.impl.AppBussinessFacdeService;
import com.libra.stockanalysisi.engine.impl.StockBussisceFacde;

public class AutoUpdateDataRecevier extends BroadcastReceiver {
	
	public static final String ACTION_AUTO_UPDATE_DATA = "action.auto.update.data";

	@Override
	public void onReceive(final Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		if(ACTION_AUTO_UPDATE_DATA.equals(action)){
			AppBussinessFacdeService service = new AppBussinessFacdeService(context);
			StockBussisceFacde facde = (StockBussisceFacde) service.getFacdeService(AppBussinessFacdeService.STOCK_FACDE_SERVICE);
			facde.updateData(new IUpdateProgress() {
				
				@Override
				public void update(int progress) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					Toast.makeText(context, "股票数据更新完成", Toast.LENGTH_LONG).show();
				}
				
				@Override
				public void onFailure(Throwable pThrowable) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}

}
