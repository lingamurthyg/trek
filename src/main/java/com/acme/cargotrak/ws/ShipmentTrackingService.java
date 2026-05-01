package com.acme.cargotrak.ws;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.acme.cargotrak.domain.Shipment;
import com.acme.cargotrak.service.CargoFacade;
import com.acme.cargotrak.util.SpringContextHolder;
import com.acme.cargotrak.util.Util;

/**
 * SOAP endpoint exposed via Axis 1.4 at /services/ShipmentTrackingService.
 *
 * Migrated:
 *  - new Integer(customerId) -> Integer.valueOf(customerId) (deprecated primitive wrapper constructor removed in Java 21)
 *
 * @author Rajesh Kumar 2009-04 (initial API)
 * @author S. Patel 2014-05 added listShipmentsByCustomer
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class ShipmentTrackingService {

    private static final Logger logger = Logger.getLogger(ShipmentTrackingService.class);

    /** returns the current status string, or "UNKNOWN" if no such shipment */
    public String getShipmentStatus(String trackingNo) {
        try {
            CargoFacade f = (CargoFacade) SpringContextHolder.getBean("cargoFacade");
            Shipment s = f.findShipmentByTracking(trackingNo);
            if (s == null) return "UNKNOWN";
            return Util.nvl(s.getStatus(), "UNKNOWN");
        } catch (Exception e) {
            logger.error("getShipmentStatus failed", e);
            return "ERROR";
        }
    }

    /** returns true if the transition was applied */
    public boolean updateShipmentStatus(String trackingNo, String newStatus, String changedBy) {
        try {
            CargoFacade f = (CargoFacade) SpringContextHolder.getBean("cargoFacade");
            Shipment s = f.findShipmentByTracking(trackingNo);
            if (s == null) return false;
            return f.transitionShipment(s.getShipmentId(), newStatus, Util.nvl(changedBy, "soap"));
        } catch (Exception e) {
            logger.error("updateShipmentStatus failed", e);
            return false;
        }
    }

    /** returns tracking numbers for shipments belonging to the given customer */
    public String[] listShipmentsByCustomer(int customerId) {
        try {
            CargoFacade f = (CargoFacade) SpringContextHolder.getBean("cargoFacade");
            // Use Integer.valueOf() instead of deprecated new Integer() constructor (removed in Java 21)
            List l = f.listShipmentsByCustomer(Integer.valueOf(customerId));
            List<String> out = new ArrayList<String>();
            Iterator it = l.iterator();
            while (it.hasNext()) {
                Shipment s = (Shipment) it.next();
                out.add(s.getTrackingNo());
            }
            return out.toArray(new String[out.size()]);
        } catch (Exception e) {
            logger.error("listShipmentsByCustomer failed", e);
            return new String[0];
        }
    }
}
