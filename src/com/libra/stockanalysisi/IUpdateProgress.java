package com.libra.stockanalysisi;

public interface IUpdateProgress {
	
	/**
	 * 数据更新进度
	 * @param progress
	 */
	void update(int progress);
	
	/**
	 * 更新完成
	 */
	void onFinish();
}
