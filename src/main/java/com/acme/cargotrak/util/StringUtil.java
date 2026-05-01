package com.acme.cargotrak.util;

public class StringUtil {

    public static boolean isBlank(String s) { return Util.isBlank(s); }
    public static boolean isNotBlank(String s) { return Util.isNotBlank(s); }
    public static String escapeHtml(String s) { return Util.escapeHtml(s); }
    public static String trim(String s) { return Util.trim(s); }
    public static String safe(String s) { return s == null ? "" : s; }
    public static String truncate(String s, int len) {
        if (s == null) return "";
        if (s.length() <= len) return s;
        return s.substring(0, len);
    }
}
