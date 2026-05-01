package com.acme.cargotrak.dao;

import java.util.List;

public interface PaymentDao extends BaseDao {
    List findByCustomer(Integer customerId);
}
