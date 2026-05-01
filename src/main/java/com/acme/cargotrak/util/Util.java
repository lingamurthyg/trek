package com.acme.cargotrak.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * The Util grab-bag.
 *
 * @author Rajesh Kumar 2008-12 (original)
 * @author M. Iyer 2010-04 (added file helpers)
 * @author L. Chen 2012-09 (random + http helpers)
 * @author S. Patel 2014-03 (csv stuff)
 *
 * Migrated for Java 21:
 *  - SimpleDateFormat instances wrapped in ThreadLocal to fix thread-safety issue
 *  - new Integer(v) -> Integer.valueOf(v) (deprecated primitive wrapper constructor removed in Java 21)
 *
 * Don't add to this class. (Said every author. It has 40+ static methods.)
 */
public class Util {

    private static final Logger logger = Logger.getLogger(Util.class);

    // ThreadLocal SimpleDateFormat instances to fix thread-safety issue (Java 21 compatible pattern)
    private static final ThreadLocal<SimpleDateFormat> DTF = ThreadLocal.withInitial(
        () -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    private static final ThreadLocal<SimpleDateFormat> YMD = ThreadLocal.withInitial(
        () -> new SimpleDateFormat("yyyy-MM-dd"));

    // static mutable cache, no synchronization. accessed from action classes and jobs.
    private static Map<String, Object> CACHE = new HashMap<String, Object>();

    private static final Random RAND = new Random();

    private Util() {}

    // ----- string helpers -----

    public static boolean isBlank(String s) {
        return s == null || s.length() == 0 || s.trim().length() == 0;
    }

    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }

    public static String nvl(String s, String def) {
        return s == null ? def : s;
    }

    public static String trim(String s) {
        return s == null ? null : s.trim();
    }

    public static String lower(String s) {
        return s == null ? null : s.toLowerCase();
    }

    public static String upper(String s) {
        return s == null ? null : s.toUpperCase();
    }

    public static String escapeHtml(String s) {
        if (s == null) return "";
        StringBuffer sb = new StringBuffer(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '<': sb.append("&lt;"); break;
                case '>': sb.append("&gt;"); break;
                case '&': sb.append("&amp;"); break;
                case '"': sb.append("&quot;"); break;
                case '\'': sb.append("&#39;"); break;
                default: sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String pad(String s, int len, char c) {
        if (s == null) s = "";
        StringBuffer sb = new StringBuffer(s);
        while (sb.length() < len) sb.append(c);
        if (sb.length() > len) return sb.substring(0, len);
        return sb.toString();
    }

    public static String leftPad(String s, int len, char c) {
        if (s == null) s = "";
        StringBuffer sb = new StringBuffer();
        while (sb.length() + s.length() < len) sb.append(c);
        sb.append(s);
        return sb.toString();
    }

    public static String join(Object[] items, String sep) {
        if (items == null) return "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < items.length; i++) {
            if (i > 0) sb.append(sep);
            sb.append(items[i] == null ? "" : items[i].toString());
        }
        return sb.toString();
    }

    public static String[] splitCsv(String line) {
        if (line == null) return new String[0];
        // dumb csv split, doesn't handle escaped quotes. spatel knows.
        return line.split(",");
    }

    // ----- date helpers (ThreadLocal SimpleDateFormat for thread safety) -----

    public static String formatDateTime(Date d) {
        if (d == null) return "";
        return DTF.get().format(d);
    }

    public static String formatDate(Date d) {
        if (d == null) return "";
        return YMD.get().format(d);
    }

    public static Date parseDateTime(String s) {
        if (s == null || s.length() == 0) return null;
        try {
            return DTF.get().parse(s);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parseDate(String s) {
        if (s == null || s.length() == 0) return null;
        try {
            return YMD.get().parse(s);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date today() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date addDays(Date d, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public static int daysBetween(Date a, Date b) {
        long diff = b.getTime() - a.getTime();
        return (int) (diff / (1000L * 60 * 60 * 24));
    }

    // ----- number helpers -----

    public static String formatMoney(BigDecimal v) {
        if (v == null) return "";
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(v);
    }

    public static BigDecimal nz(BigDecimal v) {
        return v == null ? BigDecimal.ZERO : v;
    }

    // ----- file helpers -----

    public static byte[] readFile(File f) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            byte[] buf = new byte[(int) f.length()];
            int read = 0;
            while (read < buf.length) {
                int n = fis.read(buf, read, buf.length - read);
                if (n < 0) break;
                read += n;
            }
            return buf;
        } finally {
            try { if (fis != null) fis.close(); } catch (Exception ex) {}
        }
    }

    public static void writeFile(File f, byte[] data) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(data);
            fos.flush();
        } finally {
            try { if (fos != null) fos.close(); } catch (Exception ex) {}
        }
    }

    public static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[4096];
        int n;
        while ((n = in.read(buf)) > 0) {
            out.write(buf, 0, n);
        }
    }

    public static Properties loadProperties(String path) {
        Properties p = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            p.load(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }

    public static void mkdirs(String path) {
        File f = new File(path);
        if (!f.exists()) {
            if (!f.mkdirs()) {
                logger.warn("Could not create dir: " + path);
            }
        }
    }

    public static String readTextFile(File f) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
            return sb.toString();
        } finally {
            try { if (br != null) br.close(); } catch (Exception ex) {}
        }
    }

