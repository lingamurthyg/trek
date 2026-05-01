package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.domain.Payment;
import com.acme.cargotrak.form.PaymentForm;

public class PaymentRecordAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        PaymentForm pf = (PaymentForm) form;
        Payment p = new Payment();
        p.setCustomerId(pf.getCustomerId());
        p.setAmount(pf.getAmount());
        p.setMethod(pf.getMethod());
        p.setReference(pf.getReference());
        p.setNotes(pf.getNotes());
        Integer pid = cargo().recordPayment(p);
        if (pid != null && pf.getInvoiceId() != null && pf.getAmount() != null) {
            cargo().allocatePayment(pid, pf.getInvoiceId(), pf.getAmount());
        }
        cargo().auditAction(currentUsername(req), "PAYMENT", "Customer", String.valueOf(pf.getCustomerId()),
                            "amount=" + pf.getAmount());
        req.setAttribute("paymentId", pid);
        return mapping.findForward("success");
    }
}
