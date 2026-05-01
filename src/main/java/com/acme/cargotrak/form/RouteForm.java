package com.acme.cargotrak.form;

import org.apache.struts.action.ActionForm;

public class RouteForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private Integer routeId;
    private String code;
    private String name;
    private String origin;
    private String destination;

    public Integer getRouteId() { return routeId; }
    public void setRouteId(Integer r) { this.routeId = r; }
    public String getCode() { return code; }
    public void setCode(String s) { this.code = s; }
    public String getName() { return name; }
    public void setName(String s) { this.name = s; }
    public String getOrigin() { return origin; }
    public void setOrigin(String s) { this.origin = s; }
    public String getDestination() { return destination; }
    public void setDestination(String s) { this.destination = s; }
}
