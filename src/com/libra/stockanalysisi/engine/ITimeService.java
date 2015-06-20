package com.libra.stockanalysisi.engine;

import java.io.IOException;

public interface ITimeService {
	
	/**
	 * 是否为交易时间
	 * @return
	 * @throws IOException
	 */
	boolean isDealTime() throws IOException;
}
