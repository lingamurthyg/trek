package com.acme.cargotrak.form;

import org.apache.struts.action.ActionForm;

public class CustomerAddressForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private Integer addressId;
    private Integer customerId;
    private String addressType;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    public Integer getAddressId() { return addressId; }
    public void setAddressId(Integer a) { this.addressId = a; }
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer c) { this.customerId = c; }
    public String getAddressType() { return addressType; }
    public void setAddressType(String s) { this.addressType = s; }
    public String getLine1() { return line1; }
    public void setLine1(String s) { this.line1 = s; }
    public String getLine2() { return line2; }
    public void setLine2(String s) { this.line2 = s; }
    public String getCity() { return city; }
    public void setCity(String s) { this.city = s; }
    public String getState() { return state; }
    public void setState(String s) { this.state = s; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String s) { this.postalCode = s; }
    public String getCountry() { return country; }
    public void setCountry(String s) { this.country = s; }
}
