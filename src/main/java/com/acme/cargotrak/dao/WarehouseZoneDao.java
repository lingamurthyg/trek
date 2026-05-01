package com.acme.cargotrak.dao;

import java.util.List;

public interface WarehouseZoneDao extends BaseDao {
    List findByWarehouse(Integer warehouseId);
}
