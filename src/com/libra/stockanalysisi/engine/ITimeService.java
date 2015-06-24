package com.libra.stockanalysisi.engine;

import java.io.IOException;

public interface ITimeService {
	
	/**
	 * 当前是否为交易时间
	 * @return
	 * @throws IOException
	 */
	boolean isDealTime() throws IOException;

}
