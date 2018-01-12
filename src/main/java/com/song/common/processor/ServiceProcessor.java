package com.song.common.processor;

import com.song.common.model.InvokeInfo;
import com.song.common.model.Result;

public interface ServiceProcessor {

	public Result processor(int type, String data);
	public InvokeInfo getInvokeInfoByType(int type);
	
}
