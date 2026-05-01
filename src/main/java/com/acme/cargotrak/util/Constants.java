package com.acme.cargotrak.util;

public class Constants {

    public static final String SESSION_USER = "currentUser";
    public static final String SESSION_USER_ID = "currentUserId";
    public static final String COOKIE_USER_ID = "userId";

    public static final String UPLOADS_DIR = "/var/app/cargo/uploads";
    public static final String REPORTS_DIR = "/var/app/cargo/reports";
    public static final String LOGS_DIR    = "/var/app/cargo/logs";
    public static final String INBOUND_DIR = "/var/app/cargo/inbound";
    public static final String ARCHIVE_DIR = "/var/app/cargo/archive";

    public static final String STATUS_DRAFT      = "DRAFT";
    public static final String STATUS_BOOKED     = "BOOKED";
    public static final String STATUS_IN_TRANSIT = "IN_TRANSIT";
    public static final String STATUS_DELIVERED  = "DELIVERED";
    public static final String STATUS_INVOICED   = "INVOICED";
    public static final String STATUS_CLOSED     = "CLOSED";

    public static final String TPL_PASSWORD_RESET    = "PASSWORD_RESET";
    public static final String TPL_SHIPMENT_DELIVERED = "SHIPMENT_DELIVERED";
    public static final String TPL_INVOICE_GENERATED = "INVOICE_GENERATED";
    public static final String TPL_AR_AGING_REPORT   = "AR_AGING_REPORT";

    public static final int DEFAULT_PAGE_SIZE = 25;
}
