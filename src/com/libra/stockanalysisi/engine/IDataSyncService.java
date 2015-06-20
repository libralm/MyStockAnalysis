package com.libra.stockanalysisi.engine;

import java.io.File;

import com.libra.stockanalysisi.bean.NetFileData;

/**
 * 数据同步服务
 * @author liaomin
 *
 */
public interface IDataSyncService {
	
	/**
	 * 文件上传
	 * @param pfile
	 * @param pCallback
	 */
	void uploadFile(File pfile, NetDataCallback pCallback);
	
	/**
	 * 文件下载
	 * @param pPath
	 * @param pCallback
	 */
	void downFile(String pUrl, NetDataCallback pCallback);
	
	/**
	 * 请求所有的网络文件
	 * @param pUrl
	 * @param pCallback
	 */
	void requestAllNetFiles(String pUrl, AllNetFilesCallback pCallback);
	
	/**
	 * 上传网络文件的信息
	 * @param pUrl
	 * @param pCallback
	 */
	void uploadNetFilesInfo(String pUrl, NetDataCallback pCallback);
	
	/**
	 * 请求网络文件下载地址的集合
	 * @author liaomin
	 *
	 */
	public interface AllNetFilesCallback{
		
		void onNetFilesData(NetFileData[] pNetFileData);
	}
}
