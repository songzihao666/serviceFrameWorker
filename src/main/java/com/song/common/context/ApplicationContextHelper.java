package com.song.common.context;

import org.springframework.context.ApplicationContext;

public class ApplicationContextHelper {
	
	private static ApplicationContext context;

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext context) {
		ApplicationContextHelper.context = context;
	}
	
	

}
