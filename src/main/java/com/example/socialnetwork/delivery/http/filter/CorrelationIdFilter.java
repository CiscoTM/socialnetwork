package com.example.socialnetwork.delivery.http.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorrelationIdFilter implements Filter {

    public static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
    public static final String MDC_CORRELATION_ID_KEY = "CorrelationId";
    public static final String MDC_HTTP_METHOD_KEY = "HttpMethod";
    public static final String MDC_HTTP_PATH_KEY = "HttpPath";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if(!(servletRequest instanceof HttpServletRequest httpRequest) || !(servletResponse instanceof HttpServletResponse httpResponse)){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String correlationId = extractOrGenerateCorrelationId(httpRequest);
        try{
            MDC.put(MDC_CORRELATION_ID_KEY, correlationId);
            MDC.put(MDC_HTTP_METHOD_KEY, httpRequest.getMethod());
            MDC.put(MDC_HTTP_PATH_KEY, httpRequest.getServletPath());

            httpResponse.setHeader(MDC_CORRELATION_ID_KEY, correlationId);
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.remove(MDC_CORRELATION_ID_KEY);
            MDC.remove(MDC_HTTP_METHOD_KEY);
            MDC.remove(MDC_HTTP_PATH_KEY);
        }
    }
    private String extractOrGenerateCorrelationId(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(CORRELATION_ID_HEADER))
                .filter(header -> !header.isBlank())
                .orElseGet(() -> UUID.randomUUID().toString());
    }
}
