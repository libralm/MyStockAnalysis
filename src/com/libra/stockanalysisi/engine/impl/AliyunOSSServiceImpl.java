package com.libra.stockanalysisi.engine.impl;

import java.io.File;

import android.content.Context;

import com.alibaba.sdk.android.oss.OSSService;
import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.alibaba.sdk.android.oss.model.AuthenticationType;
import com.alibaba.sdk.android.oss.model.ClientConfiguration;
import com.alibaba.sdk.android.oss.model.TokenGenerator;
import com.libra.stockanalysisi.engine.IOSSService;

public class AliyunOSSServiceImpl implements IOSSService {
	
	private OSSService m_OSSService;
	
	public AliyunOSSServiceImpl(Context pContext){
		initOSSSevice(pContext);
	}

	private void initOSSSevice(Context pContext) {
		// TODO Auto-generated method stub
		OSSService ossService = OSSServiceProvider.getService();
		ossService.setApplicationContext(pContext);
		ossService.setGlobalDefaultHostId("oss-cn-beijing.aliyuncs.com");//北京节点
		ossService.setAuthenticationType(AuthenticationType.ORIGIN_AKSK);
		ossService.setGlobalDefaultTokenGenerator(new TokenGenerator() {

			@Override
			public String generateToken(String arg0, String arg1, String arg2,
					String arg3, String arg4, String arg5) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		ossService.setCustomStandardTimeWithEpochSec(System.currentTimeMillis());
		ClientConfiguration conf = new ClientConfiguration();
		conf.setConnectTimeout(15 * 1000); // 设置建连超时时间，默认为30s
		conf.setSocketTimeout(15 * 1000); // 设置socket超时时间，默认为30s
		conf.setMaxConnections(50); // 设置全局最大并发连接数，默认为50个
		ossService.setClientConfiguration(conf);
		m_OSSService = ossService;
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
