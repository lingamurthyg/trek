package com.acme.cargotrak.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import java.sql.SQLException;

import com.acme.cargotrak.dao.UserDao;
import com.acme.cargotrak.domain.User;

public class UserDaoImpl extends AbstractHibernateDao implements UserDao {

    public User findByUsername(String username) {
        List l = getHibernateTemplate().find(
                "from com.acme.cargotrak.domain.User u where u.username = ?", username);
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
        getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
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
