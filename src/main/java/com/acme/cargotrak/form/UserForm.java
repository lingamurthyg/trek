package com.acme.cargotrak.form;

import org.apache.struts.action.ActionForm;

public class UserForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private Integer userId;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String activeFlag;
    private String[] roleNames;

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getActiveFlag() { return activeFlag; }
    public void setActiveFlag(String activeFlag) { this.activeFlag = activeFlag; }
    public String[] getRoleNames() { return roleNames; }
    public void setRoleNames(String[] roleNames) { this.roleNames = roleNames; }
}
