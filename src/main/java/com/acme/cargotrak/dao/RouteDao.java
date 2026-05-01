package com.acme.cargotrak.dao;

import com.acme.cargotrak.domain.Route;

public interface RouteDao extends BaseDao {
    Route findByCode(String code);
}
