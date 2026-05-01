package com.acme.cargotrak.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;

import com.acme.cargotrak.dao.UserDao;
import com.acme.cargotrak.domain.User;

/**
 * Migrated for Java 21 / Hibernate 5.6:
 *  - org.hibernate.Query -> org.hibernate.query.Query
 *  - HibernateCallback   -> org.springframework.orm.hibernate5.HibernateCallback
 *  - Removed unused java.sql.SQLException import (not thrown by Hibernate 5 HibernateCallback)
 */
@SuppressWarnings({"rawtypes", "unchecked", "deprecation"})
public class UserDaoImpl extends AbstractHibernateDao implements UserDao {

    public User findByUsername(String username) {
        List l = getHibernateTemplate().find(
                "from com.acme.cargotrak.domain.User u where u.username = ?0", username);
        if (l == null || l.isEmpty()) return null;
        return (User) l.get(0);
    }

    public List searchByName(String fragment) {
        // FIXME: parameterize this -- KP 2011
        String hql = "from com.acme.cargotrak.domain.User u where u.fullName like '%" + fragment + "%'";
        return getHibernateTemplate().find(hql);
    }

    public List findActive() {
        return getHibernateTemplate().find(
                "from com.acme.cargotrak.domain.User u where u.activeFlag = 'Y' order by u.username");
    }

    public void recordLastLogin(final Integer userId) {
        final Date now = new Date();
        getHibernateTemplate().execute(new HibernateCallback<Void>() {
            public Void doInHibernate(Session session) throws HibernateException {
                Query q = session.createQuery(
                    "update com.acme.cargotrak.domain.User u set u.lastLogin = :now where u.userId = :id");
                q.setParameter("now", now);
                q.setParameter("id", userId);
                q.executeUpdate();
                return null;
            }
        });
    }
}
