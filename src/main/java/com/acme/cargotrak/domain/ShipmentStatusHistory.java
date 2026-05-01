package com.acme.cargotrak.domain;

import java.io.Serializable;
import java.util.Date;

public class ShipmentStatusHistory implements Serializable {

    private Long historyId;
    private Integer shipmentId;
    private String oldStatus;
    private String newStatus;
    private String changedBy;
    private Date changedAt;
    private String notes;

    public Long getHistoryId() { return historyId; }
    public void setHistoryId(Long historyId) { this.historyId = historyId; }
    public Integer getShipmentId() { return shipmentId; }
    public void setShipmentId(Integer shipmentId) { this.shipmentId = shipmentId; }
    public String getOldStatus() { return oldStatus; }
    public void setOldStatus(String oldStatus) { this.oldStatus = oldStatus; }
    public String getNewStatus() { return newStatus; }
    public void setNewStatus(String newStatus) { this.newStatus = newStatus; }
    public String getChangedBy() { return changedBy; }
    public void setChangedBy(String changedBy) { this.changedBy = changedBy; }
    public Date getChangedAt() { return changedAt; }
    public void setChangedAt(Date changedAt) { this.changedAt = changedAt; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
