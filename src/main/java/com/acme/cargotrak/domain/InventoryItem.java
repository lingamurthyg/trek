package com.acme.cargotrak.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class InventoryItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer itemId;
    private String sku;
    private String description;
    private Integer zoneId;
    private Integer quantity;
    private BigDecimal weightKg;
    private Date lastUpdated;

    public Integer getItemId() { return itemId; }
    public void setItemId(Integer itemId) { this.itemId = itemId; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getZoneId() { return zoneId; }
    public void setZoneId(Integer zoneId) { this.zoneId = zoneId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getWeightKg() { return weightKg; }
    public void setWeightKg(BigDecimal weightKg) { this.weightKg = weightKg; }
    public Date getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(Date lastUpdated) { this.lastUpdated = lastUpdated; }
}
