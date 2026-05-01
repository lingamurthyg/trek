package com.acme.cargotrak.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer warehouseId;
    private String code;
    private String name;
    private String city;
    private BigDecimal capacityM3;

    private Set zones = new HashSet();

    public Integer getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Integer warehouseId) { this.warehouseId = warehouseId; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public BigDecimal getCapacityM3() { return capacityM3; }
    public void setCapacityM3(BigDecimal capacityM3) { this.capacityM3 = capacityM3; }
    public Set getZones() { return zones; }
    public void setZones(Set zones) { this.zones = zones; }
}
