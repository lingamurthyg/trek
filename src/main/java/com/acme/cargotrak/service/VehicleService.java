package com.acme.cargotrak.service;

import java.util.List;

import com.acme.cargotrak.dao.VehicleDao;
import com.acme.cargotrak.dao.VehicleMaintenanceDao;
import com.acme.cargotrak.domain.Vehicle;
import com.acme.cargotrak.domain.VehicleMaintenance;

public class VehicleService {

    private VehicleDao vehicleDao;
    private VehicleMaintenanceDao maintenanceDao;

    public void setVehicleDao(VehicleDao v) { this.vehicleDao = v; }
    public void setMaintenanceDao(VehicleMaintenanceDao m) { this.maintenanceDao = m; }

    public List listAll() { return vehicleDao.findAll(Vehicle.class); }
    public List listAvailable() { return vehicleDao.findAvailable(); }
    public Vehicle findById(Integer id) { return (Vehicle) vehicleDao.findById(Vehicle.class, id); }
    public Integer create(Vehicle v) {
        if (v.getStatus() == null) v.setStatus("AVAILABLE");
        return (Integer) vehicleDao.save(v);
    }
    public void update(Vehicle v) { vehicleDao.update(v); }
    public List listMaintenance(Integer vehicleId) { return maintenanceDao.findByVehicle(vehicleId); }
    public Integer addMaintenance(Integer vehicleId, VehicleMaintenance m) {
        Vehicle v = findById(vehicleId);
        if (v == null) return null;
        m.setVehicle(v);
        return (Integer) maintenanceDao.save(m);
    }
    public void setStatus(Integer vehicleId, String status) {
        Vehicle v = findById(vehicleId);
        if (v == null) return;
        v.setStatus(status);
        vehicleDao.update(v);
    }
}
