package com.acme.cargotrak.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.acme.cargotrak.ingestion.BulkShipmentImporter;
import com.acme.cargotrak.ingestion.CsvManifestParser;
import com.acme.cargotrak.ingestion.Edi204Parser;
import com.acme.cargotrak.ingestion.Edi214Parser;
import com.acme.cargotrak.ingestion.IngestResult;
import com.acme.cargotrak.util.Constants;
import com.acme.cargotrak.util.FileUtil;

public class IngestionService {

    private static final Logger logger = Logger.getLogger(IngestionService.class);

    private BulkShipmentImporter importer;
    private MailService mailService;

    public void setImporter(BulkShipmentImporter i) { this.importer = i; }
    public void setMailService(MailService m) { this.mailService = m; }

    public IngestResult ingestCsvManifest(File uploaded, String uploaderEmail) {
        FileUtil.ensureDir(Constants.UPLOADS_DIR);
        FileUtil.ensureDir(Constants.REPORTS_DIR);
        IngestResult res = CsvManifestParser.parse(uploaded);
        importer.importShipments(res);
        notifyUploader(res, uploaderEmail);
        return res;
    }

    public IngestResult ingestEdi204(File uploaded, String uploaderEmail) {
        IngestResult res = Edi204Parser.parse(uploaded);
        importer.importShipments(res);
        notifyUploader(res, uploaderEmail);
        return res;
    }

    public IngestResult ingestEdi214(File uploaded, String uploaderEmail) {
        IngestResult res = Edi214Parser.parse(uploaded);
        importer.applyStatusUpdates(res);
        notifyUploader(res, uploaderEmail);
        return res;
    }

    private void notifyUploader(IngestResult res, String uploaderEmail) {
        if (uploaderEmail == null || uploaderEmail.length() == 0) return;
        try {
            String subject = "[CargoTrak] Ingestion summary: " + res.getSourceFile();
            StringBuffer body = new StringBuffer();
            body.append("File: ").append(res.getSourceFile()).append('\n');
            body.append("Accepted: ").append(res.getAccepted()).append('\n');
            body.append("Rejected: ").append(res.getRejected()).append('\n');
            body.append("Rejection report: ").append(res.getRejectionReportPath()).append('\n');
            mailService.sendAsync(uploaderEmail, subject, body.toString());
        } catch (Exception e) {
            logger.error("ingestion notify failed", e);
        }
    }
}
