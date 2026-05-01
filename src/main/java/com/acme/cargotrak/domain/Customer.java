package com.acme.cargotrak.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Rajesh Kumar
 * @author M. Iyer (offshore) - 2010 cleanup
 */
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer customerId;
    private String customerCode;
    private String name;
    private String industry;
    private BigDecimal creditLimit;
    private String paymentTerms;
    private String activeFlag;
    private Date createdDate;

    private Set contacts = new HashSet();
    private Set addresses = new HashSet();

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public String getCustomerCode() { return customerCode; }
    public void setCustomerCode(String customerCode) { this.customerCode = customerCode; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }
    public BigDecimal getCreditLimit() { return creditLimit; }
    public void setCreditLimit(BigDecimal creditLimit) { this.creditLimit = creditLimit; }
    public String getPaymentTerms() { return paymentTerms; }
    public void setPaymentTerms(String paymentTerms) { this.paymentTerms = paymentTerms; }
    public String getActiveFlag() { return activeFlag; }
    public void setActiveFlag(String activeFlag) { this.activeFlag = activeFlag; }
    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
    public Set getContacts() { return contacts; }
    public void setContacts(Set contacts) { this.contacts = contacts; }
    public Set getAddresses() { return addresses; }
    public void setAddresses(Set addresses) { this.addresses = addresses; }
}
