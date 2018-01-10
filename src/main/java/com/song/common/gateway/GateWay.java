package com.song.common.gateway;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.song.common.model.InvokeInfo;
import com.song.common.processor.ServiceProcessor;
import com.song.common.protocol.ServerService.Iface;
import com.song.common.zipkin.TraceHelper;
import com.song.common.zipkin.ZipkinTraceContext;

@Component
public class GateWay implements Iface {
	
	@Autowired
	private ServiceProcessor serviceProcessor;
	
	@Override
	public String doService(int type, String param, ZipkinTraceContext ctx) throws TException {
		// TODO Auto-generated method stub		
		InvokeInfo info = serviceProcessor.getInvokeInfoByType(type);
		if (info == null) {
			throw new RuntimeException("No InvokeInfo!");
		}
		TraceHelper.srStart(ctx, info.getClassName(), info.getMethodName());
		String result = serviceProcessor.processor(type, param);
		TraceHelper.ssFinish();
		return result;
	}


}
