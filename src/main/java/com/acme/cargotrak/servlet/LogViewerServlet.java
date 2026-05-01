package com.acme.cargotrak.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.acme.cargotrak.domain.User;
import com.acme.cargotrak.service.AuthService;
import com.acme.cargotrak.util.Constants;
import com.acme.cargotrak.util.SpringContextHolder;
import com.acme.cargotrak.util.Util;

/**
 * Serves the contents of /var/app/cargo/logs/*.log to the admin user as plain text.
 * Yes, this is the kind of thing legacy apps actually have.
 */
public class LogViewerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        User u = s == null ? null : (User) s.getAttribute(Constants.SESSION_USER);
        AuthService auth = (AuthService) SpringContextHolder.getBean("authService");
        if (u == null || !auth.userInRole(u, "ADMIN")) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "admin only");
            return;
        }
        String name = Util.nvl(req.getParameter("name"), "cargotrak.log");
        if (name.indexOf("..") >= 0 || name.indexOf('/') >= 0 || name.indexOf('\\') >= 0) {
            res.sendError(400);
            return;
        }
        File f = new File(Constants.LOGS_DIR, name);
        res.setContentType("text/plain;charset=UTF-8");
        PrintWriter pw = res.getWriter();
        if (!f.exists()) {
            pw.println("[no such file: " + f.getAbsolutePath() + "]");
            File dir = new File(Constants.LOGS_DIR);
            File[] files = dir.listFiles();
            if (files != null) {
                pw.println("Available files:");
                for (int i = 0; i < files.length; i++) {
                    pw.println(" - " + files[i].getName());
                }
            }
            return;
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            String line;
            int max = Util.paramAsInt(req, "lines", 1000);
            int n = 0;
            while ((line = br.readLine()) != null && n < max) {
                pw.println(line);
                n++;
            }
        } finally {
            try { if (br != null) br.close(); } catch (Exception ex) {}
        }
    }
}
