package com.song.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.song.common.zipkin.TraceContextHelper;

@Component
@Aspect
public class InvokePointCut {

	@Before("execution(* com.song.biz.*.service..*.*(..))")
	public void doTracing(JoinPoint joinPoint) {
		TraceContextHelper.getSpan().name(joinPoint.getTarget().getClass().getName()).annotate("sr")
		.tag("method", joinPoint.getSignature().getName()).start();
	}
}
