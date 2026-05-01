package com.acme.cargotrak.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.util.Constants;
import com.acme.cargotrak.util.Util;

/**
 * Lists files under /var/app/cargo/{reports,uploads,inbound,archive}.
 *
 * @author S. Patel 2014-06 (added so ops doesn't have to ssh in to check report
 *                          generation)
 */
public class FilesAction extends BaseAction {

    private static final Map<String, String> DIRS = new LinkedHashMap<String, String>();
    static {
        DIRS.put("reports",  Constants.REPORTS_DIR);
        DIRS.put("uploads",  Constants.UPLOADS_DIR);
        DIRS.put("inbound",  Constants.INBOUND_DIR);
        DIRS.put("archive",  Constants.ARCHIVE_DIR);
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {

        String dirKey = Util.nvl(req.getParameter("dir"), "reports").toLowerCase();
        if (!DIRS.containsKey(dirKey)) dirKey = "reports";
        String basePath = DIRS.get(dirKey);

        // Optional sub-path (used to drill into archive/<timestamp>/ folders).
        String sub = Util.nvl(req.getParameter("sub"), "");
        // Defensive: no traversal.
        if (sub.indexOf("..") >= 0 || sub.startsWith("/") || sub.startsWith("\\")) {
            sub = "";
        }

        File browseDir = sub.length() > 0 ? new File(basePath, sub) : new File(basePath);
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> dirs = new ArrayList<Map<String, Object>>();
        long totalBytes = 0;

        if (browseDir.exists() && browseDir.isDirectory()) {
            File[] entries = browseDir.listFiles();
            if (entries != null) {
                Arrays.sort(entries, new Comparator<File>() {
                    public int compare(File a, File b) {
                        return Long.valueOf(b.lastModified()).compareTo(Long.valueOf(a.lastModified()));
                    }
                });
                for (int i = 0; i < entries.length; i++) {
                    File f = entries[i];
                    Map<String, Object> row = new HashMap<String, Object>();
                    row.put("name", f.getName());
                    row.put("size", Long.valueOf(f.length()));
                    row.put("sizeHuman", humanSize(f.length()));
                    row.put("modified", new java.util.Date(f.lastModified()));
                    row.put("isDir", Boolean.valueOf(f.isDirectory()));
                    row.put("isText", Boolean.valueOf(isTextFile(f)));
                    row.put("isViewable", Boolean.valueOf(isTextFile(f) && f.length() < 1024 * 1024 && !f.isDirectory()));
                    if (f.isDirectory()) {
                        dirs.add(row);
                    } else {
                        rows.add(row);
                        totalBytes += f.length();
                    }
                }
            }
        }

        req.setAttribute("dirKey",   dirKey);
        req.setAttribute("dirPath",  basePath);
        req.setAttribute("sub",      sub);
        req.setAttribute("browseDirAbs", browseDir.getAbsolutePath());
        req.setAttribute("dirTabs",  new ArrayList<String>(DIRS.keySet()));
        req.setAttribute("dirs",     dirs);
        req.setAttribute("rows",     rows);
        req.setAttribute("totalBytes",     Long.valueOf(totalBytes));
        req.setAttribute("totalBytesHuman", humanSize(totalBytes));

        return mapping.findForward("success");
    }

    private static boolean isTextFile(File f) {
        String n = f.getName().toLowerCase();
        return n.endsWith(".csv") || n.endsWith(".txt") || n.endsWith(".edi204")
            || n.endsWith(".edi214") || n.endsWith(".log") || n.endsWith(".xml")
            || n.endsWith(".json") || n.endsWith(".sql") || n.endsWith(".html")
            || n.indexOf(".rejections") >= 0;
    }

    private static String humanSize(long bytes) {
        if (bytes < 1024)        return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        if (bytes < 1024L * 1024 * 1024) return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
        return String.format("%.1f GB", bytes / (1024.0 * 1024.0 * 1024.0));
    }
}
