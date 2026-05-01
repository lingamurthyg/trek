package com.acme.cargotrak.dao.hibernate;

import java.util.List;

import com.acme.cargotrak.dao.WarehouseDao;
import com.acme.cargotrak.domain.Warehouse;

public class WarehouseDaoImpl extends AbstractHibernateDao implements WarehouseDao {

    public Warehouse findByCode(String code) {
        List l = getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.Warehouse w where w.code = ?", code);
        if (l == null || l.isEmpty()) return null;
        return (Warehouse) l.get(0);
    }
}
