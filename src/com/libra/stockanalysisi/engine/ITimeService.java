package com.libra.stockanalysisi.engine;

import java.io.IOException;

public interface ITimeService {
	
	boolean isDealTime() throws IOException;
}
