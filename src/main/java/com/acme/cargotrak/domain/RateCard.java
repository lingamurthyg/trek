package com.acme.cargotrak.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class RateCard implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer rateCardId;
    private String name;
    private Integer customerId;
    private String effectiveDate;
    private String expiryDate;
    private String activeFlag;

    private Set entries = new HashSet();

    public Integer getRateCardId() { return rateCardId; }
    public void setRateCardId(Integer rateCardId) { this.rateCardId = rateCardId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public String getEffectiveDate() { return effectiveDate; }
    public void setEffectiveDate(String effectiveDate) { this.effectiveDate = effectiveDate; }
    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }
    public String getActiveFlag() { return activeFlag; }
    public void setActiveFlag(String activeFlag) { this.activeFlag = activeFlag; }
    public Set getEntries() { return entries; }
    public void setEntries(Set entries) { this.entries = entries; }
}
