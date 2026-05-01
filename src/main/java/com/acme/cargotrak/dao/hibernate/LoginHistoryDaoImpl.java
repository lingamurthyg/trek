package com.acme.cargotrak.dao.hibernate;

import java.util.Collections;
import java.util.List;

import com.acme.cargotrak.dao.LoginHistoryDao;

public class LoginHistoryDaoImpl extends AbstractHibernateDao implements LoginHistoryDao {

    public List findRecentByUser(Integer userId, int limit) {
        if (userId == null) return Collections.EMPTY_LIST;
        getHibernateTemplate().setMaxResults(limit);
        try {
            return getHibernateTemplate().find(
                "from com.acme.cargotrak.domain.LoginHistory lh where lh.userId = ? order by lh.loginTime desc",
                userId);
        } finally {
            getHibernateTemplate().setMaxResults(0);
        }
    }
}
