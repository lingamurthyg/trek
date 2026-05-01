package com.acme.cargotrak.service;

import java.util.List;

import com.acme.cargotrak.dao.RateCardDao;
import com.acme.cargotrak.domain.RateCard;

public class RateCardService {

    private RateCardDao rateCardDao;
    public void setRateCardDao(RateCardDao r) { this.rateCardDao = r; }

    public List listAll() { return rateCardDao.findAll(RateCard.class); }
    public List listActive() { return rateCardDao.findActive(); }
    public RateCard findById(Integer id) { return (RateCard) rateCardDao.findById(RateCard.class, id); }
    public RateCard findForCustomer(Integer customerId) { return rateCardDao.findForCustomer(customerId); }
    public Integer create(RateCard r) { return (Integer) rateCardDao.save(r); }
    public void update(RateCard r) { rateCardDao.update(r); }
}
