package com.song.common.gateway;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.song.common.model.Args;
import com.song.common.model.InvokeInfo;
import com.song.common.model.Result;
import com.song.common.processor.ServiceProcessor;
import com.song.common.protocol.ServerService.Iface;
import com.song.common.zipkin.TraceHelper;

@Component
public class GateWay implements Iface {
	
	private static Logger logger = LoggerFactory.getLogger(GateWay.class);
	
	private static String exception = "exception";
	
	@Autowired
	private ServiceProcessor serviceProcessor;
	
	@Override
	public Result doService(Args param) throws TException {
		// TODO Auto-generated method stub
		InvokeInfo info = serviceProcessor.getInvokeInfoByType(param.getType());
		if (info == null) {
			return new Result().setCode(404).setMessage("no service!");
		}
		TraceHelper.srStart(param.getCtx(), info.getClassName(), info.getMethodName());
		Result result = null;
		try {
			result = serviceProcessor.processor(param.getType(), param.getData());
			TraceHelper.ssFinish();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(),e);
			result = new Result().setCode(500).setMessage(e.getMessage());
			TraceHelper.ssFinishWithInfo(exception, e.getMessage());
		}		
		return result;
	}
}
