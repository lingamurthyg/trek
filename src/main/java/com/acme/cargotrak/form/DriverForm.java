package com.acme.cargotrak.form;

import org.apache.struts.action.ActionForm;

public class DriverForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private Integer driverId;
    private String employeeCode;
    private String fullName;
    private String licenseNumber;
    private String licenseExpiry;
    private String phone;
    private String activeFlag;

    public Integer getDriverId() { return driverId; }
    public void setDriverId(Integer driverId) { this.driverId = driverId; }
    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String s) { this.employeeCode = s; }
    public String getFullName() { return fullName; }
    public void setFullName(String s) { this.fullName = s; }
    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String s) { this.licenseNumber = s; }
    public String getLicenseExpiry() { return licenseExpiry; }
    public void setLicenseExpiry(String s) { this.licenseExpiry = s; }
    public String getPhone() { return phone; }
    public void setPhone(String s) { this.phone = s; }
    public String getActiveFlag() { return activeFlag; }
    public void setActiveFlag(String s) { this.activeFlag = s; }
}
