package com.acme.cargotrak.dao.hibernate;

import java.util.List;

import com.acme.cargotrak.dao.CustomerAddressDao;

public class CustomerAddressDaoImpl extends AbstractHibernateDao implements CustomerAddressDao {

    public List findByCustomer(Integer customerId) {
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.CustomerAddress a where a.customer.customerId = ?", customerId);
    }
}
