package com.libra.stockanalysisi.engine.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.os.Environment;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.bmob.BmobConfiguration;
import com.bmob.BmobPro;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.DownloadListener;
import com.bmob.btp.callback.UploadListener;
import com.libra.stockanalysisi.bean.NetFileData;
import com.libra.stockanalysisi.engine.IDataSyncService;
import com.libra.stockanalysisi.engine.IPersistenceService;

public class BmobDataSyncServiceImpl implements IDataSyncService {
	
	private Context m_Context;
	
	private File m_SaveFile;
	
	public BmobDataSyncServiceImpl(Context pContext){
		m_Context = pContext;
		m_SaveFile = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/" + IPersistenceService.M_DIRNAME);
		BmobConfiguration config = new BmobConfiguration.Builder(pContext).customExternalCacheDir("Stock").build();
		BmobPro.getInstance(pContext).initConfig(config);
	}
	

	@Override
	public void uploadFile(File pfile, final AsyncFileCallback pCallback) {
		// TODO Auto-generated method stub
		BmobProFile.getInstance(m_Context).upload(pfile.getAbsolutePath(), new UploadListener() {

            @Override
            public void onSuccess(String fileName,String url) {
                // TODO Auto-generated method stub
            	pCallback.onSuccess(fileName, url);
            }

            @Override
            public void onProgress(int ratio) {
                // TODO Auto-generated method stub
            	pCallback.onProgress(ratio);
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                // TODO Auto-generated method stub
            	pCallback.onError(statuscode, errormsg);
            }
        });
	}

	@Override
	public void downFile(final NetFileData pNetFileData, final AsyncFileCallback pCallback) {
		// TODO Auto-generated method stub
		BmobProFile.getInstance(m_Context).download(pNetFileData.getFileName(), new DownloadListener() {

            @Override
            public void onSuccess(String fullPath) {
                // TODO Auto-generated method stub
            	String newFullPath = moveAndRenameFile(fullPath,pNetFileData.getOriFileName());
            	pCallback.onSuccess(newFullPath, pNetFileData.getUrl());
            	System.out.println("下载成功："+fullPath);
            }

			/**
             * 修改文件名
             * @param fullPath
             * @param oriFileName
             * @return
             */
            private String moveAndRenameFile(String fullPath, String oriFileName) {
				// TODO Auto-generated method stub
            	File file = new File(fullPath);
            	File newFile = new File(m_SaveFile,oriFileName);
            	file.renameTo(newFile);
            	file.delete();
            	return newFile.getAbsolutePath();
			}

			@Override
            public void onProgress(String localPath, int percent) {
                // TODO Auto-generated method stub
            	pCallback.onProgress(percent);
            	System.out.println("下载百分比："+localPath+":"+percent);
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                // TODO Auto-generated method stub
            	pCallback.onError(statuscode, errormsg);
            	System.out.println("下载错误信息：："+errormsg);
            }
        });
	}

	@Override
	public	void requestNetFiles(Date pBeginDate, Date pEndDate, final AllNetFilesCallback pCallback){
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strBeginDae = sdf.format(pBeginDate);
		String strEndDate = sdf.format(pEndDate);
		BmobQuery<NetFileData> query = new BmobQuery<NetFileData>("NetFileData");
		query.setLimit(1000);
		query.addWhereGreaterThan("m_OriFileName", strBeginDae);
		query.addWhereLessThanOrEqualTo("m_OriFileName", strEndDate);
		query.findObjects(m_Context, new FindListener<NetFileData>() {
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				pCallback.onError(arg0, arg1);
			}

			@Override
			public void onSuccess(List<NetFileData> arg0) {
				// TODO Auto-generated method stub
				pCallback.onNetFilesData(arg0);
			}
		});
	}

	@Override
	public void uploadNetFilesInfo(final NetFileData pNetFileData, final AsyncFileCallback pCallback) {
		pNetFileData.save(m_Context, new SaveListener() {

		    @Override
		    public void onSuccess() {
		        // TODO Auto-generated method stub
		        pCallback.onSuccess(pNetFileData.getLocalFile().getAbsolutePath(), pNetFileData.getUrl());
		    }

		    @Override
		    public void onFailure(int code, String arg0) {
		        // 添加失败
		    	pCallback.onError(code, arg0);
		    }
		});
	}


	@Override
	public void queryNetFile(String url, final QueryNetFileCallback pCallback) {
		// TODO Auto-generated method stub
		BmobQuery<NetFileData> query = new BmobQuery<NetFileData>();
		BmobQuery<NetFileData> contains = query.addWhereEqualTo("m_Url", url);
		contains.findObjects(m_Context, new FindListener<NetFileData>(){

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				pCallback.onFail(arg0, arg1);
			}

			@Override
			public void onSuccess(List<NetFileData> arg0) {
				// TODO Auto-generated method stub
				pCallback.onSuccess(arg0);
			}
			
		});
	}


}
