package com.acme.cargotrak.ingestion;

import java.math.BigDecimal;
import java.util.Date;

public class ParsedShipmentRow {

    private String customerCode;
    private String origin;
    private String destination;
    private BigDecimal weightKg;
    private BigDecimal volumeM3;
    private BigDecimal declaredValue;
    private Date pickupDate;
    private String routeCode;
    private String notes;

    public String getCustomerCode() { return customerCode; }
    public void setCustomerCode(String s) { this.customerCode = s; }
    public String getOrigin() { return origin; }
    public void setOrigin(String s) { this.origin = s; }
    public String getDestination() { return destination; }
    public void setDestination(String s) { this.destination = s; }
    public BigDecimal getWeightKg() { return weightKg; }
    public void setWeightKg(BigDecimal w) { this.weightKg = w; }
    public BigDecimal getVolumeM3() { return volumeM3; }
    public void setVolumeM3(BigDecimal v) { this.volumeM3 = v; }
    public BigDecimal getDeclaredValue() { return declaredValue; }
    public void setDeclaredValue(BigDecimal d) { this.declaredValue = d; }
    public Date getPickupDate() { return pickupDate; }
    public void setPickupDate(Date d) { this.pickupDate = d; }
    public String getRouteCode() { return routeCode; }
    public void setRouteCode(String s) { this.routeCode = s; }
    public String getNotes() { return notes; }
    public void setNotes(String s) { this.notes = s; }
}
