package com.acme.cargotrak.dao;

import com.acme.cargotrak.domain.Role;

public interface RoleDao extends BaseDao {
    Role findByName(String name);
}
