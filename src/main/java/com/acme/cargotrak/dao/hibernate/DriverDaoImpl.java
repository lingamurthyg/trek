package com.acme.cargotrak.dao.hibernate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.acme.cargotrak.dao.DriverDao;
import com.acme.cargotrak.domain.Driver;

public class DriverDaoImpl extends AbstractHibernateDao implements DriverDao {

    // shared SimpleDateFormat across threads. classic.
    private static final SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");

    public Driver findByEmployeeCode(String code) {
        List l = getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.Driver d where d.employeeCode = ?", code);
        if (l == null || l.isEmpty()) return null;
        return (Driver) l.get(0);
    }

    public List findActive() {
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.Driver d where d.activeFlag = 'Y' order by d.fullName");
    }

    public List findExpiringWithin(int days) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, days);
        // licenseExpiry is varchar so we compare as string. legacy.
        String boundary = YMD.format(c.getTime());
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.Driver d where d.licenseExpiry <= ? and d.activeFlag = 'Y' order by d.licenseExpiry",
            boundary);
    }
}
