package com.acme.cargotrak.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;

import com.acme.cargotrak.domain.User;
import com.acme.cargotrak.util.Constants;

public class MdcFilter implements Filter {

    public void init(FilterConfig cfg) throws ServletException {}

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String user = "anonymous";
        if (req instanceof HttpServletRequest) {
            HttpSession s = ((HttpServletRequest) req).getSession(false);
            if (s != null) {
                Object u = s.getAttribute(Constants.SESSION_USER);
                if (u instanceof User && ((User) u).getUsername() != null) {
                    user = ((User) u).getUsername();
                }
            }
        }
        MDC.put("user", user);
        try {
            chain.doFilter(req, res);
        } finally {
            MDC.remove("user");
        }
    }

    public void destroy() {}
}
