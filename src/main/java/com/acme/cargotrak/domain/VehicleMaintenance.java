package com.acme.cargotrak.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VehicleMaintenance implements Serializable {

    private Integer maintId;
    private Vehicle vehicle;
    private Date maintDate;
    private String description;
    private BigDecimal cost;
    private Integer odometer;
    private String technician;

    public Integer getMaintId() { return maintId; }
    public void setMaintId(Integer maintId) { this.maintId = maintId; }
    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
    public Date getMaintDate() { return maintDate; }
    public void setMaintDate(Date maintDate) { this.maintDate = maintDate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getCost() { return cost; }
    public void setCost(BigDecimal cost) { this.cost = cost; }
    public Integer getOdometer() { return odometer; }
    public void setOdometer(Integer odometer) { this.odometer = odometer; }
    public String getTechnician() { return technician; }
    public void setTechnician(String technician) { this.technician = technician; }
}
