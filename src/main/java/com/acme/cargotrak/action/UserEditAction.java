package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.domain.User;
import com.acme.cargotrak.form.UserForm;
import com.acme.cargotrak.util.Util;

public class UserEditAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        UserForm uf = (UserForm) form;
        Integer id = Util.paramAsInteger(req, "id");
        if (id == null) id = uf.getUserId();
        if (id != null) {
            User u = cargo().findUser(id);
            if (u != null) {
                uf.setUserId(u.getUserId());
                uf.setUsername(u.getUsername());
                uf.setEmail(u.getEmail());
                uf.setFullName(u.getFullName());
                uf.setActiveFlag(u.getActiveFlag());
                req.setAttribute("user", u);
            }
        }
        req.setAttribute("roles", cargo().listRoles());
        return mapping.findForward("success");
    }
}
