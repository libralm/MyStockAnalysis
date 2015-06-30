package com.libra.stockanalysisi.bean;

import java.io.File;

import cn.bmob.v3.BmobObject;

public class NetFileData extends BmobObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6063107599077834704L;

	private String m_Url;
	
	private File m_LocalFile;
	
	private String m_FileName;
	
	private String m_OriFileName;

	public String getOriFileName() {
		return m_OriFileName;
	}

	public void setOriFileName(String pOriFileName) {
		this.m_OriFileName = pOriFileName;
	}

	public String getFileName() {
		return m_FileName;
	}

	public void setFileName(String pFileName) {
		this.m_FileName = pFileName;
	}

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
