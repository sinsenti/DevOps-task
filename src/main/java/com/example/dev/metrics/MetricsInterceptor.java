package com.example.dev.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class MetricsInterceptor implements HandlerInterceptor {

    private final MeterRegistry meterRegistry;

    public MetricsInterceptor(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        startTime.set(System.nanoTime());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long duration = System.nanoTime() - startTime.get();
        startTime.remove();

        Timer.builder("http_requests_duration_seconds")
                .description("Duration of HTTP requests in seconds")
                .tags("method", request.getMethod(),
                        "uri", request.getRequestURI(),
                        "status", String.valueOf(response.getStatus()))
                .register(meterRegistry)
                .record(duration, TimeUnit.NANOSECONDS);

        meterRegistry.counter("http_requests_total",
                        "method", request.getMethod(),
                        "uri", request.getRequestURI(),
                        "status", String.valueOf(response.getStatus()))
                .increment();
    }
}
