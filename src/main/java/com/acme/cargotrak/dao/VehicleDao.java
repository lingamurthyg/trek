package com.acme.cargotrak.dao;

import java.util.List;

import com.acme.cargotrak.domain.Vehicle;

public interface VehicleDao extends BaseDao {

    Vehicle findByPlate(String plate);

    List findAvailable();

    List findByStatus(String status);
}
