package com.acme.cargotrak.form;

import java.math.BigDecimal;

import org.apache.struts.action.ActionForm;

public class WarehouseForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private Integer warehouseId;
    private String code;
    private String name;
    private String city;
    private BigDecimal capacityM3;

    public Integer getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Integer w) { this.warehouseId = w; }
    public String getCode() { return code; }
    public void setCode(String s) { this.code = s; }
    public String getName() { return name; }
    public void setName(String s) { this.name = s; }
    public String getCity() { return city; }
    public void setCity(String s) { this.city = s; }
    public BigDecimal getCapacityM3() { return capacityM3; }
    public void setCapacityM3(BigDecimal c) { this.capacityM3 = c; }
}
