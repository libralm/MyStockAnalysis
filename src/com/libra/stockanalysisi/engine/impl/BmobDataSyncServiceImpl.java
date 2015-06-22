package com.libra.stockanalysisi.engine.impl;

import java.io.File;
import java.util.List;

import android.content.Context;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.DownloadListener;
import com.bmob.btp.callback.UploadListener;
import com.libra.stockanalysisi.bean.NetFileData;
import com.libra.stockanalysisi.engine.IDataSyncService;

public class BmobDataSyncServiceImpl implements IDataSyncService {
	
	private Context m_Context;
	
	public BmobDataSyncServiceImpl(Context pContext){
		m_Context = pContext;
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
	public void downFile(final String pUrl, final AsyncFileCallback pCallback) {
		// TODO Auto-generated method stub
		BmobProFile.getInstance(m_Context).download(pUrl, new DownloadListener() {

            @Override
            public void onSuccess(String fullPath) {
                // TODO Auto-generated method stub
            	pCallback.onSuccess(fullPath, pUrl);
            }

            @Override
            public void onProgress(String localPath, int percent) {
                // TODO Auto-generated method stub
            	pCallback.onProgress(percent);
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                // TODO Auto-generated method stub
            	pCallback.onError(statuscode, errormsg);
            }
        });
	}

	@Override
	public void requestAllNetFiles(String pUrl, final AllNetFilesCallback pCallback) {
		// TODO Auto-generated method stub
		BmobQuery<NetFileData> query = new BmobQuery<NetFileData>("NetFileData");
		query.setLimit(1000);
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

}
