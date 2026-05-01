package com.acme.cargotrak.dao;

import java.util.List;

import com.acme.cargotrak.domain.Customer;

public interface CustomerDao extends BaseDao {

    Customer findByCode(String code);

    List search(String namePart, String industry, String activeFlag, int firstResult, int maxResults);

    int countSearch(String namePart, String industry, String activeFlag);

    List findActive();
}
