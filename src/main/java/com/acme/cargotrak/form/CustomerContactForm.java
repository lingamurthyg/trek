package com.acme.cargotrak.form;

import org.apache.struts.action.ActionForm;

public class CustomerContactForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private Integer contactId;
    private Integer customerId;
    private String contactName;
    private String title;
    private String email;
    private String phone;
    private String primaryFlag;

    public Integer getContactId() { return contactId; }
    public void setContactId(Integer contactId) { this.contactId = contactId; }
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
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
