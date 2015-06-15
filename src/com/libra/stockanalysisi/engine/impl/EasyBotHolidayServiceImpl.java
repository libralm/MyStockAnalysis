package com.libra.stockanalysisi.engine.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.libra.stockanalysisi.bean.Holiday;
import com.libra.stockanalysisi.engine.HolidayCallBack;
import com.libra.stockanalysisi.engine.IHolidayService;
import com.libra.stockanalysisi.engine.serialization.impl.EasyBotHolidayServiceSeralizationImpl;
import com.libra.stockanalysisi.netUtils.HttpsClientUtils;

public class EasyBotHolidayServiceImpl implements IHolidayService {

	private String m_Url = new String(
			"http://www.easybots.cn/api/holiday.php?d=*");

	private HttpsClientUtils m_HttpsClientUtils;

	private EasyBotHolidayServiceSeralizationImpl m_SeralizationImpl;

	public EasyBotHolidayServiceImpl() {
		super();
		m_HttpsClientUtils = new HttpsClientUtils(5000, 10000);
		m_SeralizationImpl = new EasyBotHolidayServiceSeralizationImpl();
	}

	@Override
	public boolean isHoliday(Date pDate) throws NetworkErrorException {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String url = getUrl(sdf.format(pDate));
		String string = m_HttpsClientUtils.get(url);
		Holiday holiday = m_SeralizationImpl.deserializationStockInfo(string);
		return holiday.isIsHoliday();
	}

	private String getUrl(String pStrDate) {
		return new String(m_Url.replace("*", pStrDate));
	}

}
