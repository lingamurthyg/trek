package com.acme.cargotrak.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Date helpers. Yes, the SimpleDateFormat is shared. Has been since 2008.
 *
 * @author M. Iyer 2010-04
 */
public class DateUtils {

    // Shared, classic mistake.
    private static final SimpleDateFormat ISO  = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat FULL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat US   = new SimpleDateFormat("MM/dd/yyyy");

    public static String iso(Date d) { return d == null ? "" : ISO.format(d); }
    public static String full(Date d) { return d == null ? "" : FULL.format(d); }
    public static String us(Date d) { return d == null ? "" : US.format(d); }

    public static Date parseIso(String s) {
        try { return s == null ? null : ISO.parse(s); }
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
