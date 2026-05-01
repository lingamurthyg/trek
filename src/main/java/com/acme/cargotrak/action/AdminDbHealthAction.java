package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.service.AdminService;
import com.acme.cargotrak.util.SpringContextHolder;

public class AdminDbHealthAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        AdminService as = (AdminService) SpringContextHolder.getBean("adminService");
        req.setAttribute("tableStatus", as.showTableStatus());
        return mapping.findForward("success");
    }
}
