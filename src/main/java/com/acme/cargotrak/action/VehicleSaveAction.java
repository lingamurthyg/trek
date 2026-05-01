package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.domain.Vehicle;
import com.acme.cargotrak.form.VehicleForm;

public class VehicleSaveAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        VehicleForm vf = (VehicleForm) form;
        Vehicle v;
        if (vf.getVehicleId() != null) {
            v = cargo().findVehicle(vf.getVehicleId());
            if (v == null) v = new Vehicle();
        } else {
            v = new Vehicle();
        }
        v.setPlateNumber(vf.getPlateNumber());
        v.setMake(vf.getMake());
        v.setModel(vf.getModel());
        v.setYear(vf.getYear());
        v.setCapacityKg(vf.getCapacityKg());
        v.setVehicleType(vf.getVehicleType());
        v.setStatus(vf.getStatus() == null ? "AVAILABLE" : vf.getStatus());
        Integer id;
        if (vf.getVehicleId() == null) {
            id = cargo().createVehicle(v);
        } else {
            id = vf.getVehicleId();
            ((com.acme.cargotrak.dao.VehicleDao) com.acme.cargotrak.util.SpringContextHolder.getBean("vehicleDao")).update(v);
        }
        cargo().auditAction(currentUsername(req), "SAVE", "Vehicle", String.valueOf(id), "");
        req.setAttribute("savedId", id);
        return mapping.findForward("success");
    }
}
