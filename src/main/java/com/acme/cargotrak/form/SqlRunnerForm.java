package com.acme.cargotrak.form;

import org.apache.struts.action.ActionForm;

public class SqlRunnerForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private String sql;
    private String mode = "QUERY"; // QUERY or UPDATE

    public String getSql() { return sql; }
    public void setSql(String s) { this.sql = s; }
    public String getMode() { return mode; }
    public void setMode(String s) { this.mode = s; }
}