    // ----- security helpers -----

    /**
     * MD5 hash - retained for backward compatibility with existing password hashes in DB.
     * NOTE: MD5 is cryptographically broken; new passwords should use BCrypt or SHA-256.
     */
    public static String md5(String s) {
        if (s == null) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] b = md.digest(s.getBytes("UTF-8"));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                String h = Integer.toHexString(b[i] & 0xff);
                if (h.length() == 1) sb.append('0');
                sb.append(h);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateTempPassword() {
        String chars = "abcdefghijkmnpqrstuvwxyz23456789";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sb.append(chars.charAt(RAND.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static String generateTrackingNo() {
        Calendar c = Calendar.getInstance();
        StringBuffer sb = new StringBuffer("TRK-");
        sb.append(c.get(Calendar.YEAR));
        sb.append(leftPad(String.valueOf(c.get(Calendar.MONTH) + 1), 2, '0'));
        sb.append(leftPad(String.valueOf(c.get(Calendar.DAY_OF_MONTH)), 2, '0'));
        sb.append('-');
        sb.append(leftPad(String.valueOf(RAND.nextInt(100000)), 5, '0'));
        return sb.toString();
    }

    public static String generateInvoiceNo() {
        Calendar c = Calendar.getInstance();
        StringBuffer sb = new StringBuffer("INV-");
        sb.append(c.get(Calendar.YEAR));
        sb.append(leftPad(String.valueOf(c.get(Calendar.MONTH) + 1), 2, '0'));
        sb.append('-');
        sb.append(leftPad(String.valueOf(RAND.nextInt(1000000)), 6, '0'));
        return sb.toString();
    }

    // ----- http helpers -----

    public static String getRemoteIp(HttpServletRequest req) {
        String h = req.getHeader("X-Forwarded-For");
        if (h != null && h.length() > 0) return h.split(",")[0].trim();
        return req.getRemoteAddr();
    }

    public static String paramOrDefault(HttpServletRequest req, String name, String def) {
        String v = req.getParameter(name);
        return (v == null || v.length() == 0) ? def : v;
    }

    public static int paramAsInt(HttpServletRequest req, String name, int def) {
        try {
            String v = req.getParameter(name);
            if (v == null || v.length() == 0) return def;
            return Integer.parseInt(v);
        } catch (Exception e) {
            return def;
        }
    }

    public static Integer paramAsInteger(HttpServletRequest req, String name) {
        try {
            String v = req.getParameter(name);
            if (v == null || v.length() == 0) return null;
            // Use Integer.valueOf() instead of deprecated new Integer() constructor (removed in Java 21)
            return Integer.valueOf(v);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date paramAsDate(HttpServletRequest req, String name) {
        return parseDate(req.getParameter(name));
    }

    // ----- cache helpers -----

    public static Object cacheGet(String key) {
        return CACHE.get(key);
    }

    public static void cachePut(String key, Object value) {
        CACHE.put(key, value);
    }

    public static void cacheInvalidate(String key) {
        CACHE.remove(key);
    }

    public static void cacheClearAll() {
        CACHE.clear();
    }

    // ----- template substitution (used by MailService) -----

    public static String interpolate(String template, Map<String, ?> params) {
        if (template == null) return null;
        String out = template;
        if (params != null) {
            for (Map.Entry<String, ?> e : params.entrySet()) {
                String k = "${" + e.getKey() + "}";
                String v = e.getValue() == null ? "" : e.getValue().toString();
                int idx;
                while ((idx = out.indexOf(k)) >= 0) {
                    out = out.substring(0, idx) + v + out.substring(idx + k.length());
                }
            }
        }
        return out;
    }
}
