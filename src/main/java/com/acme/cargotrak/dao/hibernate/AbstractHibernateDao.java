package com.acme.cargotrak.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.acme.cargotrak.dao.BaseDao;

/**
 * Base class for Hibernate DAOs.
 *
 * @author Rajesh Kumar
 * @author M. Iyer added countAll() in 2010
 */
public abstract class AbstractHibernateDao extends HibernateDaoSupport implements BaseDao {

    public Object findById(Class clazz, Serializable id) {
        return getHibernateTemplate().get(clazz, id);
    }

    public List findAll(Class clazz) {
        return getHibernateTemplate().find("from " + clazz.getName());
    }

    public Serializable save(Object entity) {
        return getHibernateTemplate().save(entity);
    }

    public void update(Object entity) {
        getHibernateTemplate().update(entity);
    }

    public void saveOrUpdate(Object entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    public void delete(Object entity) {
        getHibernateTemplate().delete(entity);
    }

    public int countAll(Class clazz) {
        List l = getHibernateTemplate().find("select count(*) from " + clazz.getName());
        if (l == null || l.isEmpty()) return 0;
        Object o = l.get(0);
        if (o instanceof Number) return ((Number) o).intValue();
        return 0;
    }

    protected Session currentSession() {
        SessionFactory sf = getSessionFactory();
        return sf.getCurrentSession();
    }

    protected Query createQuery(String hql) {
        return currentSession().createQuery(hql);
    }
}
