package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.domain.User;
import com.acme.cargotrak.form.UserForm;
import com.acme.cargotrak.util.Util;

public class UserSaveAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        UserForm uf = (UserForm) form;
        Integer id = uf.getUserId();
        if (id == null) {
            id = cargo().createUser(uf.getUsername(),
                    Util.nvl(uf.getPassword(), "changeme"),
                    uf.getEmail(), uf.getFullName());
        } else {
            User u = cargo().findUser(id);
            if (u != null) {
                u.setEmail(uf.getEmail());
                u.setFullName(uf.getFullName());
                if (uf.getActiveFlag() != null) u.setActiveFlag(uf.getActiveFlag());
                if (Util.isNotBlank(uf.getPassword())) {
                    u.setPasswordHash(cargo().passwordHashFor(uf.getPassword()));
                }
                cargo().updateUser(u);
            }
        }
        if (uf.getRoleNames() != null) {
            for (int i = 0; i < uf.getRoleNames().length; i++) {
                cargo().assignRole(id, uf.getRoleNames()[i]);
            }
        }
        cargo().auditAction(currentUsername(req), "SAVE", "User", String.valueOf(id), "user saved via web");
        return mapping.findForward("success");
    }
}
