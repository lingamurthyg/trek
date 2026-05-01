package com.acme.cargotrak.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer invoiceId;
    private String invoiceNo;
    private Integer customerId;
    private Date invoiceDate;
    private Date dueDate;
    private String status;
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal total;
    private BigDecimal amountPaid;

    private Set lineItems = new HashSet();

    public Integer getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Integer invoiceId) { this.invoiceId = invoiceId; }
    public String getInvoiceNo() { return invoiceNo; }
    public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public Date getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(Date invoiceDate) { this.invoiceDate = invoiceDate; }
    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    public BigDecimal getTax() { return tax; }
    public void setTax(BigDecimal tax) { this.tax = tax; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public BigDecimal getAmountPaid() { return amountPaid; }
    public void setAmountPaid(BigDecimal amountPaid) { this.amountPaid = amountPaid; }
    public Set getLineItems() { return lineItems; }
    public void setLineItems(Set lineItems) { this.lineItems = lineItems; }

    public BigDecimal getOutstanding() {
        if (total == null) return BigDecimal.ZERO;
        BigDecimal paid = amountPaid == null ? BigDecimal.ZERO : amountPaid;
        return total.subtract(paid);
    }
}
