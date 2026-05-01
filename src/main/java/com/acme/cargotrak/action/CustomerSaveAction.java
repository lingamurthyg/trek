package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.domain.Customer;
import com.acme.cargotrak.form.CustomerForm;

public class CustomerSaveAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        CustomerForm cf = (CustomerForm) form;
        Customer c;
        if (cf.getCustomerId() != null) {
            c = cargo().findCustomer(cf.getCustomerId());
            if (c == null) c = new Customer();
        } else {
            c = new Customer();
        }
        c.setCustomerCode(cf.getCustomerCode());
        c.setName(cf.getName());
        c.setIndustry(cf.getIndustry());
        c.setCreditLimit(cf.getCreditLimit());
        c.setPaymentTerms(cf.getPaymentTerms());
        c.setActiveFlag(cf.getActiveFlag() == null ? "Y" : cf.getActiveFlag());
        Integer id;
        if (cf.getCustomerId() == null) {
            id = cargo().createCustomer(c);
        } else {
            cargo().updateCustomer(c);
            id = c.getCustomerId();
        }
        cargo().auditAction(currentUsername(req), "SAVE", "Customer", String.valueOf(id), "");
        req.setAttribute("savedId", id);
        return mapping.findForward("success");
    }
}
