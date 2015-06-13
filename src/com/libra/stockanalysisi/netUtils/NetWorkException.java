package com.libra.stockanalysisi.netUtils;

public class NetWorkException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7171529263212291470L;

	public NetWorkException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}
}
