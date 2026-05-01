package com.acme.cargotrak.ingestion;

import java.util.ArrayList;
import java.util.List;

public class IngestResult {

    private String sourceFile;
    private int accepted;
    private int rejected;
    private String rejectionReportPath;
    private List<String> rejections = new ArrayList<String>();
    private List<ParsedShipmentRow> rows = new ArrayList<ParsedShipmentRow>();
    private List<ParsedStatusRow> statusUpdates = new ArrayList<ParsedStatusRow>();

    public String getSourceFile() { return sourceFile; }
    public void setSourceFile(String s) { this.sourceFile = s; }
    public int getAccepted() { return accepted; }
    public void incAccepted() { this.accepted++; }
    public int getRejected() { return rejected; }
    public void incRejected() { this.rejected++; }
    public String getRejectionReportPath() { return rejectionReportPath; }
    public void setRejectionReportPath(String p) { this.rejectionReportPath = p; }
    public List<String> getRejections() { return rejections; }
    public List<ParsedShipmentRow> getRows() { return rows; }
    public List<ParsedStatusRow> getStatusUpdates() { return statusUpdates; }
}
