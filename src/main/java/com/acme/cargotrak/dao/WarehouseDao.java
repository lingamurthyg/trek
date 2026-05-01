package com.acme.cargotrak.dao;

import com.acme.cargotrak.domain.Warehouse;

public interface WarehouseDao extends BaseDao {
    Warehouse findByCode(String code);
}
