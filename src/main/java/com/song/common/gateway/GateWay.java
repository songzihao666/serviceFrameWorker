package com.song.common.gateway;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.song.common.model.Args;
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
	
	@Value("${serviceName}")
	private String serviceName;
	
	@Override
	public Result doService(Args param) throws TException {
		// TODO Auto-generated method stub
		TraceHelper.srStart(param.getCtx());
		Result result = null;
		try {
			result = serviceProcessor.processor(param.getType(), param.getData());
			TraceHelper.addInfo("code", result.getCode() + "");
		} catch (Exception e) {
			// TODO: handle exception
			MDC.put("serviceName", serviceName);
			try {
				MDC.put("ip", InetAddress.getLocalHost().getHostAddress());
			} catch (UnknownHostException unknownHostException) {
				// TODO: handle exception
				logger.error(unknownHostException.getMessage(),unknownHostException);
			}
			
			logger.error(e.getMessage(),e);
			MDC.remove("serviceName");
			MDC.remove("ip");
			result = new Result().setCode(500).setMessage(e.getMessage());
			TraceHelper.addInfo("code", result.getCode() + "");
			TraceHelper.addInfo(exception, e.getMessage());
		}
		TraceHelper.ssFinish();
		return result;
	}
}
