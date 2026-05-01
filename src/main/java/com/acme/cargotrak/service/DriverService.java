package com.acme.cargotrak.service;

import java.util.List;

import com.acme.cargotrak.dao.DriverDao;
import com.acme.cargotrak.domain.Driver;

public class DriverService {

    private DriverDao driverDao;

    public void setDriverDao(DriverDao d) { this.driverDao = d; }

    public List listAll() { return driverDao.findAll(Driver.class); }
    public List listActive() { return driverDao.findActive(); }
    public Driver findById(Integer id) { return (Driver) driverDao.findById(Driver.class, id); }
    public Driver findByEmployeeCode(String code) { return driverDao.findByEmployeeCode(code); }
    public Integer create(Driver d) {
        if (d.getActiveFlag() == null) d.setActiveFlag("Y");
        return (Integer) driverDao.save(d);
    }
    public void update(Driver d) { driverDao.update(d); }
    public void deactivate(Integer id) {
        Driver d = findById(id);
        if (d == null) return;
        d.setActiveFlag("N");
        driverDao.update(d);
    }
    public List findExpiringWithin(int days) { return driverDao.findExpiringWithin(days); }
}
