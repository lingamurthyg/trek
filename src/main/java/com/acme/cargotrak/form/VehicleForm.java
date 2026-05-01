package com.acme.cargotrak.form;

import java.math.BigDecimal;

import org.apache.struts.action.ActionForm;

public class VehicleForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private Integer vehicleId;
    private String plateNumber;
    private String make;
    private String model;
    private Integer year;
    private BigDecimal capacityKg;
    private String vehicleType;
    private String status;

    public Integer getVehicleId() { return vehicleId; }
    public void setVehicleId(Integer vehicleId) { this.vehicleId = vehicleId; }
    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String s) { this.plateNumber = s; }
    public String getMake() { return make; }
    public void setMake(String s) { this.make = s; }
    public String getModel() { return model; }
    public void setModel(String s) { this.model = s; }
    public Integer getYear() { return year; }
    public void setYear(Integer y) { this.year = y; }
    public BigDecimal getCapacityKg() { return capacityKg; }
    public void setCapacityKg(BigDecimal c) { this.capacityKg = c; }
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String s) { this.vehicleType = s; }
    public String getStatus() { return status; }
    public void setStatus(String s) { this.status = s; }
}
