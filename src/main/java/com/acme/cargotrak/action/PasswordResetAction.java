package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.form.PasswordResetForm;

public class PasswordResetAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        PasswordResetForm prf = (PasswordResetForm) form;
        if ("self".equals(req.getParameter("op"))) {
            boolean ok = cargo().changePassword(currentUser(req), prf.getOldPassword(), prf.getNewPassword());
            req.setAttribute("ok", new Boolean(ok));
        } else {
            String temp = cargo().resetPassword(prf.getUsername(), currentUsername(req));
            req.setAttribute("tempPassword", temp);
        }
        return mapping.findForward("success");
    }
}
