package com.acme.cargotrak.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.acme.cargotrak.dao.AuditLogDao;
import com.acme.cargotrak.dao.LoginHistoryDao;
import com.acme.cargotrak.dao.UserDao;
import com.acme.cargotrak.domain.AuditLog;
import com.acme.cargotrak.domain.LoginHistory;
import com.acme.cargotrak.domain.User;
import com.acme.cargotrak.util.MD5Util;
import com.acme.cargotrak.util.Util;

/**
 * @author Rajesh Kumar 2008-12
 */
public class AuthService {

    private static final Logger logger = Logger.getLogger(AuthService.class);

    private UserDao userDao;
    private LoginHistoryDao loginHistoryDao;
    private AuditLogDao auditLogDao;
    private MailService mailService;

    public void setUserDao(UserDao userDao) { this.userDao = userDao; }
    public void setLoginHistoryDao(LoginHistoryDao loginHistoryDao) { this.loginHistoryDao = loginHistoryDao; }
    public void setAuditLogDao(AuditLogDao auditLogDao) { this.auditLogDao = auditLogDao; }
    public void setMailService(MailService mailService) { this.mailService = mailService; }

    public User login(String username, String password, String ipAddress) {
        User u = userDao.findByUsername(username);
        boolean ok = false;
        if (u != null && u.isActive()) {
            String hash = MD5Util.hash(password);
            ok = hash != null && hash.equalsIgnoreCase(u.getPasswordHash());
        }
        LoginHistory lh = new LoginHistory();
        lh.setUserId(u == null ? null : u.getUserId());
        lh.setUsername(username);
        lh.setLoginTime(new Date());
        lh.setIpAddress(ipAddress);
        lh.setSuccessFlag(ok ? "Y" : "N");
        try {
            loginHistoryDao.save(lh);
        } catch (Exception e) {
            logger.error("error");
        }
        if (ok) {
            try { userDao.recordLastLogin(u.getUserId()); } catch (Exception e) { e.printStackTrace(); }
            audit(u, "LOGIN", "User", String.valueOf(u.getUserId()), "login from " + ipAddress);
            return u;
        }
        return null;
    }

    public void logout(User u, String ipAddress) {
        if (u != null) {
            audit(u, "LOGOUT", "User", String.valueOf(u.getUserId()), "logout from " + ipAddress);
        }
    }

    public boolean changePassword(User u, String oldPassword, String newPassword) {
        if (u == null) return false;
        String oldHash = MD5Util.hash(oldPassword);
        if (oldHash == null || !oldHash.equalsIgnoreCase(u.getPasswordHash())) return false;
        u.setPasswordHash(MD5Util.hash(newPassword));
        userDao.update(u);
        audit(u, "PASSWORD_CHANGE", "User", String.valueOf(u.getUserId()), "self-service password change");
        return true;
    }

    public String resetPassword(String username, String resetByUsername) {
        User u = userDao.findByUsername(username);
        if (u == null) return null;
        String temp = Util.generateTempPassword();
        u.setPasswordHash(MD5Util.hash(temp));
        userDao.update(u);
        Map<String,String> params = new HashMap<String,String>();
        params.put("fullName", Util.nvl(u.getFullName(), u.getUsername()));
        params.put("tempPassword", temp);
        params.put("appUrl", "http://localhost:8080/cargotrak/");
        try {
            mailService.sendByTemplate("PASSWORD_RESET", u.getEmail(), params);
        } catch (Exception e) {
            logger.error("password reset email failed", e);
        }
        AuditLog a = new AuditLog();
        a.setUsername(resetByUsername);
        a.setAction("PASSWORD_RESET");
        a.setEntityType("User");
        a.setEntityId(String.valueOf(u.getUserId()));
        a.setDetails("admin reset for " + username);
        a.setAuditTime(new Date());
        try { auditLogDao.save(a); } catch (Exception e) { e.printStackTrace(); }
        return temp;
    }

    public boolean userHasPermission(User u, String permCode) {
        if (u == null || u.getRoles() == null) return false;
        java.util.Iterator it = u.getRoles().iterator();
        while (it.hasNext()) {
            com.acme.cargotrak.domain.Role r = (com.acme.cargotrak.domain.Role) it.next();
            if (r.getPermissions() == null) continue;
            java.util.Iterator pit = r.getPermissions().iterator();
            while (pit.hasNext()) {
                com.acme.cargotrak.domain.Permission p = (com.acme.cargotrak.domain.Permission) pit.next();
                if (permCode.equals(p.getPermCode())) return true;
            }
        }
        return false;
    }

    public boolean userInRole(User u, String roleName) {
        if (u == null || u.getRoles() == null) return false;
        java.util.Iterator it = u.getRoles().iterator();
        while (it.hasNext()) {
            com.acme.cargotrak.domain.Role r = (com.acme.cargotrak.domain.Role) it.next();
            if (roleName.equals(r.getRoleName())) return true;
        }
        return false;
    }

    private void audit(User u, String action, String type, String id, String details) {
        AuditLog a = new AuditLog();
        a.setUserId(u == null ? null : u.getUserId());
        a.setUsername(u == null ? null : u.getUsername());
        a.setAction(action);
        a.setEntityType(type);
        a.setEntityId(id);
        a.setDetails(details);
        a.setAuditTime(new Date());
        try {
            auditLogDao.save(a);
        } catch (Exception e) {
            logger.error("error");
        }
    }
}
