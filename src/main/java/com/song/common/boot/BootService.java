package com.song.common.boot;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.song.common.config.ApplicationConfig;
import com.song.common.context.ApplicationContextHelper;
import com.song.common.protocol.ServerService;
import com.song.common.protocol.ServerService.Iface;

public class BootService {
	
	private static Logger logger = LoggerFactory.getLogger(BootService.class);

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		ApplicationContextHelper.setContext(applicationContext);
		TNonblockingServerTransport transport = new TNonblockingServerSocket(9999);
		TThreadedSelectorServer.Args arg = new TThreadedSelectorServer.Args(transport);
		ApplicationConfig config = applicationContext.getBean(ApplicationConfig.class);
		arg.protocolFactory(new TCompactProtocol.Factory()).selectorThreads(config.selectorThreads)
		.workerThreads(config.workerThreads);
		arg.processor(new ServerService.Processor<ServerService.Iface>(applicationContext.getBean(Iface.class)));
		TThreadedSelectorServer server = new TThreadedSelectorServer(arg);
		logger.info("server startup");
		server.serve();
	}

}
