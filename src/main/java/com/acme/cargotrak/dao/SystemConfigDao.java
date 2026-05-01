package com.acme.cargotrak.dao;

public interface SystemConfigDao extends BaseDao {
    String getValue(String key);
    void putValue(String key, String value);
}
