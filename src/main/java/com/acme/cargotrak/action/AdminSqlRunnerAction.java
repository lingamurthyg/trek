package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.form.SqlRunnerForm;
import com.acme.cargotrak.service.AdminService;
import com.acme.cargotrak.util.SpringContextHolder;
import com.acme.cargotrak.util.Util;

/**
 * The "run arbitrary SQL" page. Yes really.
 */
public class AdminSqlRunnerAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        SqlRunnerForm sf = (SqlRunnerForm) form;
        AdminService as = (AdminService) SpringContextHolder.getBean("adminService");
        if (Util.isNotBlank(sf.getSql())) {
            if ("UPDATE".equalsIgnoreCase(sf.getMode())) {
                int n = as.runUpdate(sf.getSql());
                // Use Integer.valueOf() instead of deprecated new Integer() constructor (removed in Java 21)
                req.setAttribute("affected", Integer.valueOf(n));
            } else {
                req.setAttribute("rows", as.runQuery(sf.getSql()));
            }
            cargo().auditAction(currentUsername(req), "SQL_RUN", "Admin", "*", "sql=" + sf.getSql());
        }
        return mapping.findForward("success");
    }
}
