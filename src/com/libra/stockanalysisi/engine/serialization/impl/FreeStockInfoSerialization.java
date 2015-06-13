package com.libra.stockanalysisi.engine.serialization.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import com.libra.stockanalysisi.bean.Stock;
import com.libra.stockanalysisi.engine.serialization.IStockInfoSerializtion;

@SuppressLint("SimpleDateFormat")
public class FreeStockInfoSerialization implements IStockInfoSerializtion {


	
	
	@Override
	public Stock deserializationStockInfo(String pData) throws IOException {
		// TODO Auto-generated method stub
		String[] split = pData.split("\"");
		String gid = pData.split("=")[0].split("_")[2];
		String[] datas = split[1].split(",");
		if(datas.length<2){
			return new Stock(gid, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, null);
		}
		/**
		 * 
	 0：”大秦铁路”，股票名字； 
	1：”27.55″，今日开盘价； 
	2：”27.25″，昨日收盘价； 
	3：”26.91″，当前价格； 
	4：”27.55″，今日最高价； 
	5：”26.20″，今日最低价； 
	6：”26.91″，竞买价，即“买一”报价； 
	7：”26.92″，竞卖价，即“卖一”报价； 
	8：”22114263″，成交的股票数，由于股票交易以一百股为基本单位，所以在使用时，通常把该值除以一百； 
	9：”589824680″，成交金额，单位为“元”，为了一目了然，通常以“万元”为成交金额的单位，所以通常把该值除以一万； 
	10：”4695″，“买一”申请4695股，即47手； 
	11：”26.91″，“买一”报价； 
	12：”57590″，“买二” 
	13：”26.90″，“买二” 
	14：”14700″，“买三” 
	15：”26.89″，“买三” 
	16：”14300″，“买四” 
	17：”26.88″，“买四” 
	18：”15100″，“买五” 
	19：”26.87″，“买五” 
	20：”3100″，“卖一”申报3100股，即31手； 
	21：”26.92″，“卖一”报价 
	(22, 23), (24, 25), (26,27), (28, 29)分别为“卖二”至“卖四的情况” 
	30：”2008-01-11″，日期； 
	31：”15:05:32″，时间；
		 */
		System.out.println(pData);
		System.out.println("====================");
		String name = datas[0];
		double todayStartPri = Double.parseDouble(datas[1]);
		double yestodEndPri = Double.parseDouble(datas[2]);
		double nowPri = Double.parseDouble(datas[3]);
		double todayMax = Double.parseDouble(datas[4]);
		double todayMin = Double.parseDouble(datas[5]);
		double competitivePri = Double.parseDouble(datas[6]);
		double reservePri = Double.parseDouble(datas[7]);
		double traNumber = Double.parseDouble(datas[8]);
		double traAmount = Double.parseDouble(datas[9]);
		String strDate = datas[30];
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IOException();
		}
		Stock stock = new Stock(gid, name, todayStartPri, yestodEndPri,
				nowPri, todayMax, todayMin, competitivePri, reservePri,
				traNumber, traAmount, date);
		
		return stock;
	}

	@Override
	public String serializationStockInfo(Stock pStock) {
		// TODO Auto-generated method stub
		return null;
	}

}
