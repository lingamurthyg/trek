package com.acme.cargotrak.domain;

import java.io.Serializable;
import java.util.Date;

public class SystemConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    private String configKey;
    private String configValue;
    private String description;
    private Date updatedAt;

    public String getConfigKey() { return configKey; }
    public void setConfigKey(String configKey) { this.configKey = configKey; }
    public String getConfigValue() { return configValue; }
    public void setConfigValue(String configValue) { this.configValue = configValue; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
