package com.acme.cargotrak.dao.hibernate;

import java.util.List;

import com.acme.cargotrak.dao.EmailTemplateDao;
import com.acme.cargotrak.domain.EmailTemplate;

public class EmailTemplateDaoImpl extends AbstractHibernateDao implements EmailTemplateDao {

    public EmailTemplate findByCode(String code) {
        List l = getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.EmailTemplate t where t.templateCode = ?", code);
        if (l == null || l.isEmpty()) return null;
        return (EmailTemplate) l.get(0);
    }
}
