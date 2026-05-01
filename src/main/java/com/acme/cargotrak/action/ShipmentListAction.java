package com.acme.cargotrak.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.form.ShipmentForm;
import com.acme.cargotrak.util.Util;

public class ShipmentListAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        ShipmentForm sf = (ShipmentForm) form;
        Date from = Util.parseDate(sf.getFromDate());
        Date to = Util.parseDate(sf.getToDate());
        int page = sf.getPage();
        int size = sf.getPageSize() > 0 ? sf.getPageSize() : 25;
        req.setAttribute("results",
            cargo().searchShipments(sf.getTrackingFragment(), sf.getCustomerId(), sf.getStatus(),
                                    from, to, page, size));
        int total = cargo().countShipments(sf.getTrackingFragment(), sf.getCustomerId(), sf.getStatus(), from, to);
        // Use Integer.valueOf() instead of deprecated new Integer() constructor (removed in Java 21)
        req.setAttribute("total", Integer.valueOf(total));
        req.setAttribute("page", Integer.valueOf(page));
        req.setAttribute("pageSize", Integer.valueOf(size));
        req.setAttribute("customers", cargo().listActiveCustomers());
        return mapping.findForward("success");
    }
}
