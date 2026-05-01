package com.acme.cargotrak.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.acme.cargotrak.dao.AuditLogDao;
import com.acme.cargotrak.dao.RateCardDao;
import com.acme.cargotrak.dao.ShipmentDao;
import com.acme.cargotrak.dao.ShipmentLegDao;
import com.acme.cargotrak.dao.ShipmentStatusHistoryDao;
import com.acme.cargotrak.domain.AuditLog;
import com.acme.cargotrak.domain.RateCard;
import com.acme.cargotrak.domain.RateCardEntry;
import com.acme.cargotrak.domain.Shipment;
import com.acme.cargotrak.domain.ShipmentLeg;
import com.acme.cargotrak.domain.ShipmentStatusHistory;
import com.acme.cargotrak.util.Constants;
import com.acme.cargotrak.util.Util;

public class ShipmentService {

    private static final Logger logger = Logger.getLogger(ShipmentService.class);

    private ShipmentDao shipmentDao;
    private ShipmentLegDao shipmentLegDao;
    private ShipmentStatusHistoryDao shipmentStatusHistoryDao;
    private RateCardDao rateCardDao;
    private AuditLogDao auditLogDao;
    private ShipmentStateMachine stateMachine;
    private MailService mailService;

    public void setShipmentDao(ShipmentDao s) { this.shipmentDao = s; }
    public void setShipmentLegDao(ShipmentLegDao s) { this.shipmentLegDao = s; }
    public void setShipmentStatusHistoryDao(ShipmentStatusHistoryDao s) { this.shipmentStatusHistoryDao = s; }
    public void setRateCardDao(RateCardDao s) { this.rateCardDao = s; }
    public void setAuditLogDao(AuditLogDao a) { this.auditLogDao = a; }
    public void setStateMachine(ShipmentStateMachine sm) { this.stateMachine = sm; }
    public void setMailService(MailService m) { this.mailService = m; }

    public Shipment findById(Integer id) {
        return (Shipment) shipmentDao.findById(Shipment.class, id);
    }

    public Shipment findByTrackingNo(String trackingNo) {
        return shipmentDao.findByTrackingNo(trackingNo);
    }

    public List search(String trackingFragment, Integer customerId, String status,
                       Date fromDate, Date toDate, int page, int pageSize) {
        return shipmentDao.search(trackingFragment, customerId, status, fromDate, toDate,
                page * pageSize, pageSize);
    }

    public int countSearch(String trackingFragment, Integer customerId, String status,
                           Date fromDate, Date toDate) {
        return shipmentDao.countSearch(trackingFragment, customerId, status, fromDate, toDate);
    }

    public Integer createShipment(Shipment s, String createdBy) {
        if (Util.isBlank(s.getTrackingNo())) s.setTrackingNo(Util.generateTrackingNo());
        if (Util.isBlank(s.getStatus())) s.setStatus(Constants.STATUS_DRAFT);
        if (s.getBookedDate() == null) s.setBookedDate(new Date());
        Integer id = (Integer) shipmentDao.save(s);
        recordHistory(id, null, s.getStatus(), createdBy, "created");
        audit(createdBy, "CREATE", "Shipment", String.valueOf(id), "tracking=" + s.getTrackingNo());
        return id;
    }

    public boolean transition(Integer shipmentId, String newStatus, String changedBy) {
        Shipment s = findById(shipmentId);
        if (s == null) return false;
        String old = s.getStatus();
        if (!stateMachine.canTransition(old, newStatus)) {
            logger.warn("rejected transition " + old + " -> " + newStatus + " on shipment " + shipmentId);
            return false;
        }
        s.setStatus(newStatus);
        if (Constants.STATUS_DELIVERED.equals(newStatus)) {
            s.setDeliveryDate(new Date());
        }
        shipmentDao.update(s);
        recordHistory(shipmentId, old, newStatus, changedBy, "transition");
        audit(changedBy, "STATUS", "Shipment", String.valueOf(shipmentId), old + " -> " + newStatus);

        if (Constants.STATUS_DELIVERED.equals(newStatus)) {
            // fire-and-forget delivery email
            try {
                Map<String,String> p = new HashMap<String,String>();
                p.put("trackingNo", s.getTrackingNo());
                p.put("customerName", String.valueOf(s.getCustomerId()));
                p.put("deliveryDate", Util.formatDateTime(s.getDeliveryDate()));
                mailService.sendByTemplate("SHIPMENT_DELIVERED", null, p);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public void assignDriverVehicleRoute(Integer shipmentId, Integer driverId, Integer vehicleId,
                                         Integer routeId, String changedBy) {
        Shipment s = findById(shipmentId);
        if (s == null) return;
        s.setDriverId(driverId);
        s.setVehicleId(vehicleId);
        s.setRouteId(routeId);
        shipmentDao.update(s);
        audit(changedBy, "ASSIGN", "Shipment", String.valueOf(shipmentId),
              "driver=" + driverId + " vehicle=" + vehicleId + " route=" + routeId);
    }

    public Integer addLeg(Integer shipmentId, ShipmentLeg leg) {
        Shipment s = findById(shipmentId);
        if (s == null) return null;
        leg.setShipment(s);
        return (Integer) shipmentLegDao.save(leg);
    }

    public List listLegs(Integer shipmentId) {
        return shipmentLegDao.findByShipment(shipmentId);
    }

    public List listStatusHistory(Integer shipmentId) {
        return shipmentStatusHistoryDao.findByShipment(shipmentId);
    }

    public List findUninvoicedDelivered() {
        return shipmentDao.findUninvoicedDelivered();
    }

    public BigDecimal calculateCharge(Shipment s) {
        if (s == null || s.getRouteId() == null || s.getRateCardId() == null) {
            return BigDecimal.ZERO;
        }
        RateCard rc = (RateCard) rateCardDao.findById(RateCard.class, s.getRateCardId());
        if (rc == null) return BigDecimal.ZERO;
        // pick first matching entry
        Iterator it = rc.getEntries().iterator();
        while (it.hasNext()) {
            RateCardEntry e = (RateCardEntry) it.next();
            if (e.getRouteId() != null && e.getRouteId().equals(s.getRouteId())) {
                BigDecimal weight = Util.nz(s.getWeightKg());
                BigDecimal weightCharge = weight.multiply(Util.nz(e.getPerKgRate()));
                BigDecimal base = Util.nz(e.getBaseCharge());
                // ignore distance for now -- per_km would need route segment lookup. KP 2011.
                BigDecimal total = base.add(weightCharge);
                return total;
            }
        }
        return BigDecimal.ZERO;
    }

    private void recordHistory(Integer shipmentId, String oldS, String newS, String by, String notes) {
        ShipmentStatusHistory h = new ShipmentStatusHistory();
        h.setShipmentId(shipmentId);
        h.setOldStatus(oldS);
        h.setNewStatus(newS);
        h.setChangedBy(by);
        h.setChangedAt(new Date());
        h.setNotes(notes);
        try { shipmentStatusHistoryDao.save(h); } catch (Exception e) { e.printStackTrace(); }
    }

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
