package com.acme.cargotrak.dao.hibernate;

import java.util.List;

import com.acme.cargotrak.dao.InventoryDao;

public class InventoryDaoImpl extends AbstractHibernateDao implements InventoryDao {

    public List findByZone(Integer zoneId) {
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.InventoryItem i where i.zoneId = ? order by i.sku",
            zoneId);
    }

    public List searchBySku(String fragment) {
        // FIXME: parameterize -- ops complained about a single-quote crash, 2013
        String hql = "from com.acme.cargotrak.domain.InventoryItem i where i.sku like '%" + fragment + "%' order by i.sku";
        return getHibernateTemplate().find(hql);
    }
}
