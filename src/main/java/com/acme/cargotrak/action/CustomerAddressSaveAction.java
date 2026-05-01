package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.domain.CustomerAddress;
import com.acme.cargotrak.form.CustomerAddressForm;
import com.acme.cargotrak.service.CustomerService;
import com.acme.cargotrak.util.SpringContextHolder;

public class CustomerAddressSaveAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        CustomerAddressForm caf = (CustomerAddressForm) form;
        CustomerService cs = (CustomerService) SpringContextHolder.getBean("customerService");
        CustomerAddress a = new CustomerAddress();
        a.setAddressType(caf.getAddressType());
        a.setLine1(caf.getLine1());
        a.setLine2(caf.getLine2());
        a.setCity(caf.getCity());
        a.setState(caf.getState());
        a.setPostalCode(caf.getPostalCode());
        a.setCountry(caf.getCountry());
        cs.addAddress(caf.getCustomerId(), a);
        req.setAttribute("customerId", caf.getCustomerId());
        return mapping.findForward("success");
    }
}
