package com.acme.cargotrak.form;

import java.math.BigDecimal;

import org.apache.struts.action.ActionForm;

public class InventoryForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private Integer itemId;
    private String sku;
    private String description;
    private Integer zoneId;
    private Integer quantity;
    private BigDecimal weightKg;

    private String fragment;
    private Integer destZoneId;
    private Integer transferQty;

    public Integer getItemId() { return itemId; }
    public void setItemId(Integer i) { this.itemId = i; }
    public String getSku() { return sku; }
    public void setSku(String s) { this.sku = s; }
    public String getDescription() { return description; }
    public void setDescription(String s) { this.description = s; }
    public Integer getZoneId() { return zoneId; }
    public void setZoneId(Integer z) { this.zoneId = z; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer q) { this.quantity = q; }
    public BigDecimal getWeightKg() { return weightKg; }
    public void setWeightKg(BigDecimal w) { this.weightKg = w; }
    public String getFragment() { return fragment; }
    public void setFragment(String f) { this.fragment = f; }
    public Integer getDestZoneId() { return destZoneId; }
    public void setDestZoneId(Integer d) { this.destZoneId = d; }
    public Integer getTransferQty() { return transferQty; }
    public void setTransferQty(Integer t) { this.transferQty = t; }
}
