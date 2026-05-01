package com.acme.cargotrak.form;

import org.apache.struts.action.ActionForm;

public class ReportForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private String fromDate;
    private String toDate;
    private String asOfDate;
    private String format;

    public String getFromDate() { return fromDate; }
    public void setFromDate(String s) { this.fromDate = s; }
    public String getToDate() { return toDate; }
    public void setToDate(String s) { this.toDate = s; }
    public String getAsOfDate() { return asOfDate; }
    public void setAsOfDate(String s) { this.asOfDate = s; }
    public String getFormat() { return format; }
    public void setFormat(String s) { this.format = s; }
}
