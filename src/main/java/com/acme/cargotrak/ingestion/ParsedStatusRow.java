package com.acme.cargotrak.ingestion;

import java.util.Date;

public class ParsedStatusRow {

    private String trackingNo;
    private String newStatus;
    private Date timestamp;

    public String getTrackingNo() { return trackingNo; }
    public void setTrackingNo(String s) { this.trackingNo = s; }
    public String getNewStatus() { return newStatus; }
    public void setNewStatus(String s) { this.newStatus = s; }
    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date d) { this.timestamp = d; }
}
