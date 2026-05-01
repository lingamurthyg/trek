package com.acme.cargotrak.ingestion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.acme.cargotrak.util.Constants;
import com.acme.cargotrak.util.FileUtil;

/**
 * Fixed-width EDI 214 (Transportation Carrier Shipment Status Message) "lite".
 *
 * Layout:
 *   01-20  tracking_no      (20)
 *   21-30  status_code      (10)   one of DRAFT/BOOKED/IN_TRANSIT/DELIVERED/INVOICED/CLOSED
 *   31-44  timestamp        (14)   yyyyMMddHHmmss
 *
 * @author L. Chen 2013-02
 */
public class Edi214Parser {

    private static final Logger logger = Logger.getLogger(Edi214Parser.class);
    private static final SimpleDateFormat TS = new SimpleDateFormat("yyyyMMddHHmmss");

    public static IngestResult parse(File f) {
        IngestResult res = new IngestResult();
        res.setSourceFile(f.getName());
        BufferedReader br = null;
        PrintWriter pw = null;
        try {
            FileUtil.ensureDir(Constants.REPORTS_DIR);
            File rej = new File(Constants.REPORTS_DIR + "/" + f.getName() + ".rejections.txt");
            pw = new PrintWriter(new FileOutputStream(rej));
            pw.println("EDI 214 Rejections for " + f.getName());
            pw.println("---");
            res.setRejectionReportPath(rej.getAbsolutePath());

            br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            String line;
            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (line.length() < 44) {
                    res.incRejected();
                    String reason = "line " + lineNo + ": too short";
                    res.getRejections().add(reason);
                    pw.println(reason);
                    continue;
                }
                ParsedStatusRow row = new ParsedStatusRow();
                row.setTrackingNo(line.substring(0, 20).trim());
                row.setNewStatus(line.substring(20, 30).trim());
                String ts = line.substring(30, 44).trim();
                try {
                    row.setTimestamp(TS.parse(ts));
                } catch (Exception e) {
                    /* tolerate */
                }
                if (row.getTrackingNo() == null || row.getTrackingNo().length() == 0) {
                    res.incRejected();
                    String reason = "line " + lineNo + ": missing tracking_no";
                    res.getRejections().add(reason);
                    pw.println(reason);
                    continue;
                }
                res.getStatusUpdates().add(row);
                res.incAccepted();
            }
        } catch (Exception e) {
            logger.error("EDI 214 parse failed", e);
        } finally {
            try { if (br != null) br.close(); } catch (Exception ex) {}
            try { if (pw != null) pw.close(); } catch (Exception ex) {}
        }
        return res;
    }
}
