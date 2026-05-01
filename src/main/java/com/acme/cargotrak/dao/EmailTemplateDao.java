package com.acme.cargotrak.dao;

import com.acme.cargotrak.domain.EmailTemplate;

public interface EmailTemplateDao extends BaseDao {
    EmailTemplate findByCode(String code);
}
