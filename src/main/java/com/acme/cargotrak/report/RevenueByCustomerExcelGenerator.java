package com.acme.cargotrak.report;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.acme.cargotrak.util.Util;

public class RevenueByCustomerExcelGenerator {

    public static void write(OutputStream out, List rows, Date from, Date to) throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sh = wb.createSheet("Revenue");
        HSSFRow titleRow = sh.createRow(0);
        titleRow.createCell(0).setCellValue("Revenue by Customer");
        HSSFRow rangeRow = sh.createRow(1);
        rangeRow.createCell(0).setCellValue("From " + Util.formatDate(from) + " to " + Util.formatDate(to));

        HSSFRow header = sh.createRow(3);
        String[] cols = { "Customer code", "Customer name", "Invoice count", "Gross revenue",
                          "Amount paid", "Outstanding" };
        for (int i = 0; i < cols.length; i++) header.createCell(i).setCellValue(cols[i]);

        int r = 4;
        Iterator it = rows.iterator();
        while (it.hasNext()) {
            Map row = (Map) it.next();
            HSSFRow xr = sh.createRow(r++);
            xr.createCell(0).setCellValue(String.valueOf(row.get("customerCode")));
            xr.createCell(1).setCellValue(String.valueOf(row.get("customerName")));
            Object ic = row.get("invoiceCount");
            xr.createCell(2).setCellValue(ic == null ? 0 : ((Number) ic).doubleValue());
            xr.createCell(3).setCellValue(num((BigDecimal) row.get("grossRevenue")));
            xr.createCell(4).setCellValue(num((BigDecimal) row.get("amountPaid")));
            xr.createCell(5).setCellValue(num((BigDecimal) row.get("outstanding")));
        }
        wb.write(out);
    }

    private static double num(BigDecimal b) {
        return b == null ? 0.0 : b.doubleValue();
    }
}
