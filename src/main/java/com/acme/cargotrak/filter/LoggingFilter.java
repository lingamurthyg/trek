package com.acme.cargotrak.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class LoggingFilter implements Filter {

    private static final Logger logger = Logger.getLogger(LoggingFilter.class);

    public void init(FilterConfig cfg) throws ServletException {}

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        long start = System.currentTimeMillis();
        try {
            chain.doFilter(req, res);
        } finally {
            long elapsed = System.currentTimeMillis() - start;
            if (req instanceof HttpServletRequest) {
                HttpServletRequest hr = (HttpServletRequest) req;
                logger.info(hr.getMethod() + " " + hr.getRequestURI() + " (" + elapsed + " ms)");
            }
        }
    }

    public void destroy() {}
}
