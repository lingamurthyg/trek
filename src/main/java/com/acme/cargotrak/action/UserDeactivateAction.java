package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.util.Util;

public class UserDeactivateAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        Integer id = Util.paramAsInteger(req, "id");
        if (id != null) {
            cargo().deactivateUser(id);
            cargo().auditAction(currentUsername(req), "DEACTIVATE", "User", id.toString(), "");
        }
        return mapping.findForward("success");
    }
}
