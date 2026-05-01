package com.acme.cargotrak.form;

import org.apache.struts.action.ActionForm;

public class SystemConfigForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private String configKey;
    private String configValue;
    private String description;

    public String getConfigKey() { return configKey; }
    public void setConfigKey(String s) { this.configKey = s; }
    public String getConfigValue() { return configValue; }
    public void setConfigValue(String s) { this.configValue = s; }
    public String getDescription() { return description; }
    public void setDescription(String s) { this.description = s; }
}
