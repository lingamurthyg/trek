package com.acme.cargotrak.domain;

import java.io.Serializable;
import java.util.Date;

public class ShipmentLeg implements Serializable {

    private Integer legId;
    private Shipment shipment;
    private Integer seqNo;
    private String fromLocation;
    private String toLocation;
    private Integer driverId;
    private Integer vehicleId;
    private Date departureTime;
    private Date arrivalTime;
    private String status;

    public Integer getLegId() { return legId; }
    public void setLegId(Integer legId) { this.legId = legId; }
    public Shipment getShipment() { return shipment; }
    public void setShipment(Shipment shipment) { this.shipment = shipment; }
    public Integer getSeqNo() { return seqNo; }
    public void setSeqNo(Integer seqNo) { this.seqNo = seqNo; }
    public String getFromLocation() { return fromLocation; }
    public void setFromLocation(String fromLocation) { this.fromLocation = fromLocation; }
    public String getToLocation() { return toLocation; }
    public void setToLocation(String toLocation) { this.toLocation = toLocation; }
    public Integer getDriverId() { return driverId; }
    public void setDriverId(Integer driverId) { this.driverId = driverId; }
    public Integer getVehicleId() { return vehicleId; }
    public void setVehicleId(Integer vehicleId) { this.vehicleId = vehicleId; }
    public Date getDepartureTime() { return departureTime; }
    public void setDepartureTime(Date departureTime) { this.departureTime = departureTime; }
    public Date getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(Date arrivalTime) { this.arrivalTime = arrivalTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
