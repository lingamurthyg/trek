package com.acme.cargotrak.dao;

import java.util.List;

public interface CustomerAddressDao extends BaseDao {
    List findByCustomer(Integer customerId);
}
