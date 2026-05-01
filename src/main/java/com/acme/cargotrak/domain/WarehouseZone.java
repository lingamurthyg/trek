package com.acme.cargotrak.domain;

import java.io.Serializable;

public class WarehouseZone implements Serializable {

    private Integer zoneId;
    private Warehouse warehouse;
    private String code;
    private String description;

    public Integer getZoneId() { return zoneId; }
    public void setZoneId(Integer zoneId) { this.zoneId = zoneId; }
    public Warehouse getWarehouse() { return warehouse; }
    public void setWarehouse(Warehouse warehouse) { this.warehouse = warehouse; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
