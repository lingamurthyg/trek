package com.acme.cargotrak.dao;

import java.util.List;

public interface ShipmentDocumentDao extends BaseDao {
    List findByShipment(Integer shipmentId);
}
