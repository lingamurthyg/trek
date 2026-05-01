package com.acme.cargotrak.dao;

import java.util.List;

import com.acme.cargotrak.domain.RateCard;

public interface RateCardDao extends BaseDao {

    List findActive();

    RateCard findForCustomer(Integer customerId);
}
