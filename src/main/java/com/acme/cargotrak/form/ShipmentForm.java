package com.acme.cargotrak.form;

import java.math.BigDecimal;

import org.apache.struts.action.ActionForm;

public class ShipmentForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private Integer shipmentId;
    private String trackingNo;
    private Integer customerId;
    private String origin;
    private String destination;
    private BigDecimal weightKg;
    private BigDecimal volumeM3;
    private BigDecimal declaredValue;
    private String status;
    private Integer driverId;
    private Integer vehicleId;
    private Integer routeId;
    private Integer rateCardId;
    private String notes;

    private String trackingFragment;
    private String fromDate;
    private String toDate;
    private int page = 0;
    private int pageSize = 25;

    private String newStatus;

    public Integer getShipmentId() { return shipmentId; }
    public void setShipmentId(Integer shipmentId) { this.shipmentId = shipmentId; }
    public String getTrackingNo() { return trackingNo; }
    public void setTrackingNo(String trackingNo) { this.trackingNo = trackingNo; }
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public BigDecimal getWeightKg() { return weightKg; }
    public void setWeightKg(BigDecimal weightKg) { this.weightKg = weightKg; }
    public BigDecimal getVolumeM3() { return volumeM3; }
    public void setVolumeM3(BigDecimal volumeM3) { this.volumeM3 = volumeM3; }
    public BigDecimal getDeclaredValue() { return declaredValue; }
    public void setDeclaredValue(BigDecimal declaredValue) { this.declaredValue = declaredValue; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getDriverId() { return driverId; }
    public void setDriverId(Integer driverId) { this.driverId = driverId; }
    public Integer getVehicleId() { return vehicleId; }
    public void setVehicleId(Integer vehicleId) { this.vehicleId = vehicleId; }
    public Integer getRouteId() { return routeId; }
    public void setRouteId(Integer routeId) { this.routeId = routeId; }
    public Integer getRateCardId() { return rateCardId; }
    public void setRateCardId(Integer rateCardId) { this.rateCardId = rateCardId; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getTrackingFragment() { return trackingFragment; }
    public void setTrackingFragment(String s) { this.trackingFragment = s; }
    public String getFromDate() { return fromDate; }
    public void setFromDate(String s) { this.fromDate = s; }
    public String getToDate() { return toDate; }
    public void setToDate(String s) { this.toDate = s; }
    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }
    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }
    public String getNewStatus() { return newStatus; }
    public void setNewStatus(String newStatus) { this.newStatus = newStatus; }
}
