package com.song.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.song")
@PropertySource("classpath:config.properties")
public class ApplicationConfig {
	@Value("${selectorThreads}")
	public int selectorThreads;
	
	@Value("${workerThreads}")
	public int workerThreads;

	

}
