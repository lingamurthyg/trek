package com.acme.cargotrak.dao.hibernate;

import java.util.List;

import com.acme.cargotrak.dao.ShipmentDocumentDao;

public class ShipmentDocumentDaoImpl extends AbstractHibernateDao implements ShipmentDocumentDao {

    public List findByShipment(Integer shipmentId) {
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.ShipmentDocument d where d.shipmentId = ? order by d.uploadedAt desc",
            shipmentId);
    }
}
