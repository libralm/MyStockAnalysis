package com.libra.stockanalysisi.engine;

import java.util.Date;

import android.accounts.NetworkErrorException;


public interface IHolidayService {
	
	boolean isHoliday(Date pDate) throws NetworkErrorException;
}
