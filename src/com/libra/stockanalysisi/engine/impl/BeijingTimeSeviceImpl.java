package com.libra.stockanalysisi.engine.impl;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.TimeZone;

import com.libra.stockanalysisi.engine.ITimeService;

class BeijingTimeSeviceImpl implements ITimeService {

	@SuppressWarnings("deprecation")
	@Override
	public boolean isDealTime() throws IOException {
		// TODO Auto-generated method stub
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8")); // 时区设置  
	       URL url=new URL("http://www.baidu.com");//取得资源对象  
	       URLConnection uc=url.openConnection();//生成连接对象  
	       uc.connect(); //发出连接  
	       long ld=uc.getDate(); //取得网站日期时间（时间戳）  
	       Date date=new Date(ld); //转换为标准时间对象  
	       //分别取得时间中的小时，分钟和秒，并输出  
	       int year = date.getYear();
	       int month = date.getMonth();
	       int day = date.getDate();
	       Date dealTime = new Date(year, month, day, 8, 24);
	       if(date.after(dealTime)){
	    	   return true;
	       }
		return false;
	}

}
