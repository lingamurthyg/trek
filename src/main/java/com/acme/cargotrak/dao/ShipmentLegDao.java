package com.acme.cargotrak.dao;

import java.util.List;

public interface ShipmentLegDao extends BaseDao {
    List findByShipment(Integer shipmentId);
}
