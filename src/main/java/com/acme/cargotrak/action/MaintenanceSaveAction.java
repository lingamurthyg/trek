package com.acme.cargotrak.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.domain.VehicleMaintenance;
import com.acme.cargotrak.form.MaintenanceForm;
import com.acme.cargotrak.service.VehicleService;
import com.acme.cargotrak.util.SpringContextHolder;
import com.acme.cargotrak.util.Util;

public class MaintenanceSaveAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        MaintenanceForm mf = (MaintenanceForm) form;
        VehicleService vs = (VehicleService) SpringContextHolder.getBean("vehicleService");
        VehicleMaintenance m = new VehicleMaintenance();
        Date d = Util.parseDate(mf.getMaintDate());
        m.setMaintDate(d == null ? new Date() : d);
        m.setDescription(mf.getDescription());
        m.setCost(mf.getCost());
        m.setOdometer(mf.getOdometer());
        m.setTechnician(mf.getTechnician());
        vs.addMaintenance(mf.getVehicleId(), m);
        req.setAttribute("vehicleId", mf.getVehicleId());
        return mapping.findForward("success");
    }
}
