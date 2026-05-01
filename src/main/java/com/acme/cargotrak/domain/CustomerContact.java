package com.acme.cargotrak.domain;

import java.io.Serializable;

public class CustomerContact implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer contactId;
    private Customer customer;
    private String contactName;
    private String title;
    private String email;
    private String phone;
    private String primaryFlag;

    public Integer getContactId() { return contactId; }
    public void setContactId(Integer contactId) { this.contactId = contactId; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPrimaryFlag() { return primaryFlag; }
    public void setPrimaryFlag(String primaryFlag) { this.primaryFlag = primaryFlag; }
}
