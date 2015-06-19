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
			Double todayStartPri = dataObj.getDouble("todayStartPri");
			Double yestodEndPri = dataObj.getDouble("yestodEndPri");
			Double nowPri = dataObj.getDouble("nowPri");
			Double todayMax = dataObj.getDouble("todayMax");
			Double todayMin = dataObj.getDouble("todayMin");
			Double competitivePri = dataObj.getDouble("competitivePri");
			Double reservePri = dataObj.getDouble("reservePri");
			Double traNumber = dataObj.getDouble("traNumber");
			Double traAmount = dataObj.getDouble("traAmount");
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
