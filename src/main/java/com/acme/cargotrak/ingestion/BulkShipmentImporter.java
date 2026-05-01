package com.acme.cargotrak.ingestion;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.acme.cargotrak.dao.CustomerDao;
import com.acme.cargotrak.dao.RouteDao;
import com.acme.cargotrak.domain.Customer;
import com.acme.cargotrak.domain.Route;
import com.acme.cargotrak.domain.Shipment;
import com.acme.cargotrak.service.ShipmentService;
import com.acme.cargotrak.util.Constants;

public class BulkShipmentImporter {

    private static final Logger logger = Logger.getLogger(BulkShipmentImporter.class);

    private CustomerDao customerDao;
    private RouteDao routeDao;
    private ShipmentService shipmentService;

    public void setCustomerDao(CustomerDao d) { this.customerDao = d; }
    public void setRouteDao(RouteDao d) { this.routeDao = d; }
    public void setShipmentService(ShipmentService s) { this.shipmentService = s; }

    public int importShipments(IngestResult res) {
        int created = 0;
        Iterator it = res.getRows().iterator();
        while (it.hasNext()) {
            ParsedShipmentRow row = (ParsedShipmentRow) it.next();
            Customer c = customerDao.findByCode(row.getCustomerCode());
            if (c == null) {
                res.getRejections().add("unknown customer code: " + row.getCustomerCode());
                continue;
            }
            Shipment s = new Shipment();
            s.setCustomerId(c.getCustomerId());
            s.setOrigin(row.getOrigin());
            s.setDestination(row.getDestination());
            s.setWeightKg(row.getWeightKg());
            s.setVolumeM3(row.getVolumeM3());
            s.setDeclaredValue(row.getDeclaredValue());
            s.setPickupDate(row.getPickupDate());
            s.setNotes(row.getNotes());
            s.setStatus(Constants.STATUS_DRAFT);
            if (row.getRouteCode() != null && row.getRouteCode().length() > 0) {
                Route r = routeDao.findByCode(row.getRouteCode());
                if (r != null) s.setRouteId(r.getRouteId());
            }
            try {
                shipmentService.createShipment(s, "ingestion");
                created++;
            } catch (Exception e) {
                logger.error("error");
            }
        }
        return created;
    }

    public int applyStatusUpdates(IngestResult res) {
        int updated = 0;
        Iterator it = res.getStatusUpdates().iterator();
        while (it.hasNext()) {
            ParsedStatusRow row = (ParsedStatusRow) it.next();
            Shipment s = shipmentService.findByTrackingNo(row.getTrackingNo());
            if (s == null) {
                res.getRejections().add("unknown tracking no: " + row.getTrackingNo());
                continue;
            }
            boolean ok = shipmentService.transition(s.getShipmentId(), row.getNewStatus(), "edi214");
            if (ok) updated++;
        }
        return updated;
    }
}
