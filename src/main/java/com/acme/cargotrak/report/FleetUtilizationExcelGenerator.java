package com.acme.cargotrak.report;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.acme.cargotrak.util.Util;

public class FleetUtilizationExcelGenerator {

    public static void write(OutputStream out, List rows, Date from, Date to) throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sh = wb.createSheet("Fleet utilization");
        sh.createRow(0).createCell(0).setCellValue("Fleet Utilization");
        sh.createRow(1).createCell(0).setCellValue("From " + Util.formatDate(from) + " to " + Util.formatDate(to));

        HSSFRow header = sh.createRow(3);
        String[] cols = { "Plate", "Type", "Status", "Shipment count", "Total weight", "Revenue" };
        for (int i = 0; i < cols.length; i++) header.createCell(i).setCellValue(cols[i]);

        int r = 4;
        Iterator it = rows.iterator();
        while (it.hasNext()) {
            Map row = (Map) it.next();
            HSSFRow xr = sh.createRow(r++);
            xr.createCell(0).setCellValue(String.valueOf(row.get("plateNumber")));
            xr.createCell(1).setCellValue(String.valueOf(row.get("vehicleType")));
            xr.createCell(2).setCellValue(String.valueOf(row.get("status")));
            Object cc = row.get("shipmentCount");
            xr.createCell(3).setCellValue(cc == null ? 0 : ((Number) cc).doubleValue());
            xr.createCell(4).setCellValue(num((BigDecimal) row.get("totalWeight")));
            xr.createCell(5).setCellValue(num((BigDecimal) row.get("totalRevenue")));
        }
        wb.write(out);
    }

    private static double num(BigDecimal b) { return b == null ? 0.0 : b.doubleValue(); }
}
