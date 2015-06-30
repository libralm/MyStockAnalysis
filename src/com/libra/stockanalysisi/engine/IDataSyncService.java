package com.libra.stockanalysisi.engine;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.libra.stockanalysisi.bean.NetFileData;
import com.libra.stockanalysisi.bean.Order;

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
	void downFile(NetFileData pNetFileData, AsyncFileCallback pCallback);
	
	/**
	 * 请求指定网络文件
	 * @param pUrl
	 * @param pCallback
	 */
	void requestNetFiles(Date pBeginDate, Date pEndDate, AllNetFilesCallback pCallback);
	
	/**
	 * 上传网络文件的基本信息
	 * @param pUrl
	 * @param pCallback
	 */
	void uploadNetFilesInfo(NetFileData pNetFileData, AsyncFileCallback pCallback);
	
	/**
	 * 根据文件的唯一URL查找文件的信息。
	 * @param url
	 * @param pCallback
	 */
	void queryNetFile(String url,QueryNetFileCallback pCallback);
	
	/**
	 * 请求网络文件下载地址的集合
	 * @author liaomin
	 *
	 */
	public interface AllNetFilesCallback{
		
		void onNetFilesData(List<NetFileData> pNetFileData);
		
		void onError(int pCode, String pMsg);
	}
	
	/**
	 * 同步文件信息回调
	 * @author liaomin
	 *
	 */
	public interface AsyncFileCallback {

		public void onSuccess(String pFileName,String url);
		
		public void onProgress(int pRatio);
		
		public void onError(int pCode, String pMsg);
	}
	
	/**
	 * 
	 * @author liaomin
	 *
	 */
	
	
	/**
	 * 查询网络文件基本信息回调接口
	 * @author liaomin
	 *
	 */
	public interface QueryNetFileCallback{
		
		void onSuccess(List<NetFileData> pNetFileDatas);
		
		void onFail(int pCode, String pReason);
		
		void onUnknow();
	}
}
