package com.libra.stockanalysisi.engine.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Environment;

import com.libra.stockanalysisi.bean.BaseStock;
import com.libra.stockanalysisi.bean.Stock;
import com.libra.stockanalysisi.engine.IPersistenceService;

class PersistenceServiceImpl implements IPersistenceService {

	private File m_SavePathFile;

	private final String M_DIRNAME = "Stock";

	private final String M_BASESTOCK_INFO = "basestock.txt";

	public PersistenceServiceImpl() {
		// TODO Auto-generated constructor stub
		super();
		initSavePath();
	}

	private void initSavePath() {
		// TODO Auto-generated method stub
		m_SavePathFile = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/" + M_DIRNAME);
		if(!m_SavePathFile.exists()){
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
				ObjectOutputStream oos;
				File file = new File(m_SavePathFile, M_BASESTOCK_INFO);
				try {
					if(!file.exists()){
						file.createNewFile();
					}
					fis = new FileOutputStream(file);
					oos = new ObjectOutputStream(fis);
					oos.writeObject(pStocks);
					oos.close();
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
					for(int i=0; i<pStocks.length; i++){
						Date date = new Date();
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						strDate = format.format(date);
					}
				}
				FileOutputStream fos;
				ObjectOutputStream oos;
				File file = new File(m_SavePathFile, strDate);
				if(!file.exists()){
					try {
						file.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					fos = new FileOutputStream(file);
					oos = new ObjectOutputStream(fos);
					oos.writeObject(pStocks);
					oos.close();
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
		ObjectInputStream ois;
		BaseStock[] baseStocks = null;
		File file = new File(m_SavePathFile, M_BASESTOCK_INFO);
		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			baseStocks = (BaseStock[]) ois.readObject();
			ois.close();
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
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
		ObjectInputStream ois;
		Stock[] stocks = null;
		File file = new File(m_SavePathFile, strDate);
		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			stocks = (Stock[]) ois.readObject();
			ois.close();
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stocks;
	}

}
