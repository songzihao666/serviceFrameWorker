package com.song.common.zipkin;

import brave.Span;

public class TraceContextHelper {
	
	public static ThreadLocal<Span> threadLocal = new ThreadLocal<Span>();
	
	public static Span getSpan() {
		return threadLocal.get();
	}
	
	public static void setSpan(Span span) {
		threadLocal.set(span);
	}
	
	public static void remove() {
		threadLocal.remove();
	}

}
