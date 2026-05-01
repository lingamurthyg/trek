package com.acme.cargotrak.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {

    private String encoding = "UTF-8";

    public void init(FilterConfig cfg) throws ServletException {
        String e = cfg.getInitParameter("encoding");
        if (e != null && e.length() > 0) encoding = e;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        if (req.getCharacterEncoding() == null) {
            req.setCharacterEncoding(encoding);
        }
        chain.doFilter(req, res);
    }

    public void destroy() {}
}
