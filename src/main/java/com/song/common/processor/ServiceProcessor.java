package com.song.common.processor;

import com.song.common.model.InvokeInfo;

public interface ServiceProcessor {

	public String processor(int type, String param);
	public InvokeInfo getInvokeInfoByType(int type);
	
}
