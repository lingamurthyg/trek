package com.acme.cargotrak.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Route implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer routeId;
    private String code;
    private String name;
    private String origin;
    private String destination;

    private Set segments = new HashSet();

    public Integer getRouteId() { return routeId; }
    public void setRouteId(Integer routeId) { this.routeId = routeId; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public Set getSegments() { return segments; }
    public void setSegments(Set segments) { this.segments = segments; }
}
