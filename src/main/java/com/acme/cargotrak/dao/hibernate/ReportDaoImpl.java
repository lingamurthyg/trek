package com.acme.cargotrak.dao.hibernate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.acme.cargotrak.dao.ReportDao;

/**
 * Reporting DAO. Mostly raw JDBC + stored procs.
 *
 * @author L. Chen 2012-09 -- "hibernate over stored procs is a recipe for tears"
 */
public class ReportDaoImpl implements ReportDao {

    private static final Logger logger = Logger.getLogger(ReportDaoImpl.class);

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List revenueByCustomer(Date from, Date to) {
        List rows = new ArrayList();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            cs = con.prepareCall("{call sp_revenue_by_customer(?, ?)}");
            cs.setTimestamp(1, new Timestamp(from.getTime()));
            cs.setTimestamp(2, new Timestamp(to.getTime()));
            rs = cs.executeQuery();
            while (rs.next()) {
                Map row = new LinkedHashMap();
                row.put("customerId",   rs.getObject("customer_id"));
                row.put("customerCode", rs.getString("customer_code"));
                row.put("customerName", rs.getString("customer_name"));
                row.put("invoiceCount", rs.getObject("invoice_count"));
                row.put("grossRevenue", rs.getBigDecimal("gross_revenue"));
                row.put("amountPaid",   rs.getBigDecimal("amount_paid"));
                row.put("outstanding",  rs.getBigDecimal("outstanding"));
                rows.add(row);
            }
        } catch (SQLException e) {
            logger.error("revenueByCustomer failed", e);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ex) {}
            try { if (cs != null) cs.close(); } catch (Exception ex) {}
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
        return rows;
    }

    public List fleetUtilization(Date from, Date to) {
        List rows = new ArrayList();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            cs = con.prepareCall("{call sp_fleet_utilization(?, ?)}");
            cs.setTimestamp(1, new Timestamp(from.getTime()));
            cs.setTimestamp(2, new Timestamp(to.getTime()));
            rs = cs.executeQuery();
            while (rs.next()) {
                Map row = new LinkedHashMap();
                row.put("vehicleId",     rs.getObject("vehicle_id"));
                row.put("plateNumber",   rs.getString("plate_number"));
                row.put("vehicleType",   rs.getString("vehicle_type"));
                row.put("status",        rs.getString("status"));
                row.put("shipmentCount", rs.getObject("shipment_count"));
                row.put("totalWeight",   rs.getBigDecimal("total_weight"));
                row.put("totalRevenue",  rs.getBigDecimal("total_revenue"));
                rows.add(row);
            }
        } catch (SQLException e) {
            logger.error("fleetUtilization failed", e);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ex) {}
            try { if (cs != null) cs.close(); } catch (Exception ex) {}
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
        return rows;
    }

    public List onTimeDeliveryStats(Date from, Date to) {
        List rows = new ArrayList();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(
                "select c.customer_code, c.name, "
              + "  count(s.shipment_id) as total, "
              + "  sum(case when s.delivery_date is not null and s.delivery_date <= date_add(s.booked_date, interval 5 day) then 1 else 0 end) as on_time "
              + " from shipments s join customers c on c.customer_id = s.customer_id "
              + " where s.booked_date between ? and ? "
              + " and s.status in ('DELIVERED','INVOICED','CLOSED') "
              + " group by c.customer_code, c.name order by c.customer_code");
            ps.setTimestamp(1, new Timestamp(from.getTime()));
            ps.setTimestamp(2, new Timestamp(to.getTime()));
            rs = ps.executeQuery();
            while (rs.next()) {
                Map row = new LinkedHashMap();
                row.put("customerCode", rs.getString("customer_code"));
                row.put("customerName", rs.getString("name"));
                row.put("total",        new Integer(rs.getInt("total")));
                row.put("onTime",       new Integer(rs.getInt("on_time")));
                int total = rs.getInt("total");
                int onTime = rs.getInt("on_time");
                double pct = total == 0 ? 0.0 : (100.0 * onTime / total);
                row.put("pct", new Double(pct));
                rows.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ex) {}
            try { if (ps != null) ps.close(); } catch (Exception ex) {}
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
        return rows;
    }

    public List arAging(Date asOf) {
        List rows = new ArrayList();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(
                "select c.customer_code, c.name, "
              + "  sum(case when datediff(?, i.due_date) <= 30 then i.total - i.amount_paid else 0 end) as bucket_0_30, "
              + "  sum(case when datediff(?, i.due_date) between 31 and 60 then i.total - i.amount_paid else 0 end) as bucket_31_60, "
              + "  sum(case when datediff(?, i.due_date) between 61 and 90 then i.total - i.amount_paid else 0 end) as bucket_61_90, "
              + "  sum(case when datediff(?, i.due_date) > 90 then i.total - i.amount_paid else 0 end) as bucket_90_plus, "
              + "  sum(i.total - i.amount_paid) as total_outstanding "
              + " from invoices i join customers c on c.customer_id = i.customer_id "
              + " where i.status <> 'PAID' "
              + " group by c.customer_code, c.name order by total_outstanding desc");
            Timestamp t = new Timestamp(asOf.getTime());
            ps.setTimestamp(1, t);
            ps.setTimestamp(2, t);
            ps.setTimestamp(3, t);
            ps.setTimestamp(4, t);
            rs = ps.executeQuery();
            while (rs.next()) {
                Map row = new LinkedHashMap();
                row.put("customerCode",  rs.getString("customer_code"));
                row.put("customerName",  rs.getString("name"));
                row.put("bucket0_30",    rs.getBigDecimal("bucket_0_30"));
                row.put("bucket31_60",   rs.getBigDecimal("bucket_31_60"));
                row.put("bucket61_90",   rs.getBigDecimal("bucket_61_90"));
                row.put("bucket90Plus",  rs.getBigDecimal("bucket_90_plus"));
                row.put("totalOutstanding", rs.getBigDecimal("total_outstanding"));
                rows.add(row);
            }
        } catch (SQLException e) {
            logger.error("arAging failed", e);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ex) {}
            try { if (ps != null) ps.close(); } catch (Exception ex) {}
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
        return rows;
    }

    public List shipmentsByRoute(Date from, Date to) {
        List rows = new ArrayList();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(
                "select r.code, r.name, count(s.shipment_id) as cnt, "
              + "  ifnull(sum(s.weight_kg),0) as total_weight, ifnull(sum(s.total_charge),0) as revenue "
              + " from routes r left join shipments s on s.route_id = r.route_id "
              + "  and s.booked_date between ? and ? "
              + " group by r.code, r.name order by cnt desc");
            ps.setTimestamp(1, new Timestamp(from.getTime()));
            ps.setTimestamp(2, new Timestamp(to.getTime()));
            rs = ps.executeQuery();
            while (rs.next()) {
                Map row = new LinkedHashMap();
                row.put("routeCode", rs.getString("code"));
                row.put("routeName", rs.getString("name"));
                row.put("count",     new Integer(rs.getInt("cnt")));
                row.put("totalWeight", rs.getBigDecimal("total_weight"));
                row.put("revenue",   rs.getBigDecimal("revenue"));
                rows.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ex) {}
            try { if (ps != null) ps.close(); } catch (Exception ex) {}
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
        return rows;
    }

    public List driverPerformance(Date from, Date to) {
        List rows = new ArrayList();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(
                "select d.employee_code, d.full_name, "
              + "  count(s.shipment_id) as cnt, "
              + "  ifnull(sum(s.weight_kg),0) as total_weight, "
              + "  ifnull(sum(s.total_charge),0) as revenue "
              + " from drivers d left join shipments s on s.driver_id = d.driver_id "
              + "  and s.booked_date between ? and ? "
              + " group by d.employee_code, d.full_name order by cnt desc");
            ps.setTimestamp(1, new Timestamp(from.getTime()));
            ps.setTimestamp(2, new Timestamp(to.getTime()));
            rs = ps.executeQuery();
            while (rs.next()) {
                Map row = new LinkedHashMap();
                row.put("employeeCode", rs.getString("employee_code"));
                row.put("driverName",   rs.getString("full_name"));
                row.put("count",        new Integer(rs.getInt("cnt")));
                row.put("totalWeight",  rs.getBigDecimal("total_weight"));
                row.put("revenue",      rs.getBigDecimal("revenue"));
                rows.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ex) {}
            try { if (ps != null) ps.close(); } catch (Exception ex) {}
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
        return rows;
    }

    public List<Map<String,String>> showTableStatus() {
        List<Map<String,String>> rows = new ArrayList<Map<String,String>>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("SHOW TABLE STATUS");
            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();
            while (rs.next()) {
                Map<String,String> row = new LinkedHashMap<String,String>();
                for (int i = 1; i <= cols; i++) {
                    Object v = rs.getObject(i);
                    row.put(md.getColumnLabel(i), v == null ? "" : v.toString());
                }
                rows.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ex) {}
            try { if (st != null) st.close(); } catch (Exception ex) {}
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
        return rows;
    }

    public List<List<String>> runSql(String sql) {
        List<List<String>> rows = new ArrayList<List<String>>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.createStatement();
            // FIXME: this is the admin SQL runner. yes, we know.
            rs = st.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();
            // first row = column names
            List<String> header = new ArrayList<String>();
            for (int i = 1; i <= cols; i++) header.add(md.getColumnLabel(i));
            rows.add(header);
            while (rs.next()) {
                List<String> row = new ArrayList<String>();
                for (int i = 1; i <= cols; i++) {
                    Object v = rs.getObject(i);
                    row.add(v == null ? "" : v.toString());
                }
                rows.add(row);
            }
        } catch (SQLException e) {
            List<String> err = new ArrayList<String>();
            err.add("ERROR: " + e.getMessage());
            rows.add(err);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ex) {}
            try { if (st != null) st.close(); } catch (Exception ex) {}
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
        return rows;
    }

    public int runUpdate(String sql) {
        Connection con = null;
        Statement st = null;
        try {
            con = dataSource.getConnection();
            st = con.createStatement();
            return st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            try { if (st != null) st.close(); } catch (Exception ex) {}
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
    }
}
