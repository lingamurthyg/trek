package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.domain.Vehicle;
import com.acme.cargotrak.form.VehicleForm;
import com.acme.cargotrak.util.Util;

public class VehicleEditAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        VehicleForm vf = (VehicleForm) form;
        Integer id = Util.paramAsInteger(req, "id");
        if (id != null) {
            Vehicle v = cargo().findVehicle(id);
            if (v != null) {
                vf.setVehicleId(v.getVehicleId());
                vf.setPlateNumber(v.getPlateNumber());
                vf.setMake(v.getMake());
                vf.setModel(v.getModel());
                vf.setYear(v.getYear());
                vf.setCapacityKg(v.getCapacityKg());
                vf.setVehicleType(v.getVehicleType());
                vf.setStatus(v.getStatus());
                req.setAttribute("vehicle", v);
                req.setAttribute("maintenance", cargo().listVehicleMaintenance(id));
            }
        }
        return mapping.findForward("success");
    }
}
