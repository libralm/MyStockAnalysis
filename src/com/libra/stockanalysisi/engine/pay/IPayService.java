package com.libra.stockanalysisi.engine.pay;

public interface IPayService {
	
	void payZhifubao(IPayListener pPayListener);
	
	void payWeixin(IPayListener pPayListener);
}
