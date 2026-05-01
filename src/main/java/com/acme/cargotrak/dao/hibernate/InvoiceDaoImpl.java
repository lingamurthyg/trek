package com.acme.cargotrak.dao.hibernate;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;

import com.acme.cargotrak.dao.InvoiceDao;
import com.acme.cargotrak.domain.Invoice;

/**
 * @author M. Iyer 2010-08
 *
 * NOTE: this class is mostly copy-pasted from ShipmentDaoImpl. Refactor target. miyer 2011.
 *
 * Migrated for Java 21 / Hibernate 5.6:
 *  - com.mysql.jdbc.Driver -> com.mysql.cj.jdbc.Driver (MySQL Connector/J 8.x)
 *  - org.hibernate.Query   -> org.hibernate.query.Query
 *  - HibernateCallback     -> org.springframework.orm.hibernate5.HibernateCallback
 */
@SuppressWarnings({"rawtypes", "unchecked", "deprecation"})
public class InvoiceDaoImpl extends AbstractHibernateDao implements InvoiceDao {

    private static final Logger logger = Logger.getLogger(InvoiceDaoImpl.class);

    static {
        try {
            // MySQL Connector/J 8.x: driver class changed from com.mysql.jdbc.Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Invoice findByInvoiceNo(String invoiceNo) {
        List l = getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.Invoice i where i.invoiceNo = ?0", invoiceNo);
        if (l == null || l.isEmpty()) return null;
        return (Invoice) l.get(0);
    }

    public List search(final String invoiceFragment, final Integer customerId, final String status,
                       final Date fromDate, final Date toDate,
                       final int firstResult, final int maxResults) {
        final StringBuffer hql = new StringBuffer("from com.acme.cargotrak.domain.Invoice i where 1=1 ");
        final List args = new ArrayList();
        if (invoiceFragment != null && invoiceFragment.length() > 0) {
            hql.append(" and i.invoiceNo like '%").append(invoiceFragment).append("%' ");
        }
        if (customerId != null) {
            hql.append(" and i.customerId = ?").append(args.size()).append(" ");
            args.add(customerId);
        }
        if (status != null && status.length() > 0) {
            hql.append(" and i.status = ?").append(args.size()).append(" ");
            args.add(status);
        }
        if (fromDate != null) {
            hql.append(" and i.invoiceDate >= ?").append(args.size()).append(" ");
            args.add(fromDate);
        }
        if (toDate != null) {
            hql.append(" and i.invoiceDate <= ?").append(args.size()).append(" ");
            args.add(toDate);
        }
        hql.append(" order by i.invoiceDate desc");

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

    public int countSearch(String invoiceFragment, Integer customerId, String status, Date fromDate, Date toDate) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            StringBuffer sql = new StringBuffer("select count(*) from invoices where 1=1 ");
            if (invoiceFragment != null && invoiceFragment.length() > 0) {
                sql.append(" and invoice_no like '%").append(invoiceFragment).append("%' ");
            }
            if (customerId != null) {
                sql.append(" and customer_id = ").append(customerId.intValue()).append(" ");
            }
            if (status != null && status.length() > 0) {
                sql.append(" and status = '").append(status).append("' ");
            }
            if (fromDate != null) {
                sql.append(" and invoice_date >= '").append(new Timestamp(fromDate.getTime())).append("' ");
            }
            if (toDate != null) {
                sql.append(" and invoice_date <= '").append(new Timestamp(toDate.getTime())).append("' ");
            }
            st = con.createStatement();
            rs = st.executeQuery(sql.toString());
            if (rs.next()) return rs.getInt(1);
            return 0;
        } catch (SQLException e) {
            logger.error("error");
            return 0;
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ex) {}
            try { if (st != null) st.close(); } catch (Exception ex) {}
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
    }

    public List findOpenForCustomer(Integer customerId) {
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.Invoice i where i.customerId = ?0 and i.status <> 'PAID' order by i.dueDate",
            customerId);
    }

    public List findOverdueAsOf(Date asOf) {
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.Invoice i where i.dueDate < ?0 and i.status <> 'PAID' order by i.dueDate",
            asOf);
    }

    public void applyPayment(final Integer invoiceId, final BigDecimal amount) {
        // raw jdbc, single statement so we don't need to worry about Hibernate stale state
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(
                "update invoices set amount_paid = amount_paid + ?, "
              + " status = case when amount_paid + ? >= total then 'PAID' else status end "
              + " where invoice_id = ?");
            ps.setBigDecimal(1, amount);
            ps.setBigDecimal(2, amount);
            ps.setInt(3, invoiceId.intValue());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception ex) {}
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
    }
}
