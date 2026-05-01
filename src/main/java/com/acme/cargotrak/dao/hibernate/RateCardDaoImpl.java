package com.acme.cargotrak.dao.hibernate;

import java.util.List;

import com.acme.cargotrak.dao.RateCardDao;
import com.acme.cargotrak.domain.RateCard;

public class RateCardDaoImpl extends AbstractHibernateDao implements RateCardDao {

    public List findActive() {
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.RateCard r where r.activeFlag = 'Y' order by r.name");
    }

    public RateCard findForCustomer(Integer customerId) {
        List l = getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.RateCard r where r.customerId = ? and r.activeFlag = 'Y'", customerId);
        if (l != null && !l.isEmpty()) return (RateCard) l.get(0);
        // fall back to default
        l = getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.RateCard r where r.customerId is null and r.activeFlag = 'Y'");
        if (l != null && !l.isEmpty()) return (RateCard) l.get(0);
        return null;
    }
}
