package com.acme.cargotrak.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.form.InvoiceForm;
import com.acme.cargotrak.util.Util;

public class InvoiceListAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        InvoiceForm inf = (InvoiceForm) form;
        Date from = Util.parseDate(inf.getFromDate());
        Date to = Util.parseDate(inf.getToDate());
        int page = inf.getPage();
        int size = inf.getPageSize() > 0 ? inf.getPageSize() : 25;
        req.setAttribute("results",
            cargo().searchInvoices(inf.getFragment(), inf.getCustomerId(), inf.getStatus(), from, to, page, size));
        req.setAttribute("total",
            new Integer(cargo().countInvoices(inf.getFragment(), inf.getCustomerId(), inf.getStatus(), from, to)));
        req.setAttribute("page", new Integer(page));
        req.setAttribute("pageSize", new Integer(size));
        req.setAttribute("customers", cargo().listActiveCustomers());
        return mapping.findForward("success");
    }
}
