package com.acme.cargotrak.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.form.ReportForm;
import com.acme.cargotrak.util.DateUtils;
import com.acme.cargotrak.util.Util;

public class ReportShipmentsByRouteAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        ReportForm rf = (ReportForm) form;
        Date from = Util.parseDate(rf.getFromDate());
        Date to = Util.parseDate(rf.getToDate());
        if (from == null) from = DateUtils.addDays(new Date(), -90);
        if (to == null) to = new Date();
        req.setAttribute("rows", cargo().shipmentsByRouteReport(from, to));
        req.setAttribute("from", from);
        req.setAttribute("to", to);
        return mapping.findForward("success");
    }
}
