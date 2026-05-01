package com.acme.cargotrak.form;

import org.apache.struts.action.ActionForm;

public class LoginForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String next;

    public String getUsername() { return username; }
    public void setUsername(String u) { this.username = u; }
    public String getPassword() { return password; }
    public void setPassword(String p) { this.password = p; }
    public String getNext() { return next; }
    public void setNext(String n) { this.next = n; }
}
