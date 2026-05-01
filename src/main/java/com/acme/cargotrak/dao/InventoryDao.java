package com.acme.cargotrak.dao;

import java.util.List;

public interface InventoryDao extends BaseDao {
    List findByZone(Integer zoneId);
    List searchBySku(String fragment);
}
