package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.domain.Shipment;
import com.acme.cargotrak.form.ShipmentForm;
import com.acme.cargotrak.util.Util;

public class ShipmentEditAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        ShipmentForm sf = (ShipmentForm) form;
        Integer id = Util.paramAsInteger(req, "id");
        if (id == null) id = sf.getShipmentId();
        if (id != null) {
            Shipment s = cargo().findShipment(id);
            if (s != null) {
                sf.setShipmentId(s.getShipmentId());
                sf.setTrackingNo(s.getTrackingNo());
                sf.setCustomerId(s.getCustomerId());
                sf.setOrigin(s.getOrigin());
                sf.setDestination(s.getDestination());
                sf.setWeightKg(s.getWeightKg());
                sf.setVolumeM3(s.getVolumeM3());
                sf.setDeclaredValue(s.getDeclaredValue());
                sf.setStatus(s.getStatus());
                sf.setDriverId(s.getDriverId());
                sf.setVehicleId(s.getVehicleId());
                sf.setRouteId(s.getRouteId());
                sf.setRateCardId(s.getRateCardId());
                sf.setNotes(s.getNotes());
                req.setAttribute("shipment", s);
                req.setAttribute("legs", cargo().listShipmentLegs(id));
                req.setAttribute("history", cargo().listShipmentHistory(id));
            }
        }
        req.setAttribute("customers", cargo().listActiveCustomers());
        req.setAttribute("drivers", cargo().listActiveDrivers());
        req.setAttribute("vehicles", cargo().listAvailableVehicles());
        return mapping.findForward("success");
    }
}
