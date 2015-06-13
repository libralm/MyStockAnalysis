package com.libra.stockanalysisi.engine.serialization.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import com.libra.stockanalysisi.bean.Stock;
import com.libra.stockanalysisi.engine.serialization.IStockInfoSerializtion;

@SuppressLint("SimpleDateFormat")
public class JuheStockInfoSerialization implements IStockInfoSerializtion {

	@Override
	public Stock deserializationStockInfo(String pData) {
		// TODO Auto-generated method stub
		try {
			JSONObject object = new JSONObject(pData);
			JSONArray jsonArray = object.getJSONArray("result");
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			JSONObject dataObj = jsonObject.getJSONObject("data");
			String gid = dataObj.getString("gid");
			String name = dataObj.getString("name");
			double todayStartPri = dataObj.getDouble("todayStartPri");
			double yestodEndPri = dataObj.getDouble("yestodEndPri");
			double nowPri = dataObj.getDouble("nowPri");
			double todayMax = dataObj.getDouble("todayMax");
			double todayMin = dataObj.getDouble("todayMin");
			double competitivePri = dataObj.getDouble("competitivePri");
			double reservePri = dataObj.getDouble("reservePri");
			int traNumber = dataObj.getInt("traNumber");
			int traAmount = dataObj.getInt("traAmount");
			String strDate = dataObj.getString("date");
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
			Stock stock = new Stock(gid, name, todayStartPri, yestodEndPri,
					nowPri, todayMax, todayMin, competitivePri, reservePri,
					traNumber, traAmount, date);
			return stock;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String serializationStockInfo(Stock pStock) {
		// TODO Auto-generated method stub
		return null;
	}

}
