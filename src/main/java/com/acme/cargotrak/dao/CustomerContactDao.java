package com.acme.cargotrak.dao;

import java.util.List;

public interface CustomerContactDao extends BaseDao {
    List findByCustomer(Integer customerId);
}
