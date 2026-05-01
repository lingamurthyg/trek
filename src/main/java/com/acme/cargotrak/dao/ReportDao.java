package com.acme.cargotrak.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ReportDao {

    /** stored proc sp_revenue_by_customer */
    List revenueByCustomer(Date from, Date to);

    /** stored proc sp_fleet_utilization */
    List fleetUtilization(Date from, Date to);

    /** raw sql */
    List onTimeDeliveryStats(Date from, Date to);

    /** raw sql */
    List arAging(Date asOf);

    /** raw sql */
    List shipmentsByRoute(Date from, Date to);

    /** raw sql */
    List driverPerformance(Date from, Date to);

    /** SHOW TABLE STATUS dump for the admin db-health page */
    List<Map<String,String>> showTableStatus();

    /** runs an arbitrary SELECT (admin SQL runner) */
    List<List<String>> runSql(String sql);

    /** runs an arbitrary update (admin SQL runner) */
    int runUpdate(String sql);
}
