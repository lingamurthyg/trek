package com.acme.cargotrak.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Date helpers.
 *
 * Migrated for Java 21:
 *  - SimpleDateFormat instances wrapped in ThreadLocal to fix thread-safety issue
 *
 * @author M. Iyer 2010-04
 */
public class DateUtils {

    // ThreadLocal SimpleDateFormat instances for thread safety (Java 21 compatible pattern)
    private static final ThreadLocal<SimpleDateFormat> ISO  = ThreadLocal.withInitial(
        () -> new SimpleDateFormat("yyyy-MM-dd"));
    private static final ThreadLocal<SimpleDateFormat> FULL = ThreadLocal.withInitial(
        () -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    private static final ThreadLocal<SimpleDateFormat> US   = ThreadLocal.withInitial(
        () -> new SimpleDateFormat("MM/dd/yyyy"));

    public static String iso(Date d) { return d == null ? "" : ISO.get().format(d); }
    public static String full(Date d) { return d == null ? "" : FULL.get().format(d); }
    public static String us(Date d) { return d == null ? "" : US.get().format(d); }

    public static Date parseIso(String s) {
        try { return s == null ? null : ISO.get().parse(s); }
        catch (Exception e) { return null; }
    }

    public static Date startOfDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date endOfDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    public static Date addDays(Date d, int days) { return Util.addDays(d, days); }
}
