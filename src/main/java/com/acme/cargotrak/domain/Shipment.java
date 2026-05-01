package com.acme.cargotrak.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Rajesh Kumar
 * @author L. Chen 2012-08 added rateCardId, totalCharge.
 * @author S. Patel 2014-03 split notes out of description (some shipments had 2KB blobs).
 */
public class Shipment implements Serializable {

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
    private Date bookedDate;
    private Date pickupDate;
    private Date deliveryDate;
    private Integer driverId;
    private Integer vehicleId;
    private Integer routeId;
    private Integer rateCardId;
    private BigDecimal totalCharge;
    private String notes;

    private Set legs = new HashSet();
    private Set statusHistory = new HashSet();
    private Set documents = new HashSet();

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
    public Date getBookedDate() { return bookedDate; }
    public void setBookedDate(Date bookedDate) { this.bookedDate = bookedDate; }
    public Date getPickupDate() { return pickupDate; }
    public void setPickupDate(Date pickupDate) { this.pickupDate = pickupDate; }
    public Date getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(Date deliveryDate) { this.deliveryDate = deliveryDate; }
    public Integer getDriverId() { return driverId; }
    public void setDriverId(Integer driverId) { this.driverId = driverId; }
    public Integer getVehicleId() { return vehicleId; }
    public void setVehicleId(Integer vehicleId) { this.vehicleId = vehicleId; }
    public Integer getRouteId() { return routeId; }
    public void setRouteId(Integer routeId) { this.routeId = routeId; }
    public Integer getRateCardId() { return rateCardId; }
    public void setRateCardId(Integer rateCardId) { this.rateCardId = rateCardId; }
    public BigDecimal getTotalCharge() { return totalCharge; }
    public void setTotalCharge(BigDecimal totalCharge) { this.totalCharge = totalCharge; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public Set getLegs() { return legs; }
    public void setLegs(Set legs) { this.legs = legs; }
    public Set getStatusHistory() { return statusHistory; }
    public void setStatusHistory(Set statusHistory) { this.statusHistory = statusHistory; }
    public Set getDocuments() { return documents; }
    public void setDocuments(Set documents) { this.documents = documents; }
}
