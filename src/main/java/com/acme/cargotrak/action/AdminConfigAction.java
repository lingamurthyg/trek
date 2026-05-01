package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.form.SystemConfigForm;
import com.acme.cargotrak.util.Util;

public class AdminConfigAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        SystemConfigForm scf = (SystemConfigForm) form;
        if ("save".equals(req.getParameter("op")) && Util.isNotBlank(scf.getConfigKey())) {
            cargo().configPut(scf.getConfigKey(), scf.getConfigValue());
            cargo().auditAction(currentUsername(req), "CONFIG", "SystemConfig", scf.getConfigKey(),
                                "value=" + scf.getConfigValue());
        }
        req.setAttribute("configs", cargo().configList());
        return mapping.findForward("success");
    }
}
