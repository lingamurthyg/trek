package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.domain.Driver;
import com.acme.cargotrak.form.DriverForm;

public class DriverSaveAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        DriverForm df = (DriverForm) form;
        Driver d;
        if (df.getDriverId() != null) {
            d = cargo().findDriver(df.getDriverId());
            if (d == null) d = new Driver();
        } else {
            d = new Driver();
        }
        d.setEmployeeCode(df.getEmployeeCode());
        d.setFullName(df.getFullName());
        d.setLicenseNumber(df.getLicenseNumber());
        d.setLicenseExpiry(df.getLicenseExpiry());
        d.setPhone(df.getPhone());
        d.setActiveFlag(df.getActiveFlag() == null ? "Y" : df.getActiveFlag());
        Integer id;
        if (df.getDriverId() == null) {
            id = cargo().createDriver(d);
        } else {
            id = df.getDriverId();
            // direct DAO call (legacy)
            ((com.acme.cargotrak.dao.DriverDao) com.acme.cargotrak.util.SpringContextHolder.getBean("driverDao")).update(d);
        }
        cargo().auditAction(currentUsername(req), "SAVE", "Driver", String.valueOf(id), "");
        req.setAttribute("savedId", id);
        return mapping.findForward("success");
    }
}
