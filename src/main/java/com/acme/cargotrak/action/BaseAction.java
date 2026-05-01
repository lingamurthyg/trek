package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;

import com.acme.cargotrak.domain.User;
import com.acme.cargotrak.service.CargoFacade;
import com.acme.cargotrak.util.Constants;
import com.acme.cargotrak.util.SpringContextHolder;

public abstract class BaseAction extends Action {

    protected CargoFacade cargo() {
        return (CargoFacade) SpringContextHolder.getBean("cargoFacade");
    }

    protected User currentUser(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        if (s == null) return null;
        return (User) s.getAttribute(Constants.SESSION_USER);
    }

    protected String currentUsername(HttpServletRequest req) {
        User u = currentUser(req);
        return u == null ? "anonymous" : u.getUsername();
    }
}
