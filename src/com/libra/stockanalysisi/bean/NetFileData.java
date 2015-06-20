package com.libra.stockanalysisi.bean;

import java.io.File;

public class NetFileData {

	private String m_Url;
	
	private File m_LocalFile;

	public String getUrl() {
		return m_Url;
	}

	public void setUrl(String pUrl) {
		this.m_Url = pUrl;
	}

	public File getLocalFile() {
		return m_LocalFile;
	}

	public void setLocalFile(File pLocalFile) {
		this.m_LocalFile = pLocalFile;
	}
	
}
