package com.acme.cargotrak.service;

import java.util.List;

import com.acme.cargotrak.dao.SystemConfigDao;
import com.acme.cargotrak.domain.SystemConfig;

public class SystemConfigService {

    private SystemConfigDao systemConfigDao;
    public void setSystemConfigDao(SystemConfigDao d) { this.systemConfigDao = d; }

    public String get(String key) { return systemConfigDao.getValue(key); }
    public String getOrDefault(String key, String def) {
        String v = systemConfigDao.getValue(key);
        return v == null ? def : v;
    }
    public void put(String key, String value) { systemConfigDao.putValue(key, value); }
    public List listAll() { return systemConfigDao.findAll(SystemConfig.class); }
}
