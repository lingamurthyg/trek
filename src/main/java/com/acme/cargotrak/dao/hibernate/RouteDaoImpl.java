package com.acme.cargotrak.dao.hibernate;

import java.util.List;

import com.acme.cargotrak.dao.RouteDao;
import com.acme.cargotrak.domain.Route;

public class RouteDaoImpl extends AbstractHibernateDao implements RouteDao {

    public Route findByCode(String code) {
        List l = getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.Route r where r.code = ?", code);
        if (l == null || l.isEmpty()) return null;
        return (Route) l.get(0);
    }
}
