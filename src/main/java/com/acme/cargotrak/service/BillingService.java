package com.acme.cargotrak.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.acme.cargotrak.dao.AuditLogDao;
import com.acme.cargotrak.dao.InvoiceDao;
import com.acme.cargotrak.dao.PaymentAllocationDao;
import com.acme.cargotrak.dao.PaymentDao;
import com.acme.cargotrak.dao.ShipmentDao;
import com.acme.cargotrak.domain.AuditLog;
import com.acme.cargotrak.domain.Invoice;
import com.acme.cargotrak.domain.InvoiceLineItem;
import com.acme.cargotrak.domain.Payment;
import com.acme.cargotrak.domain.PaymentAllocation;
import com.acme.cargotrak.domain.Shipment;
import com.acme.cargotrak.util.Constants;
import com.acme.cargotrak.util.DateUtils;
import com.acme.cargotrak.util.Util;

public class BillingService {

    private static final Logger logger = Logger.getLogger(BillingService.class);

    private InvoiceDao invoiceDao;
    private PaymentDao paymentDao;
    private PaymentAllocationDao paymentAllocationDao;
    private ShipmentDao shipmentDao;
    private ShipmentService shipmentService;
    private AuditLogDao auditLogDao;
    private MailService mailService;

    public void setInvoiceDao(InvoiceDao d) { this.invoiceDao = d; }
    public void setPaymentDao(PaymentDao d) { this.paymentDao = d; }
    public void setPaymentAllocationDao(PaymentAllocationDao d) { this.paymentAllocationDao = d; }
    public void setShipmentDao(ShipmentDao d) { this.shipmentDao = d; }
    public void setShipmentService(ShipmentService s) { this.shipmentService = s; }
    public void setAuditLogDao(AuditLogDao a) { this.auditLogDao = a; }
    public void setMailService(MailService m) { this.mailService = m; }

    public Invoice findById(Integer id) { return (Invoice) invoiceDao.findById(Invoice.class, id); }
    public Invoice findByInvoiceNo(String no) { return invoiceDao.findByInvoiceNo(no); }

    public List search(String fragment, Integer customerId, String status, Date from, Date to,
                       int page, int pageSize) {
        return invoiceDao.search(fragment, customerId, status, from, to, page * pageSize, pageSize);
    }

    public int countSearch(String fragment, Integer customerId, String status, Date from, Date to) {
        return invoiceDao.countSearch(fragment, customerId, status, from, to);
    }

    public Integer generateInvoiceForShipment(Integer shipmentId, String createdBy) {
        Shipment s = (Shipment) shipmentDao.findById(Shipment.class, shipmentId);
        if (s == null) return null;
        BigDecimal subtotal = s.getTotalCharge();
        if (subtotal == null) subtotal = shipmentService.calculateCharge(s);
        BigDecimal taxRate = new BigDecimal("0.0825");
        BigDecimal tax = subtotal.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(tax).setScale(2, RoundingMode.HALF_UP);

        Invoice inv = new Invoice();
        inv.setInvoiceNo(Util.generateInvoiceNo());
        inv.setCustomerId(s.getCustomerId());
        inv.setInvoiceDate(new Date());
        inv.setDueDate(DateUtils.addDays(new Date(), 30));
        inv.setStatus("OPEN");
        inv.setSubtotal(subtotal);
        inv.setTax(tax);
        inv.setTotal(total);
        inv.setAmountPaid(BigDecimal.ZERO);
        Integer invoiceId = (Integer) invoiceDao.save(inv);

        InvoiceLineItem li = new InvoiceLineItem();
        li.setInvoice(inv);
        li.setShipmentId(s.getShipmentId());
        li.setDescription("Freight charge for " + s.getTrackingNo());
        li.setQuantity(BigDecimal.ONE);
        li.setUnitPrice(subtotal);
        li.setLineTotal(subtotal);
        inv.getLineItems().add(li);
        invoiceDao.update(inv);

        // transition shipment to INVOICED
        shipmentService.transition(shipmentId, Constants.STATUS_INVOICED, createdBy);

        // notify
        try {
            Map<String,String> p = new HashMap<String,String>();
            p.put("invoiceNo", inv.getInvoiceNo());
            p.put("customerName", "Customer #" + s.getCustomerId());
            p.put("total", Util.formatMoney(total));
            p.put("dueDate", Util.formatDate(inv.getDueDate()));
            mailService.sendByTemplate("INVOICE_GENERATED", null, p);
        } catch (Exception e) {
            logger.error("invoice email failed", e);
        }

        audit(createdBy, "GENERATE", "Invoice", String.valueOf(invoiceId),
              "for shipment " + shipmentId + " total=" + Util.formatMoney(total));
        return invoiceId;
    }

    public int generateInvoicesForAllDelivered(String createdBy) {
        List ids = shipmentService.findUninvoicedDelivered();
        int count = 0;
        Iterator it = ids.iterator();
        while (it.hasNext()) {
            Shipment s = (Shipment) it.next();
            try {
                Integer iid = generateInvoiceForShipment(s.getShipmentId(), createdBy);
                if (iid != null) count++;
            } catch (Exception e) {
                logger.error("error");
            }
        }
        return count;
    }

    public Integer recordPayment(Payment p) {
        if (p.getPaymentDate() == null) p.setPaymentDate(new Date());
        return (Integer) paymentDao.save(p);
    }

    public void allocatePayment(Integer paymentId, Integer invoiceId, BigDecimal amount) {
        PaymentAllocation a = new PaymentAllocation();
        a.setPaymentId(paymentId);
        a.setInvoiceId(invoiceId);
        a.setAmount(amount);
        paymentAllocationDao.save(a);
        invoiceDao.applyPayment(invoiceId, amount);
    }

    public List listPaymentsForCustomer(Integer customerId) {
        return paymentDao.findByCustomer(customerId);
    }

    public List listOverdueInvoices() { return invoiceDao.findOverdueAsOf(new Date()); }
    public List listOpenForCustomer(Integer customerId) { return invoiceDao.findOpenForCustomer(customerId); }

    private void audit(String username, String action, String type, String id, String details) {
        AuditLog a = new AuditLog();
        a.setUsername(username);
        a.setAction(action);
        a.setEntityType(type);
        a.setEntityId(id);
        a.setDetails(details);
        a.setAuditTime(new Date());
        try { auditLogDao.save(a); } catch (Exception e) { e.printStackTrace(); }
    }
}
