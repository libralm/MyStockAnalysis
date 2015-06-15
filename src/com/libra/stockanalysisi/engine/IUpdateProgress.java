package com.libra.stockanalysisi.engine;

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
	
	void onFailure(Throwable pThrowable);
}
