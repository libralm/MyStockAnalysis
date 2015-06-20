package com.libra.stockanalysisi.engine;

import java.util.Date;

import android.accounts.NetworkErrorException;


public interface IHolidayService {
	
	/**
	 * 是否为节假日
	 * @param pDate
	 * @return
	 * @throws NetworkErrorException
	 */
	boolean isHoliday(Date pDate) throws NetworkErrorException;
}
