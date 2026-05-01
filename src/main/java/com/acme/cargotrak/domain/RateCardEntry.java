package com.acme.cargotrak.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class RateCardEntry implements Serializable {

    private Integer entryId;
    private RateCard rateCard;
    private Integer routeId;
    private String vehicleType;
    private BigDecimal baseCharge;
    private BigDecimal perKmRate;
    private BigDecimal perKgRate;

    public Integer getEntryId() { return entryId; }
    public void setEntryId(Integer entryId) { this.entryId = entryId; }
    public RateCard getRateCard() { return rateCard; }
    public void setRateCard(RateCard rateCard) { this.rateCard = rateCard; }
    public Integer getRouteId() { return routeId; }
    public void setRouteId(Integer routeId) { this.routeId = routeId; }
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public BigDecimal getBaseCharge() { return baseCharge; }
    public void setBaseCharge(BigDecimal baseCharge) { this.baseCharge = baseCharge; }
    public BigDecimal getPerKmRate() { return perKmRate; }
    public void setPerKmRate(BigDecimal perKmRate) { this.perKmRate = perKmRate; }
    public BigDecimal getPerKgRate() { return perKgRate; }
    public void setPerKgRate(BigDecimal perKgRate) { this.perKgRate = perKgRate; }
}
