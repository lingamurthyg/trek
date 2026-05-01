package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.form.CustomerForm;

public class CustomerListAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        CustomerForm cf = (CustomerForm) form;
        int page = cf.getPage();
        int size = cf.getPageSize() > 0 ? cf.getPageSize() : 25;
        req.setAttribute("results",
            cargo().searchCustomers(cf.getNamePart(), cf.getIndustry(), cf.getActiveFlag(), page, size));
        int total = cargo().countCustomers(cf.getNamePart(), cf.getIndustry(), cf.getActiveFlag());
        req.setAttribute("total", new Integer(total));
        req.setAttribute("page", new Integer(page));
        req.setAttribute("pageSize", new Integer(size));
        return mapping.findForward("success");
    }
}
