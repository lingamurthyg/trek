package com.acme.cargotrak.dao;

import java.util.List;

public interface ShipmentStatusHistoryDao extends BaseDao {
    List findByShipment(Integer shipmentId);
}
