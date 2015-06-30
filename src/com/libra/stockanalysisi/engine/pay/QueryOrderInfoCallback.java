package com.libra.stockanalysisi.engine.pay;

import com.libra.stockanalysisi.bean.NetFileData;

public interface QueryOrderInfoCallback {
	
	void onSuccess(NetFileData pNetFileData);
	
	void onFail(int pCode, String pReason);
	
	void onUnknow();
}
