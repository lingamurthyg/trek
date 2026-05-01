package com.acme.cargotrak.dao.hibernate;

import java.util.List;

import com.acme.cargotrak.dao.VehicleDao;
import com.acme.cargotrak.domain.Vehicle;

public class VehicleDaoImpl extends AbstractHibernateDao implements VehicleDao {

    public Vehicle findByPlate(String plate) {
        List l = getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.Vehicle v where v.plateNumber = ?", plate);
        if (l == null || l.isEmpty()) return null;
        return (Vehicle) l.get(0);
    }

    public List findAvailable() {
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.Vehicle v where v.status = 'AVAILABLE' order by v.plateNumber");
    }

    public List findByStatus(String status) {
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.Vehicle v where v.status = ? order by v.plateNumber", status);
    }
}
