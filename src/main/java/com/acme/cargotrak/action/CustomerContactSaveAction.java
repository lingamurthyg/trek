package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.domain.CustomerContact;
import com.acme.cargotrak.form.CustomerContactForm;
import com.acme.cargotrak.service.CustomerService;
import com.acme.cargotrak.util.SpringContextHolder;

public class CustomerContactSaveAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        CustomerContactForm ccf = (CustomerContactForm) form;
        CustomerService cs = (CustomerService) SpringContextHolder.getBean("customerService");
        CustomerContact c = new CustomerContact();
        c.setContactName(ccf.getContactName());
        c.setTitle(ccf.getTitle());
        c.setEmail(ccf.getEmail());
        c.setPhone(ccf.getPhone());
        c.setPrimaryFlag(ccf.getPrimaryFlag());
        cs.addContact(ccf.getCustomerId(), c);
        req.setAttribute("customerId", ccf.getCustomerId());
        return mapping.findForward("success");
    }
}
