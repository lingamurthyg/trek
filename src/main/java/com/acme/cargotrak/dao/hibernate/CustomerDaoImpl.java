package com.acme.cargotrak.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;

import com.acme.cargotrak.dao.CustomerDao;
import com.acme.cargotrak.domain.Customer;

/**
 * @author Rajesh Kumar 2008-12
 * @author L. Chen 2012-09 added paged search
 *
 * Migrated for Java 21 / Hibernate 5.6:
 *  - org.hibernate.Query -> org.hibernate.query.Query
 *  - HibernateCallback   -> org.springframework.orm.hibernate5.HibernateCallback
 */
@SuppressWarnings({"rawtypes", "unchecked", "deprecation"})
public class CustomerDaoImpl extends AbstractHibernateDao implements CustomerDao {

    public Customer findByCode(String code) {
        List l = getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.Customer c where c.customerCode = ?0", code);
        if (l == null || l.isEmpty()) return null;
        return (Customer) l.get(0);
    }

    public List search(final String namePart, final String industry, final String activeFlag,
                       final int firstResult, final int maxResults) {
        // build HQL by string concatenation. yes, we know.
        final StringBuffer hql = new StringBuffer("from com.acme.cargotrak.domain.Customer c where 1=1 ");
        final List args = new ArrayList();
        if (namePart != null && namePart.length() > 0) {
            hql.append(" and c.name like ?").append(args.size()).append(" ");
            args.add("%" + namePart + "%");
        }
        if (industry != null && industry.length() > 0) {
            hql.append(" and c.industry = ?").append(args.size()).append(" ");
            args.add(industry);
        }
        if (activeFlag != null && activeFlag.length() > 0) {
            hql.append(" and c.activeFlag = ?").append(args.size()).append(" ");
            args.add(activeFlag);
        }
        hql.append(" order by c.name");

        return (List) getHibernateTemplate().execute(new HibernateCallback<List>() {
            public List doInHibernate(Session session) throws HibernateException {
                Query q = session.createQuery(hql.toString());
                for (int i = 0; i < args.size(); i++) {
                    q.setParameter(i, args.get(i));
                }
                q.setFirstResult(firstResult);
                q.setMaxResults(maxResults);
                return q.list();
            }
        });
    }

    public int countSearch(final String namePart, final String industry, final String activeFlag) {
        final StringBuffer hql = new StringBuffer("select count(*) from com.acme.cargotrak.domain.Customer c where 1=1 ");
        final List args = new ArrayList();
        if (namePart != null && namePart.length() > 0) {
            hql.append(" and c.name like ?").append(args.size()).append(" ");
            args.add("%" + namePart + "%");
        }
        if (industry != null && industry.length() > 0) {
            hql.append(" and c.industry = ?").append(args.size()).append(" ");
            args.add(industry);
        }
        if (activeFlag != null && activeFlag.length() > 0) {
            hql.append(" and c.activeFlag = ?").append(args.size()).append(" ");
            args.add(activeFlag);
        }
        Long n = (Long) getHibernateTemplate().execute(new HibernateCallback<Long>() {
            public Long doInHibernate(Session session) throws HibernateException {
                Query q = session.createQuery(hql.toString());
                for (int i = 0; i < args.size(); i++) {
                    q.setParameter(i, args.get(i));
                }
                return (Long) q.uniqueResult();
            }
        });
        return n == null ? 0 : n.intValue();
    }

    public List findActive() {
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.Customer c where c.activeFlag = 'Y' order by c.name");
    }
}
