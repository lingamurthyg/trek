package com.acme.cargotrak.dao;

import java.util.List;

import com.acme.cargotrak.domain.User;

public interface UserDao extends BaseDao {

    User findByUsername(String username);

    List searchByName(String fragment);

    List findActive();

    void recordLastLogin(Integer userId);
}
