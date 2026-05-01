package com.acme.cargotrak.dao.hibernate;

import java.util.List;

import com.acme.cargotrak.dao.CustomerContactDao;

public class CustomerContactDaoImpl extends AbstractHibernateDao implements CustomerContactDao {

    public List findByCustomer(Integer customerId) {
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.CustomerContact c where c.customer.customerId = ?", customerId);
    }
}
