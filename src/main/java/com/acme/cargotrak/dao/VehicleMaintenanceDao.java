package com.acme.cargotrak.dao;

import java.util.List;

public interface VehicleMaintenanceDao extends BaseDao {
    List findByVehicle(Integer vehicleId);
}
