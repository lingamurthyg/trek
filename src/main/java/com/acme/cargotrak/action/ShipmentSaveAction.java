package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.domain.Shipment;
import com.acme.cargotrak.form.ShipmentForm;

public class ShipmentSaveAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        ShipmentForm sf = (ShipmentForm) form;
        Shipment s;
        if (sf.getShipmentId() != null) {
            s = cargo().findShipment(sf.getShipmentId());
            if (s == null) s = new Shipment();
        } else {
            s = new Shipment();
        }
        s.setTrackingNo(sf.getTrackingNo());
        s.setCustomerId(sf.getCustomerId());
        s.setOrigin(sf.getOrigin());
        s.setDestination(sf.getDestination());
        s.setWeightKg(sf.getWeightKg());
        s.setVolumeM3(sf.getVolumeM3());
        s.setDeclaredValue(sf.getDeclaredValue());
        s.setDriverId(sf.getDriverId());
        s.setVehicleId(sf.getVehicleId());
        s.setRouteId(sf.getRouteId());
        s.setRateCardId(sf.getRateCardId());
        s.setNotes(sf.getNotes());

        Integer id;
        if (sf.getShipmentId() == null) {
            id = cargo().createShipment(s, currentUsername(req));
        } else {
            id = sf.getShipmentId();
            // delegate update via direct facade write (no dedicated method, use service through DAO)
            // not the cleanest but legacy code does this
            com.acme.cargotrak.dao.ShipmentDao dao =
                (com.acme.cargotrak.dao.ShipmentDao) com.acme.cargotrak.util.SpringContextHolder.getBean("shipmentDao");
            dao.update(s);
            cargo().auditAction(currentUsername(req), "UPDATE", "Shipment", id.toString(), "");
        }
        req.setAttribute("savedId", id);
        return mapping.findForward("success");
    }
}
