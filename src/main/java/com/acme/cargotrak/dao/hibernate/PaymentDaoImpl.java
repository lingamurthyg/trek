package com.acme.cargotrak.dao.hibernate;

import java.util.List;

import com.acme.cargotrak.dao.PaymentDao;

public class PaymentDaoImpl extends AbstractHibernateDao implements PaymentDao {

    public List findByCustomer(Integer customerId) {
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.Payment p where p.customerId = ? order by p.paymentDate desc",
            customerId);
    }
}
