package com.acme.cargotrak.form;

import org.apache.struts.action.ActionForm;

public class PasswordResetForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private String username;
    private String oldPassword;
    private String newPassword;

    public String getUsername() { return username; }
    public void setUsername(String s) { this.username = s; }
    public String getOldPassword() { return oldPassword; }
    public void setOldPassword(String s) { this.oldPassword = s; }
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String s) { this.newPassword = s; }
}
