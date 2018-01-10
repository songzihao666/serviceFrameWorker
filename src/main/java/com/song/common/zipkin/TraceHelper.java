package com.song.common.zipkin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.internal.Platform;
import brave.propagation.TraceContext;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.kafka11.KafkaSender;
@Component
public class TraceHelper {
	@Value("${kafkaServer}")
	private String kafkaServer;
	
	@Value("${serviceName}")
	private String serviceName;
	
	private static Sender sender;
	private static AsyncReporter spanReporter;
	
	private static Tracing tracing;
	private static Tracer tracer;
	
	@PostConstruct
	public void init() {
		sender = KafkaSender.create(kafkaServer);//OkHttpSender.create("http://10.211.55.5:9411/api/v2/spans");
		spanReporter = AsyncReporter.create(sender);
		tracing = Tracing.newBuilder()
	            .localServiceName(serviceName)
	            .spanReporter(spanReporter)
	            .build();
		tracer = tracing.tracer();
	}

	
	public static Span csStart() {
		Span span = TraceContextHelper.getSpan();
		if (span == null) {
			span = tracer.newTrace().annotate("cs").start();
		}else {
			span = tracer.joinSpan(TraceContext.newBuilder().traceId(span.context().traceId())
				.parentId(span.context().spanId()).sampled(span.context().sampled()).spanId(Platform.get().randomLong())
				.debug(span.context().debug())
				.build()).annotate("cs").start();
		}
		return span;
	}
	
	public static void crFinish(Span span) {
		span.annotate("cr").finish();	
	}
	
	public static void srStart(ZipkinTraceContext context, String name, String method) {
		Span span = tracer.joinSpan(TraceContext.newBuilder().traceId(context.getTraceId())
				.parentId(context.getParentId()).sampled(context.isSampled()).spanId(context.getSpanId())
				.debug(context.isDebug())
				.build()).name(name).annotate("sr").tag("method", method).start();
		TraceContextHelper.setSpan(span);
	}
	
	public static void ssFinish() {
		TraceContextHelper.getSpan().annotate("ss").finish();
		TraceContextHelper.remove();
	}

}
