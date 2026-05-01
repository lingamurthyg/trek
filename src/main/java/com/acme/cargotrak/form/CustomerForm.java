package com.acme.cargotrak.form;

import java.math.BigDecimal;

import org.apache.struts.action.ActionForm;

public class CustomerForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private Integer customerId;
    private String customerCode;
    private String name;
    private String industry;
    private BigDecimal creditLimit;
    private String paymentTerms;
    private String activeFlag;

    private String namePart;
    private int page = 0;
    private int pageSize = 25;

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
    public String getNamePart() { return namePart; }
    public void setNamePart(String namePart) { this.namePart = namePart; }
    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }
    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }
}
