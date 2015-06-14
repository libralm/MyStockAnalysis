package com.libra.stockanalysisi;

import java.io.File;

import com.alibaba.sdk.android.oss.OSSClient;
import com.libra.stockanalysisi.engine.IOSSService;

public class AliyunOSSServiceImpl implements IOSSService {
	
	private OSSClient m_OSSClient;
	
	public AliyunOSSServiceImpl(){
		m_OSSClient = new OSSClient();
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
