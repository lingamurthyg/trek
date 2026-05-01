package com.acme.cargotrak.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao {

    Object findById(Class clazz, Serializable id);

    List findAll(Class clazz);

    Serializable save(Object entity);

    void update(Object entity);

    void saveOrUpdate(Object entity);

    void delete(Object entity);

    int countAll(Class clazz);
}
