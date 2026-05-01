package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.domain.Customer;
import com.acme.cargotrak.form.CustomerForm;
import com.acme.cargotrak.util.Util;

public class CustomerEditAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        CustomerForm cf = (CustomerForm) form;
        Integer id = Util.paramAsInteger(req, "id");
        if (id == null) id = cf.getCustomerId();
        if (id != null) {
            Customer c = cargo().findCustomer(id);
            if (c != null) {
                cf.setCustomerId(c.getCustomerId());
                cf.setCustomerCode(c.getCustomerCode());
                cf.setName(c.getName());
                cf.setIndustry(c.getIndustry());
                cf.setCreditLimit(c.getCreditLimit());
                cf.setPaymentTerms(c.getPaymentTerms());
                cf.setActiveFlag(c.getActiveFlag());
                req.setAttribute("customer", c);
                req.setAttribute("contacts", cargo().listCustomerContacts(id));
                req.setAttribute("addresses", cargo().listCustomerAddresses(id));
            }
        }
        return mapping.findForward("success");
    }
}
