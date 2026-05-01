package com.acme.cargotrak.dao.hibernate;

import java.util.List;

import com.acme.cargotrak.dao.WarehouseZoneDao;

public class WarehouseZoneDaoImpl extends AbstractHibernateDao implements WarehouseZoneDao {

    public List findByWarehouse(Integer warehouseId) {
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.WarehouseZone z where z.warehouse.warehouseId = ? order by z.code",
            warehouseId);
    }
}
