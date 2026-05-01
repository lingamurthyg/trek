package com.acme.cargotrak.dao.hibernate;

import java.util.List;

import com.acme.cargotrak.dao.ShipmentStatusHistoryDao;

public class ShipmentStatusHistoryDaoImpl extends AbstractHibernateDao implements ShipmentStatusHistoryDao {

    public List findByShipment(Integer shipmentId) {
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.ShipmentStatusHistory h where h.shipmentId = ? order by h.changedAt desc",
            shipmentId);
    }
}
