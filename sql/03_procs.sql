-- Stored procedures used by the reporting module.
-- Called via CallableStatement from ReportDaoImpl.
-- Authoring: L. Chen 2012-09. "Just easier than writing this in Java." - lchen

USE cargotrak;

DROP PROCEDURE IF EXISTS sp_revenue_by_customer;
DELIMITER $$
CREATE PROCEDURE sp_revenue_by_customer (IN p_from_date DATETIME, IN p_to_date DATETIME)
BEGIN
    SELECT
        c.customer_id,
        c.customer_code,
        c.name AS customer_name,
        COUNT(i.invoice_id)            AS invoice_count,
        IFNULL(SUM(i.total),0)         AS gross_revenue,
        IFNULL(SUM(i.amount_paid),0)   AS amount_paid,
        IFNULL(SUM(i.total) - SUM(i.amount_paid),0) AS outstanding
    FROM customers c
    LEFT JOIN invoices i ON i.customer_id = c.customer_id
        AND i.invoice_date >= p_from_date
        AND i.invoice_date <= p_to_date
    GROUP BY c.customer_id, c.customer_code, c.name
    ORDER BY gross_revenue DESC;
END$$
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_fleet_utilization;
DELIMITER $$
CREATE PROCEDURE sp_fleet_utilization (IN p_from_date DATETIME, IN p_to_date DATETIME)
BEGIN
    SELECT
        v.vehicle_id,
        v.plate_number,
        v.vehicle_type,
        v.status,
        COUNT(s.shipment_id)             AS shipment_count,
        IFNULL(SUM(s.weight_kg),0)       AS total_weight,
        IFNULL(SUM(s.total_charge),0)    AS total_revenue
    FROM vehicles v
    LEFT JOIN shipments s ON s.vehicle_id = v.vehicle_id
        AND s.booked_date >= p_from_date
        AND s.booked_date <= p_to_date
    GROUP BY v.vehicle_id, v.plate_number, v.vehicle_type, v.status
    ORDER BY shipment_count DESC;
END$$
DELIMITER ;
