package com.acme.cargotrak.ingestion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.acme.cargotrak.util.Constants;
import com.acme.cargotrak.util.FileUtil;

/**
 * Fixed-width EDI 204 (Motor Carrier Load Tender) "lite" parser.
 * Real EDI is way more complex but ops just gives us positional flat files.
 *
 * Layout (1-indexed):
 *   01-10  customer_code  (10)
 *   11-30  origin          (20)
 *   31-50  destination     (20)
 *   51-60  weight_kg       (10, integer)
 *   61-70  declared_value  (10, integer)
 *   71-80  route_code      (10)
 *   81-end notes           (variable)
 *
 * @author L. Chen 2013-02
 */
public class Edi204Parser {

    private static final Logger logger = Logger.getLogger(Edi204Parser.class);

    public static IngestResult parse(File f) {
        IngestResult res = new IngestResult();
        res.setSourceFile(f.getName());
        BufferedReader br = null;
        PrintWriter pw = null;
        try {
            FileUtil.ensureDir(Constants.REPORTS_DIR);
            File rej = new File(Constants.REPORTS_DIR + "/" + f.getName() + ".rejections.txt");
            pw = new PrintWriter(new FileOutputStream(rej));
            pw.println("EDI 204 Rejections for " + f.getName());
            pw.println("---");
            res.setRejectionReportPath(rej.getAbsolutePath());

            br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            String line;
            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (line.length() < 80) {
                    res.incRejected();
                    String reason = "line " + lineNo + ": too short (" + line.length() + " chars, expected >= 80)";
                    res.getRejections().add(reason);
                    pw.println(reason);
                    continue;
                }
                ParsedShipmentRow row = new ParsedShipmentRow();
                row.setCustomerCode(line.substring(0, 10).trim());
                row.setOrigin(line.substring(10, 30).trim());
                row.setDestination(line.substring(30, 50).trim());
                try {
                    row.setWeightKg(new BigDecimal(line.substring(50, 60).trim()));
                } catch (NumberFormatException e) {
                    res.incRejected();
                    String reason = "line " + lineNo + ": bad weight";
                    res.getRejections().add(reason);
                    pw.println(reason);
                    continue;
                }
                try {
                    String dv = line.substring(60, 70).trim();
                    if (dv.length() > 0) row.setDeclaredValue(new BigDecimal(dv));
                } catch (NumberFormatException e) { /* tolerate */ }
                row.setRouteCode(line.substring(70, 80).trim());
                if (line.length() > 80) {
                    row.setNotes(line.substring(80).trim());
                }
                if (row.getCustomerCode() == null || row.getCustomerCode().length() == 0) {
                    res.incRejected();
                    String reason = "line " + lineNo + ": missing customer_code";
                    res.getRejections().add(reason);
                    pw.println(reason);
                    continue;
                }
                res.getRows().add(row);
                res.incAccepted();
            }
        } catch (Exception e) {
            logger.error("EDI 204 parse failed", e);
        } finally {
            try { if (br != null) br.close(); } catch (Exception ex) {}
            try { if (pw != null) pw.close(); } catch (Exception ex) {}
        }
        return res;
    }
}
