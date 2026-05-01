package com.acme.cargotrak.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class PaymentAllocation implements Serializable {

    private Integer allocationId;
    private Integer paymentId;
    private Integer invoiceId;
    private BigDecimal amount;

    public Integer getAllocationId() { return allocationId; }
    public void setAllocationId(Integer allocationId) { this.allocationId = allocationId; }
    public Integer getPaymentId() { return paymentId; }
    public void setPaymentId(Integer paymentId) { this.paymentId = paymentId; }
    public Integer getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Integer invoiceId) { this.invoiceId = invoiceId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
