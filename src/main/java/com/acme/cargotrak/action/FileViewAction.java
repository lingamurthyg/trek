package com.acme.cargotrak.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.util.Constants;
import com.acme.cargotrak.util.Util;

/**
 * Renders a text file from /var/app/cargo/{reports,uploads,inbound,archive}
 * inline in the browser inside a &lt;pre&gt; block.
 */
public class FileViewAction extends BaseAction {

    private static final Map<String, String> DIRS = new LinkedHashMap<String, String>();
    static {
        DIRS.put("reports", Constants.REPORTS_DIR);
        DIRS.put("uploads", Constants.UPLOADS_DIR);
        DIRS.put("inbound", Constants.INBOUND_DIR);
        DIRS.put("archive", Constants.ARCHIVE_DIR);
    }

    private static final long MAX_VIEW_BYTES = 1024L * 1024L; // 1 MB

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {

        String dirKey = Util.nvl(req.getParameter("dir"), "reports").toLowerCase();
        if (!DIRS.containsKey(dirKey)) dirKey = "reports";
        String basePath = DIRS.get(dirKey);

        String sub = Util.nvl(req.getParameter("sub"), "");
        if (sub.indexOf("..") >= 0 || sub.startsWith("/") || sub.startsWith("\\")) sub = "";

        String name = Util.nvl(req.getParameter("name"), "");
        if (Util.isBlank(name) || name.indexOf("..") >= 0
                || name.indexOf('/') >= 0 || name.indexOf('\\') >= 0) {
            req.setAttribute("error", "Invalid file name");
            return mapping.findForward("success");
        }

        File f = sub.length() > 0
                ? new File(new File(basePath, sub), name)
                : new File(basePath, name);
        if (!f.exists() || !f.isFile()) {
            req.setAttribute("error", "File not found: " + f.getAbsolutePath());
            return mapping.findForward("success");
        }

        StringBuffer sb = new StringBuffer();
        boolean truncated = false;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            char[] buf = new char[8192];
            long total = 0;
            int n;
            while ((n = br.read(buf)) > 0) {
                if (total + n > MAX_VIEW_BYTES) {
                    sb.append(buf, 0, (int)(MAX_VIEW_BYTES - total));
                    truncated = true;
                    break;
                }
                sb.append(buf, 0, n);
                total += n;
            }
        } catch (Exception e) {
            req.setAttribute("error", "Could not read file: " + e.getMessage());
            return mapping.findForward("success");
        } finally {
            try { if (br != null) br.close(); } catch (Exception ex) {}
        }

        req.setAttribute("dirKey",   dirKey);
        req.setAttribute("sub",      sub);
        req.setAttribute("fileName", f.getName());
        req.setAttribute("fileAbs",  f.getAbsolutePath());
        req.setAttribute("fileSize", Long.valueOf(f.length()));
        req.setAttribute("content",  sb.toString());
        req.setAttribute("truncated", Boolean.valueOf(truncated));
        return mapping.findForward("success");
    }
}
