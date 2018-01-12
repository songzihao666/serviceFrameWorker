package com.song.common.config;

import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.RetryPolicy;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("com.song")
@PropertySource("classpath:config.properties")
@ImportResource("classpath:service.xml")
public class ApplicationConfig {
	
	@Value("${selectorThreads}")
	public int selectorThreads;
	
	@Value("${workerThreads}")
	public int workerThreads;
	
	@Value("${zkServer}")
	private String zkServer;
	
	
	@Bean
	public CuratorZookeeperClient zkClientBean() throws Exception {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 3);
		CuratorZookeeperClient client = new CuratorZookeeperClient(zkServer, 30000, 10000, null, retryPolicy);
		client.start();
		return client;
	}

	@Bean
	public HystrixCommandAspect hystrixAspectBean() {
		return new HystrixCommandAspect();
	}
	
}
