package com.acme.cargotrak.dao;

import java.util.List;

import com.acme.cargotrak.domain.Driver;

public interface DriverDao extends BaseDao {

    Driver findByEmployeeCode(String code);

    List findActive();

    List findExpiringWithin(int days);
}
