package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.domain.Driver;
import com.acme.cargotrak.form.DriverForm;
import com.acme.cargotrak.util.Util;

public class DriverEditAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        DriverForm df = (DriverForm) form;
        Integer id = Util.paramAsInteger(req, "id");
        if (id != null) {
            Driver d = cargo().findDriver(id);
            if (d != null) {
                df.setDriverId(d.getDriverId());
                df.setEmployeeCode(d.getEmployeeCode());
                df.setFullName(d.getFullName());
                df.setLicenseNumber(d.getLicenseNumber());
                df.setLicenseExpiry(d.getLicenseExpiry());
                df.setPhone(d.getPhone());
                df.setActiveFlag(d.getActiveFlag());
                req.setAttribute("driver", d);
            }
        }
        return mapping.findForward("success");
    }
}
