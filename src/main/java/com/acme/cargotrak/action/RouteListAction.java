package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.service.RouteService;
import com.acme.cargotrak.util.SpringContextHolder;

public class RouteListAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        RouteService rs = (RouteService) SpringContextHolder.getBean("routeService");
        req.setAttribute("routes", rs.listAll());
        return mapping.findForward("success");
    }
}
