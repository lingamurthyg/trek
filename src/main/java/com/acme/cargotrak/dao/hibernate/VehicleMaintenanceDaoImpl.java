package com.acme.cargotrak.dao.hibernate;

import java.util.List;

import com.acme.cargotrak.dao.VehicleMaintenanceDao;

public class VehicleMaintenanceDaoImpl extends AbstractHibernateDao implements VehicleMaintenanceDao {

    public List findByVehicle(Integer vehicleId) {
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.VehicleMaintenance m where m.vehicle.vehicleId = ? order by m.maintDate desc",
            vehicleId);
    }
}
