package com.libra.stockanalysisi.engine.impl;

import java.io.File;

import android.content.Context;

import com.alibaba.sdk.android.oss.OSSService;
import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.libra.stockanalysisi.engine.IOSSService;

public class AliyunOSSServiceImpl implements IOSSService {
	
	private OSSService m_OSSService;
	
	public AliyunOSSServiceImpl(Context pContext){
		m_OSSService = OSSServiceProvider.getService();
		m_OSSService.setApplicationContext(pContext);
	}

	@Override
	public void updateFile(File pFile, IOOSServiceCallback pCallback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void downloadFile(File pFile, IOOSServiceCallback pCallback) {
		// TODO Auto-generated method stub

	}

}
