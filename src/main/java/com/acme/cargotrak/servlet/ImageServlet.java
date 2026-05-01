package com.acme.cargotrak.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.acme.cargotrak.util.Util;

public class ImageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String name = req.getParameter("name");
        if (Util.isBlank(name) || name.indexOf("..") >= 0) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "bad name");
            return;
        }
        InputStream in = getServletContext().getResourceAsStream("/images/" + name);
        if (in == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        if (name.endsWith(".png"))      res.setContentType("image/png");
        else if (name.endsWith(".jpg")) res.setContentType("image/jpeg");
        else if (name.endsWith(".gif")) res.setContentType("image/gif");
        else                            res.setContentType("application/octet-stream");
        OutputStream out = res.getOutputStream();
        try {
            Util.copy(in, out);
        } finally {
            try { in.close(); } catch (Exception ex) {}
        }
    }
}
