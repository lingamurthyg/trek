package com.acme.cargotrak.dao.hibernate;

import java.util.List;

import com.acme.cargotrak.dao.NotificationDao;

public class NotificationDaoImpl extends AbstractHibernateDao implements NotificationDao {

    public List findRecent(int limit) {
        getHibernateTemplate().setMaxResults(limit);
        try {
            return getHibernateTemplate().find(
                "from com.acme.cargotrak.domain.Notification n order by n.sentAt desc");
        } finally {
            getHibernateTemplate().setMaxResults(0);
        }
    }

    public List findUndelivered() {
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.Notification n where n.deliveredFlag = 'N' order by n.sentAt");
    }
}
