package com.acme.cargotrak.form;

import java.math.BigDecimal;

import org.apache.struts.action.ActionForm;

public class MaintenanceForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private Integer maintId;
    private Integer vehicleId;
    private String maintDate;
    private String description;
    private BigDecimal cost;
    private Integer odometer;
    private String technician;

    public Integer getMaintId() { return maintId; }
    public void setMaintId(Integer m) { this.maintId = m; }
    public Integer getVehicleId() { return vehicleId; }
    public void setVehicleId(Integer v) { this.vehicleId = v; }
    public String getMaintDate() { return maintDate; }
    public void setMaintDate(String s) { this.maintDate = s; }
    public String getDescription() { return description; }
    public void setDescription(String s) { this.description = s; }
    public BigDecimal getCost() { return cost; }
    public void setCost(BigDecimal c) { this.cost = c; }
    public Integer getOdometer() { return odometer; }
    public void setOdometer(Integer o) { this.odometer = o; }
    public String getTechnician() { return technician; }
    public void setTechnician(String s) { this.technician = s; }
}
