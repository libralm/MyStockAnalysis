package com.libra.stockanalysisi.engine.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.libra.stockanalysisi.bean.BaseStock;
import com.libra.stockanalysisi.bean.Stock;
import com.libra.stockanalysisi.engine.IPersistenceService;

class PersistenceServiceImpl implements IPersistenceService {

	private File m_SavePathFile;

	private final String M_DIRNAME = "Stock";

	private final String M_BASESTOCK_INFO = "basestock";

	public PersistenceServiceImpl() {
		// TODO Auto-generated constructor stub
		super();
		initSavePath();
	}

	private void initSavePath() {
		// TODO Auto-generated method stub
		m_SavePathFile = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/" + M_DIRNAME);
		if (!m_SavePathFile.exists()) {
			m_SavePathFile.mkdirs();
		}
	}

	@Override
	public void saveAllBaseStockInfo(final BaseStock[] pStocks) {
		// TODO Auto-generated method stub
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				FileOutputStream fis;
				File file = new File(m_SavePathFile, M_BASESTOCK_INFO);
				try {
					if (!file.exists()) {
						file.createNewFile();
					}
					Gson gson = new GsonBuilder().create();
					String json = gson.toJson(pStocks);
					byte[] bytes = json.getBytes();
					fis = new FileOutputStream(file);
					fis.write(bytes);
					fis.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				return null;
			}
		}.execute();

	}

	@Override
	public void saveAllStocksDetailInfo(final Stock[] pStocks) {
		// TODO Auto-generated method stub
		new AsyncTask<Void, Void, Void>() {

			@SuppressLint("SimpleDateFormat")
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				String strDate = null;
				if (pStocks.length > 0) {
					Date date = new Date();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					strDate = format.format(date);
				}
				Gson gson = new GsonBuilder().create();
				String json = gson.toJson(pStocks);
				byte[] jsonBytes = json.getBytes();
				FileOutputStream fos;
				File file = new File(m_SavePathFile, strDate);
				if (!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					fos = new FileOutputStream(file);
					fos.write(jsonBytes);
					fos.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				return null;
			}
		}.execute();

	}

	@Override
	public BaseStock[] readAllBaseStockInfo() {
		// TODO Auto-generated method stub
		FileInputStream fis;
		BaseStock[] baseStocks = null;
		File file = new File(m_SavePathFile, M_BASESTOCK_INFO);
		try {
			Gson gson = new GsonBuilder().create();
			fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			int length = -1;
			char[] buffer = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((length = isr.read(buffer)) != -1) {
				sb.append(buffer, 0, length);
			}
			String json = sb.toString();
			baseStocks = gson.fromJson(json, BaseStock[].class);
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return baseStocks;
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public Stock[] readAllStocksDetailInfo(Date pStockDate) {
		// TODO Auto-generated method stub
		String strDate = new SimpleDateFormat("yyyy-MM-dd").format(pStockDate);
		FileInputStream fis;
		Stock[] stocks = null;
		Gson gson = new GsonBuilder().create();
		File file = new File(m_SavePathFile, strDate);
		try {
			fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			int length = -1;
			char[] buffer = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((length = isr.read(buffer)) != -1) {
				sb.append(buffer, 0, length);
			}
			String json = sb.toString();
			stocks = gson.fromJson(json, Stock[].class);
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return stocks;
	}

}
