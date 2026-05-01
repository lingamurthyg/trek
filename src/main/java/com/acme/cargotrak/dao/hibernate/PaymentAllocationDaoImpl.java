package com.acme.cargotrak.dao.hibernate;

import java.util.List;

import com.acme.cargotrak.dao.PaymentAllocationDao;

public class PaymentAllocationDaoImpl extends AbstractHibernateDao implements PaymentAllocationDao {

    public List findByPayment(Integer paymentId) {
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.PaymentAllocation a where a.paymentId = ?", paymentId);
    }

    public List findByInvoice(Integer invoiceId) {
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.PaymentAllocation a where a.invoiceId = ?", invoiceId);
    }
}
