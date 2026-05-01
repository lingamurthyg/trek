package com.acme.cargotrak.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.acme.cargotrak.dao.InvoiceDao;
import com.acme.cargotrak.dao.ReportDao;
import com.acme.cargotrak.domain.Invoice;
import com.acme.cargotrak.report.ArAgingPdfGenerator;
import com.acme.cargotrak.report.FleetUtilizationExcelGenerator;
import com.acme.cargotrak.report.InvoicePdfGenerator;
import com.acme.cargotrak.report.RevenueByCustomerExcelGenerator;
import com.acme.cargotrak.util.Constants;
import com.acme.cargotrak.util.DateUtils;
import com.acme.cargotrak.util.FileUtil;
import com.acme.cargotrak.util.Util;

public class ReportService {

    private static final Logger logger = Logger.getLogger(ReportService.class);

    private ReportDao reportDao;
    private InvoiceDao invoiceDao;

    public void setReportDao(ReportDao r) { this.reportDao = r; }
    public void setInvoiceDao(InvoiceDao i) { this.invoiceDao = i; }

    public List revenueByCustomer(Date from, Date to) { return reportDao.revenueByCustomer(from, to); }
    public List fleetUtilization(Date from, Date to) { return reportDao.fleetUtilization(from, to); }
    public List onTimeDelivery(Date from, Date to) { return reportDao.onTimeDeliveryStats(from, to); }
    public List arAging(Date asOf) { return reportDao.arAging(asOf); }
    public List shipmentsByRoute(Date from, Date to) { return reportDao.shipmentsByRoute(from, to); }
    public List driverPerformance(Date from, Date to) { return reportDao.driverPerformance(from, to); }

    public File generateRevenueExcel(Date from, Date to) {
        FileUtil.ensureDir(Constants.REPORTS_DIR);
        File out = new File(Constants.REPORTS_DIR + "/revenue-" + System.currentTimeMillis() + ".xls");
        try {
            FileOutputStream fos = new FileOutputStream(out);
            try {
                RevenueByCustomerExcelGenerator.write(fos, revenueByCustomer(from, to), from, to);
                fos.flush();
            } finally {
                fos.close();
            }
        } catch (Exception e) {
            logger.error("revenue excel failed", e);
        }
        return out;
    }

    public File generateFleetExcel(Date from, Date to) {
        FileUtil.ensureDir(Constants.REPORTS_DIR);
        File out = new File(Constants.REPORTS_DIR + "/fleet-" + System.currentTimeMillis() + ".xls");
        try {
            FileOutputStream fos = new FileOutputStream(out);
            try {
                FleetUtilizationExcelGenerator.write(fos, fleetUtilization(from, to), from, to);
                fos.flush();
            } finally {
                fos.close();
            }
        } catch (Exception e) {
            logger.error("fleet excel failed", e);
        }
        return out;
    }

    public File generateArAgingPdf(Date asOf) {
        FileUtil.ensureDir(Constants.REPORTS_DIR);
        File out = new File(Constants.REPORTS_DIR + "/ar-aging-" + System.currentTimeMillis() + ".pdf");
        try {
            FileOutputStream fos = new FileOutputStream(out);
            try {
                ArAgingPdfGenerator.write(fos, arAging(asOf), asOf);
                fos.flush();
            } finally {
                fos.close();
            }
        } catch (Exception e) {
            logger.error("ar aging pdf failed", e);
        }
        return out;
    }

    public File generateInvoicePdf(Integer invoiceId) {
        Invoice inv = (Invoice) invoiceDao.findById(Invoice.class, invoiceId);
        if (inv == null) return null;
        FileUtil.ensureDir(Constants.REPORTS_DIR);
        File out = new File(Constants.REPORTS_DIR + "/invoice-" + inv.getInvoiceNo() + ".pdf");
        try {
            FileOutputStream fos = new FileOutputStream(out);
            try {
                InvoicePdfGenerator.write(fos, inv);
                fos.flush();
            } finally {
                fos.close();
            }
        } catch (Exception e) {
            logger.error("invoice pdf failed", e);
        }
        return out;
    }

    public void generateInvoicePdfAsync(final Integer invoiceId) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                generateInvoicePdf(invoiceId);
            }
        }, "report-" + invoiceId + "-" + System.currentTimeMillis());
        t.start();
    }
}
