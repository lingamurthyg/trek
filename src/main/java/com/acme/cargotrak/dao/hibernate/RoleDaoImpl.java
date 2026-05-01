package com.acme.cargotrak.dao.hibernate;

import java.util.List;

import com.acme.cargotrak.dao.RoleDao;
import com.acme.cargotrak.domain.Role;

public class RoleDaoImpl extends AbstractHibernateDao implements RoleDao {

    public Role findByName(String name) {
        List l = getHibernateTemplate().find(
                "from com.acme.cargotrak.domain.Role r where r.roleName = ?", name);
        if (l == null || l.isEmpty()) return null;
        return (Role) l.get(0);
    }
}
