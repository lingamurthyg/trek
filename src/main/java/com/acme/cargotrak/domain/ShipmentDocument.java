package com.acme.cargotrak.domain;

import java.io.Serializable;
import java.util.Date;

public class ShipmentDocument implements Serializable {

    private Integer documentId;
    private Integer shipmentId;
    private String documentType;
    private String fileName;
    private String filePath;
    private String uploadedBy;
    private Date uploadedAt;

    public Integer getDocumentId() { return documentId; }
    public void setDocumentId(Integer documentId) { this.documentId = documentId; }
    public Integer getShipmentId() { return shipmentId; }
    public void setShipmentId(Integer shipmentId) { this.shipmentId = shipmentId; }
    public String getDocumentType() { return documentType; }
    public void setDocumentType(String documentType) { this.documentType = documentType; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public String getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(String uploadedBy) { this.uploadedBy = uploadedBy; }
    public Date getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(Date uploadedAt) { this.uploadedAt = uploadedAt; }
}
