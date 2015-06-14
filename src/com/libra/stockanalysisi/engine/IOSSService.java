package com.libra.stockanalysisi.engine;

import java.io.File;

public interface IOSSService {

	void updateFile(File pFile,IOOSServiceCallback pCallback);
	
	void downloadFile(File pFile, IOOSServiceCallback pCallback);
	
	public interface IOOSServiceCallback{
		
		void onFinish();
		
		void onFailture();
	}
}
