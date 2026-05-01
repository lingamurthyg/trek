package com.acme.cargotrak.form;

import java.math.BigDecimal;

import org.apache.struts.action.ActionForm;

public class PaymentForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private Integer paymentId;
    private Integer customerId;
    private Integer invoiceId;
    private BigDecimal amount;
    private String method;
    private String reference;
    private String notes;

    public Integer getPaymentId() { return paymentId; }
    public void setPaymentId(Integer p) { this.paymentId = p; }
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer c) { this.customerId = c; }
    public Integer getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Integer i) { this.invoiceId = i; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal a) { this.amount = a; }
    public String getMethod() { return method; }
    public void setMethod(String s) { this.method = s; }
    public String getReference() { return reference; }
    public void setReference(String s) { this.reference = s; }
    public String getNotes() { return notes; }
    public void setNotes(String s) { this.notes = s; }
}
