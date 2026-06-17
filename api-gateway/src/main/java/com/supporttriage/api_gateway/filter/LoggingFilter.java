package com.supporttriage.api_gateway.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoggingFilter implements Filter {
    
    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        long start = System.currentTimeMillis();
        log.info("[Gateway] --> {} {}", httpRequest.getMethod(), httpRequest.getRequestURI());

        chain.doFilter(request, response);

        long duration = System.currentTimeMillis() - start;
        log.info("[Gateway] <-- {} {} - status: {} ({} ms)",
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                httpResponse.getStatus(),
                duration);
    }
}
