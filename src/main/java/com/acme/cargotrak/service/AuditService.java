package com.acme.cargotrak.service;

import java.util.Date;
import java.util.List;

import com.acme.cargotrak.dao.AuditLogDao;
import com.acme.cargotrak.domain.AuditLog;

public class AuditService {

    private AuditLogDao auditLogDao;
    public void setAuditLogDao(AuditLogDao d) { this.auditLogDao = d; }

    public void log(String username, String action, String entityType, String entityId, String details) {
        AuditLog a = new AuditLog();
        a.setUsername(username);
        a.setAction(action);
        a.setEntityType(entityType);
        a.setEntityId(entityId);
        a.setDetails(details);
        a.setAuditTime(new Date());
        try { auditLogDao.save(a); } catch (Exception e) { e.printStackTrace(); }
    }

    public List recent(int limit) { return auditLogDao.findRecent(limit); }
    public List byEntity(String type, String id) { return auditLogDao.findByEntity(type, id); }
    public List between(Date from, Date to) { return auditLogDao.findBetween(from, to); }
}
