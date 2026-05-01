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

import com.acme.cargotrak.dao.ShipmentDao;
import com.acme.cargotrak.domain.Shipment;

/**
 * @author Rajesh Kumar 2008-12
 * @author M. Iyer (offshore) added paged search 2010-04
 * @author L. Chen 2012-09 - "had to drop to raw JDBC for the audit insert,
 *         hibernate session was getting flushed too eagerly"
 *
 * Migrated:
 *  - com.mysql.jdbc.Driver -> com.mysql.cj.jdbc.Driver (MySQL Connector/J 8.x)
 *  - org.hibernate.Query   -> org.hibernate.query.Query (Hibernate 5+)
 *  - HibernateCallback     -> org.springframework.orm.hibernate5.HibernateCallback
 *  - new Integer(x)        -> Integer.valueOf(x) (deprecated primitive wrapper constructor)
 */
@SuppressWarnings({"rawtypes", "unchecked", "deprecation"})
public class ShipmentDaoImpl extends AbstractHibernateDao implements ShipmentDao {

    private static final Logger logger = Logger.getLogger(ShipmentDaoImpl.class);

    static {
        try {
            // MySQL Connector/J 8.x: driver class is com.mysql.cj.jdbc.Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // ignore -- Tomcat lib already has it
            e.printStackTrace();
        }
    }

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Shipment findByTrackingNo(String trackingNo) {
        List l = getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.Shipment s where s.trackingNo = ?0", trackingNo);
        if (l == null || l.isEmpty()) return null;
        return (Shipment) l.get(0);
    }

    public List search(final String trackingFragment, final Integer customerId, final String status,
                       final Date fromDate, final Date toDate,
                       final int firstResult, final int maxResults) {
        // Build the HQL by string concatenation. ops asked for this in 2009. KP.
        final StringBuffer hql = new StringBuffer("from com.acme.cargotrak.domain.Shipment s where 1=1 ");
        final List args = new ArrayList();
        if (trackingFragment != null && trackingFragment.length() > 0) {
            // FIXME: parameterize this -- KP 2011
            hql.append(" and s.trackingNo like '%").append(trackingFragment).append("%' ");
        }
        if (customerId != null) {
            hql.append(" and s.customerId = ?").append(args.size()).append(" ");
            args.add(customerId);
        }
        if (status != null && status.length() > 0) {
            hql.append(" and s.status = ?").append(args.size()).append(" ");
            args.add(status);
        }
        if (fromDate != null) {
            hql.append(" and s.bookedDate >= ?").append(args.size()).append(" ");
            args.add(fromDate);
        }
        if (toDate != null) {
            hql.append(" and s.bookedDate <= ?").append(args.size()).append(" ");
            args.add(toDate);
        }
        hql.append(" order by s.bookedDate desc");

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

    public int countSearch(String trackingFragment, Integer customerId, String status, Date fromDate, Date toDate) {
        // raw JDBC for the count, faster than HQL on this table apparently
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            StringBuffer sql = new StringBuffer("select count(*) from shipments where 1=1 ");
            if (trackingFragment != null && trackingFragment.length() > 0) {
                sql.append(" and tracking_no like '%").append(trackingFragment).append("%' ");
            }
            if (customerId != null) {
                sql.append(" and customer_id = ").append(customerId.intValue()).append(" ");
            }
            if (status != null && status.length() > 0) {
                sql.append(" and status = '").append(status).append("' ");
            }
            if (fromDate != null) {
                sql.append(" and booked_date >= '").append(new Timestamp(fromDate.getTime())).append("' ");
            }
            if (toDate != null) {
                sql.append(" and booked_date <= '").append(new Timestamp(toDate.getTime())).append("' ");
            }
            st = con.createStatement();
            rs = st.executeQuery(sql.toString());
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            logger.error("countSearch failed", e);
            return 0;
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ex) {}
            try { if (st != null) st.close(); } catch (Exception ex) {}
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
    }

    public List findByCustomer(Integer customerId) {
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.Shipment s where s.customerId = ?0 order by s.bookedDate desc",
            customerId);
    }

    public List findByStatus(String status) {
        return getHibernateTemplate().find(
            "from com.acme.cargotrak.domain.Shipment s where s.status = ?0 order by s.bookedDate desc",
            status);
    }

    public List findUninvoicedDelivered() {
        // raw JDBC because the join was cleaner this way
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        List ids = new ArrayList();
        try {
            con = dataSource.getConnection();
            st = con.createStatement();
            String sql = "select s.shipment_id from shipments s "
                       + "where s.status = 'DELIVERED' "
                       + "and not exists (select 1 from invoice_line_items li where li.shipment_id = s.shipment_id)";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                ids.add(Integer.valueOf(rs.getInt(1)));
            }
        } catch (SQLException e) {
            logger.error("findUninvoicedDelivered failed", e);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ex) {}
            try { if (st != null) st.close(); } catch (Exception ex) {}
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
        if (ids.isEmpty()) return new ArrayList();
        return getHibernateTemplate().findByNamedParam(
            "from com.acme.cargotrak.domain.Shipment s where s.shipmentId in (:ids)",
            "ids", ids);
    }

    public int updateStatus(final Integer shipmentId, final String newStatus, final String changedBy) {
        // mixed: hibernate update + raw jdbc audit insert
        Integer updated = (Integer) getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session session) throws HibernateException {
                Query q = session.createQuery(
                    "update com.acme.cargotrak.domain.Shipment s set s.status = :ns where s.shipmentId = :id");
                q.setParameter("ns", newStatus);
                q.setParameter("id", shipmentId);
                return Integer.valueOf(q.executeUpdate());
            }
        });
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(
                "insert into shipment_status_history (shipment_id, old_status, new_status, changed_by, changed_at, notes) "
                + "values (?,?,?,?,?,?)");
            ps.setInt(1, shipmentId.intValue());
            ps.setString(2, null);
            ps.setString(3, newStatus);
            ps.setString(4, changedBy);
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            ps.setString(6, "status updated via dao");
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("audit insert failed", e);
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception ex) {}
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
        return updated == null ? 0 : updated.intValue();
    }
}
