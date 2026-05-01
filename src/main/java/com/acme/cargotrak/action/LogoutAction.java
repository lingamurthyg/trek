package com.acme.cargotrak.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.util.Constants;
import com.acme.cargotrak.util.Util;

public class LogoutAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        cargo().logout(currentUser(req), Util.getRemoteIp(req));
        HttpSession s = req.getSession(false);
        if (s != null) s.invalidate();
        Cookie c = new Cookie(Constants.COOKIE_USER_ID, "");
        c.setPath(req.getContextPath().length() == 0 ? "/" : req.getContextPath());
        c.setMaxAge(0);
        res.addCookie(c);
        return mapping.findForward("success");
    }
}
