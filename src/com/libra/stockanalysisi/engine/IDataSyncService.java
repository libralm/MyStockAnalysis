package com.libra.stockanalysisi.engine;

import java.io.File;
import java.util.List;

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
	void uploadFile(File pfile, AsyncFileCallback pCallback);
	
	/**
	 * 文件下载
	 * @param pPath
	 * @param pCallback
	 */
	void downFile(String pUrl, AsyncFileCallback pCallback);
	
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
	void uploadNetFilesInfo(NetFileData pNetFileData, AsyncFileCallback pCallback);
	
	/**
	 * 请求网络文件下载地址的集合
	 * @author liaomin
	 *
	 */
	public interface AllNetFilesCallback{
		
		void onNetFilesData(List<NetFileData> pNetFileData);
		
		void onError(int pCode, String pMsg);
	}
	
	public interface AsyncFileCallback {

		public void onSuccess(String pFileName,String url);
		
		public void onProgress(int pRatio);
		
		public void onError(int pCode, String pMsg);
	}
}
