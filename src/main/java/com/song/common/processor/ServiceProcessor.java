package com.song.common.processor;

import com.song.common.model.InvokeInfo;
import com.song.common.model.Result;

public interface ServiceProcessor {

	public Result processor(int type, byte[] data);
	public InvokeInfo getInvokeInfoByType(int type);
	
}
