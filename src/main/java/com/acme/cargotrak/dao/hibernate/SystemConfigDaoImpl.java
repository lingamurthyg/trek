package com.acme.cargotrak.dao.hibernate;

import java.util.Date;

import com.acme.cargotrak.dao.SystemConfigDao;
import com.acme.cargotrak.domain.SystemConfig;

public class SystemConfigDaoImpl extends AbstractHibernateDao implements SystemConfigDao {

    public String getValue(String key) {
        SystemConfig sc = (SystemConfig) getHibernateTemplate().get(SystemConfig.class, key);
        return sc == null ? null : sc.getConfigValue();
    }

    public void putValue(String key, String value) {
        SystemConfig sc = (SystemConfig) getHibernateTemplate().get(SystemConfig.class, key);
        if (sc == null) {
            sc = new SystemConfig();
            sc.setConfigKey(key);
        }
        sc.setConfigValue(value);
        sc.setUpdatedAt(new Date());
        getHibernateTemplate().saveOrUpdate(sc);
    }
}
