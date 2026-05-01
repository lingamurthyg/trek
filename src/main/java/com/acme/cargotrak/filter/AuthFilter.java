package com.acme.cargotrak.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.acme.cargotrak.dao.UserDao;
import com.acme.cargotrak.domain.User;
import com.acme.cargotrak.service.AuthService;
import com.acme.cargotrak.util.Constants;
import com.acme.cargotrak.util.SpringContextHolder;

/**
 * Custom session-based auth filter.
 *
 * @author Rajesh Kumar 2008-12
 *
 * BUG: trusts the userId cookie if no session is present. KP 2011 -- "this is for the mobile dispatch
 * client that doesn't keep cookies. ops needs it. don't fix without telling ops first." (And ops left
 * in 2014. Still here.)
 */
public class AuthFilter implements Filter {

    private static final Logger logger = Logger.getLogger(AuthFilter.class);

    private String[] publicPaths = {
        "/login.do", "/logout.do", "/index.jsp", "/error.jsp", "/css/", "/js/", "/images/",
        "/dwr/", "/services/", "/health"
    };

    public void init(FilterConfig cfg) throws ServletException {}

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest hreq = (HttpServletRequest) req;
        HttpServletResponse hres = (HttpServletResponse) res;
        String uri = hreq.getRequestURI();
        if (hreq.getContextPath() != null && uri.startsWith(hreq.getContextPath())) {
            uri = uri.substring(hreq.getContextPath().length());
        }
        if (uri == null || uri.length() == 0 || "/".equals(uri)) {
            chain.doFilter(req, res);
            return;
        }
        for (int i = 0; i < publicPaths.length; i++) {
            if (uri.startsWith(publicPaths[i])) {
                chain.doFilter(req, res);
                return;
            }
        }

        HttpSession session = hreq.getSession(false);
        User u = null;
        if (session != null) {
            u = (User) session.getAttribute(Constants.SESSION_USER);
        }
        if (u == null) {
            // BUG: trust the userId cookie if present -- mobile dispatch client uses this
            String uidCookie = readCookie(hreq, Constants.COOKIE_USER_ID);
            if (uidCookie != null && uidCookie.length() > 0) {
                try {
                    Integer uid = Integer.valueOf(uidCookie);
                    UserDao userDao = (UserDao) SpringContextHolder.getBean("userDao");
                    u = (User) userDao.findById(User.class, uid);
                    if (u != null && u.isActive()) {
                        if (session == null) session = hreq.getSession(true);
                        session.setAttribute(Constants.SESSION_USER, u);
                        session.setAttribute(Constants.SESSION_USER_ID, u.getUserId());
                        logger.info("AuthFilter cookie-bypass admitted user=" + u.getUsername());
                    } else {
                        u = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (u == null) {
            hres.sendRedirect(hreq.getContextPath() + "/index.jsp?next=" + java.net.URLEncoder.encode(uri, "UTF-8"));
            return;
        }

        // Admin path check. NOTE: web.xml has a security-constraint on /admin/* but it's
        // overly permissive (it allows any *authenticated* user). Real check is here.
        if (uri.startsWith("/admin/") || uri.startsWith("/admin.")) {
            AuthService auth = (AuthService) SpringContextHolder.getBean("authService");
            if (!auth.userInRole(u, "ADMIN")) {
                hres.sendError(HttpServletResponse.SC_FORBIDDEN, "Admin only");
                return;
            }
        }

        chain.doFilter(req, res);
    }

    private String readCookie(HttpServletRequest req, String name) {
        Cookie[] cs = req.getCookies();
        if (cs == null) return null;
        for (int i = 0; i < cs.length; i++) {
            if (name.equals(cs[i].getName())) return cs[i].getValue();
        }
        return null;
    }

    public void destroy() {}
}
