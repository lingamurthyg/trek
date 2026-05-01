package com.acme.cargotrak.form;

import org.apache.struts.action.ActionForm;

public class InvoiceForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private Integer invoiceId;
    private String invoiceNo;
    private Integer customerId;
    private String status;

    private String fragment;
    private String fromDate;
    private String toDate;
    private int page = 0;
    private int pageSize = 25;

    private Integer shipmentId;

    public Integer getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Integer i) { this.invoiceId = i; }
    public String getInvoiceNo() { return invoiceNo; }
    public void setInvoiceNo(String s) { this.invoiceNo = s; }
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer c) { this.customerId = c; }
    public String getStatus() { return status; }
    public void setStatus(String s) { this.status = s; }
    public String getFragment() { return fragment; }
    public void setFragment(String f) { this.fragment = f; }
    public String getFromDate() { return fromDate; }
    public void setFromDate(String s) { this.fromDate = s; }
    public String getToDate() { return toDate; }
    public void setToDate(String s) { this.toDate = s; }
    public int getPage() { return page; }
    public void setPage(int p) { this.page = p; }
    public int getPageSize() { return pageSize; }
    public void setPageSize(int p) { this.pageSize = p; }
    public Integer getShipmentId() { return shipmentId; }
    public void setShipmentId(Integer s) { this.shipmentId = s; }
}
