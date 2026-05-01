package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DashboardAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setAttribute("counts", cargo().dashboardCounts());
        req.setAttribute("recentAudits", cargo().recentAudits(15));
        req.setAttribute("undelivered", cargo().undeliveredNotifications());
        return mapping.findForward("success");
    }
}
