package com.acme.cargotrak.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.acme.cargotrak.util.Constants;
import com.acme.cargotrak.util.Util;

/**
 * Serves files from /var/app/cargo/{reports,uploads,inbound,archive} by name.
 *
 * URLs:
 *   /download?name=invoice-INV-000123.pdf
 *   /download?dir=reports&name=ar-aging-1714349765.pdf
 *   /download?dir=uploads&name=manifest_basic-1714349765.csv
 *   /download?dir=archive&sub=20260429001234&name=status_updates_001-1714349765.edi214
 *
 * Defaults to dir=reports for backward-compat with older links.
 */
public class FileDownloadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Map<String, String> DIRS = new HashMap<String, String>();
    static {
        DIRS.put("reports", Constants.REPORTS_DIR);
        DIRS.put("uploads", Constants.UPLOADS_DIR);
        DIRS.put("inbound", Constants.INBOUND_DIR);
        DIRS.put("archive", Constants.ARCHIVE_DIR);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String dirKey = Util.nvl(req.getParameter("dir"), "reports").toLowerCase();
        String basePath = DIRS.get(dirKey);
        if (basePath == null) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "bad dir");
            return;
        }
        String sub = Util.nvl(req.getParameter("sub"), "");
        if (sub.indexOf("..") >= 0 || sub.startsWith("/") || sub.startsWith("\\")) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "bad sub");
            return;
        }
        String name = req.getParameter("name");
        if (Util.isBlank(name) || name.indexOf("..") >= 0
                || name.indexOf('/') >= 0 || name.indexOf('\\') >= 0) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "bad name");
            return;
        }
        File f = sub.length() > 0
                ? new File(new File(basePath, sub), name)
                : new File(basePath, name);
        if (!f.exists() || !f.isFile()) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "not found");
            return;
        }
        String n = name.toLowerCase();
        if      (n.endsWith(".pdf"))     res.setContentType("application/pdf");
        else if (n.endsWith(".xls"))     res.setContentType("application/vnd.ms-excel");
        else if (n.endsWith(".xlsx"))    res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        else if (n.endsWith(".csv"))     res.setContentType("text/csv");
        else if (n.endsWith(".txt"))     res.setContentType("text/plain;charset=UTF-8");
        else if (n.endsWith(".log"))     res.setContentType("text/plain;charset=UTF-8");
        else if (n.endsWith(".edi204"))  res.setContentType("text/plain;charset=UTF-8");
        else if (n.endsWith(".edi214"))  res.setContentType("text/plain;charset=UTF-8");
        else                              res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
        FileInputStream fis = null;
        OutputStream out = null;
        try {
            fis = new FileInputStream(f);
            out = res.getOutputStream();
            Util.copy(fis, out);
        } finally {
            try { if (fis != null) fis.close(); } catch (Exception ex) {}
        }
    }
}
