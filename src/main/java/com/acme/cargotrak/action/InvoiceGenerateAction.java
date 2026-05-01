package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.form.InvoiceForm;

public class InvoiceGenerateAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        InvoiceForm inf = (InvoiceForm) form;
        Integer iid = null;
        if (inf.getShipmentId() != null) {
            iid = cargo().generateInvoice(inf.getShipmentId(), currentUsername(req));
        }
        req.setAttribute("invoiceId", iid);
        return mapping.findForward("success");
    }
}
