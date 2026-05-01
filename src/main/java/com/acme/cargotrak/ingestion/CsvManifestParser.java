package com.acme.cargotrak.ingestion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.acme.cargotrak.util.Constants;
import com.acme.cargotrak.util.FileUtil;

/**
 * Parses customer shipment manifest CSVs.
 *
 * Expected columns (header row required):
 *   customer_code, origin, destination, weight_kg, volume_m3, declared_value, pickup_date, route_code, notes
 *
 * Rejection criteria: missing customer_code, weight not numeric, etc.
 *
 * @author S. Patel 2014-04
 */
public class CsvManifestParser {

    private static final Logger logger = Logger.getLogger(CsvManifestParser.class);
    // shared SimpleDateFormat. yes.
    private static final SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");

    public static IngestResult parse(File f) {
        IngestResult res = new IngestResult();
        res.setSourceFile(f.getName());
        BufferedReader br = null;
        PrintWriter pw = null;
        try {
            FileUtil.ensureDir(Constants.REPORTS_DIR);
            File rej = new File(Constants.REPORTS_DIR + "/" + f.getName() + ".rejections.txt");
            pw = new PrintWriter(new FileOutputStream(rej));
            pw.println("Rejections for " + f.getName());
            pw.println("---");
            res.setRejectionReportPath(rej.getAbsolutePath());

            br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            String line;
            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (lineNo == 1) continue; // header
                if (line.trim().length() == 0) continue;
                String[] parts = line.split(",");
                if (parts.length < 9) {
                    res.incRejected();
                    String reason = "line " + lineNo + ": too few columns (" + parts.length + ")";
                    res.getRejections().add(reason);
                    pw.println(reason);
                    continue;
                }
                ParsedShipmentRow row = new ParsedShipmentRow();
                String customerCode = parts[0].trim();
                if (customerCode.length() == 0) {
                    res.incRejected();
                    String reason = "line " + lineNo + ": missing customer_code";
                    res.getRejections().add(reason);
                    pw.println(reason);
                    continue;
                }
                row.setCustomerCode(customerCode);
                row.setOrigin(parts[1].trim());
                row.setDestination(parts[2].trim());
                try {
                    row.setWeightKg(new BigDecimal(parts[3].trim()));
                } catch (NumberFormatException e) {
                    res.incRejected();
                    String reason = "line " + lineNo + ": bad weight '" + parts[3] + "'";
                    res.getRejections().add(reason);
                    pw.println(reason);
                    continue;
                }
                try {
                    if (parts[4].trim().length() > 0) row.setVolumeM3(new BigDecimal(parts[4].trim()));
                    if (parts[5].trim().length() > 0) row.setDeclaredValue(new BigDecimal(parts[5].trim()));
                } catch (NumberFormatException e) {
                    // tolerate
                }
                if (parts[6].trim().length() > 0) {
                    try { row.setPickupDate(YMD.parse(parts[6].trim())); } catch (Exception e) { /* tolerate */ }
                }
                row.setRouteCode(parts[7].trim());
                row.setNotes(parts[8].trim());
                res.getRows().add(row);
                res.incAccepted();
            }
        } catch (Exception e) {
            logger.error("CSV parse failed", e);
        } finally {
            try { if (br != null) br.close(); } catch (Exception ex) {}
            try { if (pw != null) pw.close(); } catch (Exception ex) {}
        }
        return res;
    }
}
