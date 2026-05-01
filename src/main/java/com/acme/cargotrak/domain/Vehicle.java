package com.acme.cargotrak.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer vehicleId;
    private String plateNumber;
    private String make;
    private String model;
    private Integer year;
    private BigDecimal capacityKg;
    private String vehicleType;
    private String status;

    private Set maintenanceRecords = new HashSet();

    public Integer getVehicleId() { return vehicleId; }
    public void setVehicleId(Integer vehicleId) { this.vehicleId = vehicleId; }
    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public BigDecimal getCapacityKg() { return capacityKg; }
    public void setCapacityKg(BigDecimal capacityKg) { this.capacityKg = capacityKg; }
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Set getMaintenanceRecords() { return maintenanceRecords; }
    public void setMaintenanceRecords(Set maintenanceRecords) { this.maintenanceRecords = maintenanceRecords; }
}
