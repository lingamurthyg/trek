package com.acme.cargotrak.dao.hibernate;

import java.util.Date;
import java.util.List;

import com.acme.cargotrak.dao.AuditLogDao;

public class AuditLogDaoImpl extends AbstractHibernateDao implements AuditLogDao {

    public List findRecent(int limit) {
        getHibernateTemplate().setMaxResults(limit);
        try {
            return getHibernateTemplate().find(
                "from com.acme.cargotrak.domain.AuditLog a order by a.auditTime desc");
        } finally {
            getHibernateTemplate().setMaxResults(0);
        }
    }

    public List findByEntity(String entityType, String entityId) {
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.AuditLog a where a.entityType = ? and a.entityId = ? order by a.auditTime desc",
            new Object[] { entityType, entityId });
    }

    public List findBetween(Date from, Date to) {
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.AuditLog a where a.auditTime between ? and ? order by a.auditTime desc",
            new Object[] { from, to });
    }
}
