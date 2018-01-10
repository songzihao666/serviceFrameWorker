package com.song.common.model;

public class InvokeInfo {
	
	private String className;
	private String methodName;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public InvokeInfo(String className, String method) {
		super();
		this.className = className;
		this.methodName = method;
	}

}
