package com.song.common.processor;

import com.song.common.model.Result;

public interface ServiceProcessor {

	public Result processor(int type, String jdata, byte[] data) throws Exception;
	
}
