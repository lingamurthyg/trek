package com.acme.cargotrak.dao;

import java.util.List;

public interface PaymentAllocationDao extends BaseDao {
    List findByPayment(Integer paymentId);
    List findByInvoice(Integer invoiceId);
}
