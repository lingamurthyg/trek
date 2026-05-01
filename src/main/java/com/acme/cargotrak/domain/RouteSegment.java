package com.acme.cargotrak.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class RouteSegment implements Serializable {

    private Integer segmentId;
    private Route route;
    private Integer seqNo;
    private String fromLocation;
    private String toLocation;
    private BigDecimal distanceKm;
    private BigDecimal expectedHours;

    public Integer getSegmentId() { return segmentId; }
    public void setSegmentId(Integer segmentId) { this.segmentId = segmentId; }
    public Route getRoute() { return route; }
    public void setRoute(Route route) { this.route = route; }
    public Integer getSeqNo() { return seqNo; }
    public void setSeqNo(Integer seqNo) { this.seqNo = seqNo; }
    public String getFromLocation() { return fromLocation; }
    public void setFromLocation(String fromLocation) { this.fromLocation = fromLocation; }
    public String getToLocation() { return toLocation; }
    public void setToLocation(String toLocation) { this.toLocation = toLocation; }
    public BigDecimal getDistanceKm() { return distanceKm; }
    public void setDistanceKm(BigDecimal distanceKm) { this.distanceKm = distanceKm; }
    public BigDecimal getExpectedHours() { return expectedHours; }
    public void setExpectedHours(BigDecimal expectedHours) { this.expectedHours = expectedHours; }
}
