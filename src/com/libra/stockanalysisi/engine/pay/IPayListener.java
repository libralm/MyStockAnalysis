package com.libra.stockanalysisi.engine.pay;

public interface IPayListener {
	
	public void unknow();

	public void succeed();
	
	public void orderId(String orderId);

	public void fail(int code, String reason);
}
