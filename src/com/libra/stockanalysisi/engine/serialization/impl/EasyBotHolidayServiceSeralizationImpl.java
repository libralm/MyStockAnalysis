package com.libra.stockanalysisi.engine.serialization.impl;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.libra.stockanalysisi.bean.Holiday;
import com.libra.stockanalysisi.engine.serialization.IHolidaySeralization;

public class EasyBotHolidayServiceSeralizationImpl implements
		IHolidaySeralization {

	@Override
	public Holiday deserializationStockInfo(String pData) {
		// TODO Auto-generated method stub
		String date = patternData(pData);
		JSONObject object;
		Holiday holiday = new Holiday();
		try {
			object = new JSONObject(pData);
			boolean isHoliday = object.getInt(date) == 0 ? false : true;
			holiday.setDate(date);
			holiday.setIsHoliday(isHoliday);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return holiday;
	}

	private String patternData(String pData) {
		String re1="((?:(?:[1]{1}\\d{1}\\d{1}\\d{1})|(?:[2]{1}\\d{3}))(?:[0]?[1-9]|[1][012])(?:(?:[0-2]?\\d{1})|(?:[3][01]{1})))(?![\\d])";	// YYYYMMDD 1

	    Pattern p = Pattern.compile(re1,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	    Matcher m = p.matcher(pData);
	    if (m.find())
	    {
	        String yyyymmdd=m.group(1);
	        return yyyymmdd;
	    }
		return null;
	}

}
