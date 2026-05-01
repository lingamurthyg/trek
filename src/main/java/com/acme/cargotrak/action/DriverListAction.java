package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DriverListAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setAttribute("drivers", cargo().listDrivers());
        req.setAttribute("expiring", cargo().driversWithExpiringLicense(60));
        return mapping.findForward("success");
    }
}
