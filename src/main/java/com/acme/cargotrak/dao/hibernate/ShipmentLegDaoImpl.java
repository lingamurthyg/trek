package com.acme.cargotrak.dao.hibernate;

import java.util.List;

import com.acme.cargotrak.dao.ShipmentLegDao;

public class ShipmentLegDaoImpl extends AbstractHibernateDao implements ShipmentLegDao {

    public List findByShipment(Integer shipmentId) {
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.ShipmentLeg l where l.shipment.shipmentId = ? order by l.seqNo",
            shipmentId);
    }
}
