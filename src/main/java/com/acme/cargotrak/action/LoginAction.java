package com.acme.cargotrak.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.domain.User;
import com.acme.cargotrak.form.LoginForm;
import com.acme.cargotrak.util.Constants;
import com.acme.cargotrak.util.Util;

public class LoginAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        LoginForm lf = (LoginForm) form;
        String ip = Util.getRemoteIp(req);
        User u = cargo().login(lf.getUsername(), lf.getPassword(), ip);
        if (u == null) {
            req.setAttribute("error", "Invalid username or password");
            return mapping.findForward("failure");
        }
        HttpSession session = req.getSession(true);
        session.setAttribute(Constants.SESSION_USER, u);
        session.setAttribute(Constants.SESSION_USER_ID, u.getUserId());
        // also drop the legacy custom userId cookie that AuthFilter trusts
        Cookie c = new Cookie(Constants.COOKIE_USER_ID, String.valueOf(u.getUserId()));
        c.setPath(req.getContextPath().length() == 0 ? "/" : req.getContextPath());
        c.setMaxAge(60 * 60 * 8);
        res.addCookie(c);

        if (Util.isNotBlank(lf.getNext())) {
            res.sendRedirect(req.getContextPath() + lf.getNext());
            return null;
        }
        return mapping.findForward("success");
    }
}
