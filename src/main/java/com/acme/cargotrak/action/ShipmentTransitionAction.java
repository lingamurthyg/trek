package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.form.ShipmentForm;

public class ShipmentTransitionAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        ShipmentForm sf = (ShipmentForm) form;
        boolean ok = false;
        if (sf.getShipmentId() != null && sf.getNewStatus() != null) {
            ok = cargo().transitionShipment(sf.getShipmentId(), sf.getNewStatus(), currentUsername(req));
        }
        req.setAttribute("ok", Boolean.valueOf(ok));
        req.setAttribute("shipmentId", sf.getShipmentId());
        return mapping.findForward("success");
    }
}
