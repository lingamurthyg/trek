-- CargoTrak bulk dummy data.
-- Generates roughly 700 MB - 1 GB of realistic-ish operational data on top of
-- 02_seed.sql, intended for performance testing of legacy modernization tools.
--
-- Volumes:
--   customers              50 ->     5,000   (+4,950)
--   shipments             200 ->   500,000   (+499,800)
--   shipment_legs                 +500,000
--   shipment_status_history       +999,600   (~2 per bulk shipment)
--   invoices                      ~166,000   (one per bulk shipment in INVOICED/CLOSED)
--   invoice_line_items            ~332,000   (two lines per bulk invoice)
--   audit_log                   +1,000,000   (with chunky TEXT details)
--   notifications                 +200,000   (~1-2 KB body each)
--
-- Uses the MariaDB Sequence Storage Engine (built in since 10.0) to materialize
-- ranges of integers cheaply (`SELECT seq FROM seq_1_to_500000`), which makes
-- a single INSERT ... SELECT capable of generating hundreds of thousands of
-- rows in one go.
--
-- Expected runtime on first boot: 3-7 minutes depending on host I/O.

USE cargotrak;

SET autocommit = 0;
SET unique_checks = 0;
SET foreign_key_checks = 0;
-- The Sequence Storage Engine returns seq as BIGINT UNSIGNED; expressions like
-- (seq MOD 1825) - 1 underflow when (seq MOD 1825) is 0. Switching off strict
-- mode and adding NO_UNSIGNED_SUBTRACTION makes the result a signed integer.
SET SESSION sql_mode = 'NO_UNSIGNED_SUBTRACTION';

-- ===========================================================================
-- 1. Bulk customers (4,950 new -> total 5,000)
-- ===========================================================================
INSERT IGNORE INTO customers
  (customer_code, name, industry, credit_limit, payment_terms, active_flag, created_date)
SELECT
  CONCAT('GEN', LPAD(seq, 7, '0')),
  CONCAT(
    ELT((seq MOD 20) + 1,
        'Acme', 'Globex', 'Initech', 'Umbrella', 'Wonka', 'Stark', 'Wayne',
        'Cyberdyne', 'Tyrell', 'Weyland', 'Costas', 'Pinewood', 'Atlas',
        'BlackRock', 'Camargo', 'Delta', 'Evergreen', 'Fortuna', 'Granville',
        'Helm'),
    ' ',
    ELT((seq MOD 15) + 1,
        'Manufacturing', 'Foods', 'Tech', 'Pharma', 'Industries',
        'Shipping', 'Distribution', 'Logistics', 'Imports', 'Exports',
        'Holdings', 'Group', 'Co', 'Corp', 'LLC'),
    ' #', seq),
  ELT((seq MOD 8) + 1,
      'Manufacturing', 'Food & Beverage', 'Technology', 'Retail',
      'Logistics', 'Mining', 'Construction', 'Pharmaceutical'),
  100000 + ((seq * 137) MOD 1000000),
  ELT((seq MOD 4) + 1, 'NET30', 'NET45', 'NET60', 'COD'),
  IF(seq MOD 50 = 0, 'N', 'Y'),
  DATE_SUB(NOW(), INTERVAL (seq MOD 3650) DAY)
FROM seq_51_to_5000;
COMMIT;

-- A couple of contacts/addresses each (gives us another ~10K rows total)
INSERT IGNORE INTO customer_contacts
  (customer_id, contact_name, title, email, phone, primary_flag)
SELECT
  c.customer_id,
  CONCAT('Contact ', c.customer_code),
  ELT((c.customer_id MOD 5) + 1,
      'Logistics Manager', 'AP Manager', 'Operations Director',
      'Procurement Lead', 'CFO'),
  CONCAT('contact.', LOWER(c.customer_code), '@example.com'),
  CONCAT('555-', LPAD(c.customer_id MOD 10000, 4, '0')),
  'Y'
FROM customers c
WHERE c.customer_id > 50
  AND NOT EXISTS (SELECT 1 FROM customer_contacts cc WHERE cc.customer_id = c.customer_id);
COMMIT;

INSERT IGNORE INTO customer_addresses
  (customer_id, address_type, line1, city, state, postal_code, country)
SELECT
  c.customer_id,
  'BILLING',
  CONCAT(c.customer_id, ' Industrial Pkwy'),
  ELT((c.customer_id MOD 10) + 1,
      'Chicago', 'Dallas', 'New York', 'Phoenix', 'Denver',
      'Miami', 'Seattle', 'Atlanta', 'Boston', 'Portland'),
  ELT((c.customer_id MOD 10) + 1,
      'IL', 'TX', 'NY', 'AZ', 'CO', 'FL', 'WA', 'GA', 'MA', 'OR'),
  LPAD((c.customer_id * 7) MOD 100000, 5, '0'),
  'USA'
FROM customers c
WHERE c.customer_id > 50
  AND NOT EXISTS (SELECT 1 FROM customer_addresses ca WHERE ca.customer_id = c.customer_id);
COMMIT;

-- ===========================================================================
-- 2. Bulk shipments (499,800 new -> total 500,000)
--    Note: existing seeds have shipment_id 1-200.
-- ===========================================================================
-- We do this in 5 batches of 100K to keep transactions reasonable.
INSERT IGNORE INTO shipments
  (tracking_no, customer_id, origin, destination, weight_kg, volume_m3,
   declared_value, status, booked_date, pickup_date, delivery_date,
   driver_id, vehicle_id, route_id, rate_card_id, total_charge, notes)
SELECT
  CONCAT('TRK-BULK-', LPAD(seq, 8, '0')),
  ((seq - 1) MOD 5000) + 1,
  ELT((seq MOD 10) + 1,
      'Chicago', 'Dallas', 'New York', 'Phoenix', 'Denver',
      'Miami', 'Seattle', 'Atlanta', 'Boston', 'Portland'),
  ELT(((seq * 7) MOD 10) + 1,
      'Los Angeles', 'Houston', 'Detroit', 'Memphis', 'Salt Lake City',
      'Cleveland', 'Cincinnati', 'Indianapolis', 'Minneapolis', 'St. Louis'),
  100.0 + ((seq * 13) MOD 5000),
  1.0 + ((seq * 7) MOD 80),
  500.0 + ((seq * 11) MOD 50000),
  ELT((seq MOD 6) + 1, 'DRAFT', 'BOOKED', 'IN_TRANSIT', 'DELIVERED', 'INVOICED', 'CLOSED'),
  DATE_SUB(NOW(), INTERVAL (seq MOD 1825) DAY),
  DATE_SUB(NOW(), INTERVAL ((seq MOD 1825) - 1) DAY),
  CASE WHEN (seq MOD 6) >= 3 THEN DATE_SUB(NOW(), INTERVAL ((seq MOD 1825) - 5) DAY) ELSE NULL END,
  ((seq - 1) MOD 30) + 1,
  ((seq - 1) MOD 40) + 1,
  ((seq - 1) MOD 10) + 1,
  ((seq MOD 3) + 1),
  500.0 + ((seq * 17) MOD 5000),
  CONCAT('Bulk-generated shipment row #', seq, '. ',
         REPEAT('Lorem ipsum dolor sit amet, consectetur adipiscing elit. ', 3))
FROM seq_201_to_100200;
COMMIT;

INSERT IGNORE INTO shipments
  (tracking_no, customer_id, origin, destination, weight_kg, volume_m3,
   declared_value, status, booked_date, pickup_date, delivery_date,
   driver_id, vehicle_id, route_id, rate_card_id, total_charge, notes)
SELECT
  CONCAT('TRK-BULK-', LPAD(seq, 8, '0')),
  ((seq - 1) MOD 5000) + 1,
  ELT((seq MOD 10) + 1, 'Chicago', 'Dallas', 'New York', 'Phoenix', 'Denver', 'Miami', 'Seattle', 'Atlanta', 'Boston', 'Portland'),
  ELT(((seq * 7) MOD 10) + 1, 'Los Angeles', 'Houston', 'Detroit', 'Memphis', 'Salt Lake City', 'Cleveland', 'Cincinnati', 'Indianapolis', 'Minneapolis', 'St. Louis'),
  100.0 + ((seq * 13) MOD 5000),
  1.0 + ((seq * 7) MOD 80),
  500.0 + ((seq * 11) MOD 50000),
  ELT((seq MOD 6) + 1, 'DRAFT', 'BOOKED', 'IN_TRANSIT', 'DELIVERED', 'INVOICED', 'CLOSED'),
  DATE_SUB(NOW(), INTERVAL (seq MOD 1825) DAY),
  DATE_SUB(NOW(), INTERVAL ((seq MOD 1825) - 1) DAY),
  CASE WHEN (seq MOD 6) >= 3 THEN DATE_SUB(NOW(), INTERVAL ((seq MOD 1825) - 5) DAY) ELSE NULL END,
  ((seq - 1) MOD 30) + 1,
  ((seq - 1) MOD 40) + 1,
  ((seq - 1) MOD 10) + 1,
  ((seq MOD 3) + 1),
  500.0 + ((seq * 17) MOD 5000),
  CONCAT('Bulk-generated shipment row #', seq, '. ', REPEAT('Lorem ipsum dolor sit amet, consectetur adipiscing elit. ', 3))
FROM seq_100201_to_200200;
COMMIT;

INSERT IGNORE INTO shipments
  (tracking_no, customer_id, origin, destination, weight_kg, volume_m3,
   declared_value, status, booked_date, pickup_date, delivery_date,
   driver_id, vehicle_id, route_id, rate_card_id, total_charge, notes)
SELECT
  CONCAT('TRK-BULK-', LPAD(seq, 8, '0')),
  ((seq - 1) MOD 5000) + 1,
  ELT((seq MOD 10) + 1, 'Chicago', 'Dallas', 'New York', 'Phoenix', 'Denver', 'Miami', 'Seattle', 'Atlanta', 'Boston', 'Portland'),
  ELT(((seq * 7) MOD 10) + 1, 'Los Angeles', 'Houston', 'Detroit', 'Memphis', 'Salt Lake City', 'Cleveland', 'Cincinnati', 'Indianapolis', 'Minneapolis', 'St. Louis'),
  100.0 + ((seq * 13) MOD 5000),
  1.0 + ((seq * 7) MOD 80),
  500.0 + ((seq * 11) MOD 50000),
  ELT((seq MOD 6) + 1, 'DRAFT', 'BOOKED', 'IN_TRANSIT', 'DELIVERED', 'INVOICED', 'CLOSED'),
  DATE_SUB(NOW(), INTERVAL (seq MOD 1825) DAY),
  DATE_SUB(NOW(), INTERVAL ((seq MOD 1825) - 1) DAY),
  CASE WHEN (seq MOD 6) >= 3 THEN DATE_SUB(NOW(), INTERVAL ((seq MOD 1825) - 5) DAY) ELSE NULL END,
  ((seq - 1) MOD 30) + 1,
  ((seq - 1) MOD 40) + 1,
  ((seq - 1) MOD 10) + 1,
  ((seq MOD 3) + 1),
  500.0 + ((seq * 17) MOD 5000),
  CONCAT('Bulk-generated shipment row #', seq, '. ', REPEAT('Lorem ipsum dolor sit amet, consectetur adipiscing elit. ', 3))
FROM seq_200201_to_300200;
COMMIT;

INSERT IGNORE INTO shipments
  (tracking_no, customer_id, origin, destination, weight_kg, volume_m3,
   declared_value, status, booked_date, pickup_date, delivery_date,
   driver_id, vehicle_id, route_id, rate_card_id, total_charge, notes)
SELECT
  CONCAT('TRK-BULK-', LPAD(seq, 8, '0')),
  ((seq - 1) MOD 5000) + 1,
  ELT((seq MOD 10) + 1, 'Chicago', 'Dallas', 'New York', 'Phoenix', 'Denver', 'Miami', 'Seattle', 'Atlanta', 'Boston', 'Portland'),
  ELT(((seq * 7) MOD 10) + 1, 'Los Angeles', 'Houston', 'Detroit', 'Memphis', 'Salt Lake City', 'Cleveland', 'Cincinnati', 'Indianapolis', 'Minneapolis', 'St. Louis'),
  100.0 + ((seq * 13) MOD 5000),
  1.0 + ((seq * 7) MOD 80),
  500.0 + ((seq * 11) MOD 50000),
  ELT((seq MOD 6) + 1, 'DRAFT', 'BOOKED', 'IN_TRANSIT', 'DELIVERED', 'INVOICED', 'CLOSED'),
  DATE_SUB(NOW(), INTERVAL (seq MOD 1825) DAY),
  DATE_SUB(NOW(), INTERVAL ((seq MOD 1825) - 1) DAY),
  CASE WHEN (seq MOD 6) >= 3 THEN DATE_SUB(NOW(), INTERVAL ((seq MOD 1825) - 5) DAY) ELSE NULL END,
  ((seq - 1) MOD 30) + 1,
  ((seq - 1) MOD 40) + 1,
  ((seq - 1) MOD 10) + 1,
  ((seq MOD 3) + 1),
  500.0 + ((seq * 17) MOD 5000),
  CONCAT('Bulk-generated shipment row #', seq, '. ', REPEAT('Lorem ipsum dolor sit amet, consectetur adipiscing elit. ', 3))
FROM seq_300201_to_400200;
COMMIT;

INSERT IGNORE INTO shipments
  (tracking_no, customer_id, origin, destination, weight_kg, volume_m3,
   declared_value, status, booked_date, pickup_date, delivery_date,
   driver_id, vehicle_id, route_id, rate_card_id, total_charge, notes)
SELECT
  CONCAT('TRK-BULK-', LPAD(seq, 8, '0')),
  ((seq - 1) MOD 5000) + 1,
  ELT((seq MOD 10) + 1, 'Chicago', 'Dallas', 'New York', 'Phoenix', 'Denver', 'Miami', 'Seattle', 'Atlanta', 'Boston', 'Portland'),
  ELT(((seq * 7) MOD 10) + 1, 'Los Angeles', 'Houston', 'Detroit', 'Memphis', 'Salt Lake City', 'Cleveland', 'Cincinnati', 'Indianapolis', 'Minneapolis', 'St. Louis'),
  100.0 + ((seq * 13) MOD 5000),
  1.0 + ((seq * 7) MOD 80),
  500.0 + ((seq * 11) MOD 50000),
  ELT((seq MOD 6) + 1, 'DRAFT', 'BOOKED', 'IN_TRANSIT', 'DELIVERED', 'INVOICED', 'CLOSED'),
  DATE_SUB(NOW(), INTERVAL (seq MOD 1825) DAY),
  DATE_SUB(NOW(), INTERVAL ((seq MOD 1825) - 1) DAY),
  CASE WHEN (seq MOD 6) >= 3 THEN DATE_SUB(NOW(), INTERVAL ((seq MOD 1825) - 5) DAY) ELSE NULL END,
  ((seq - 1) MOD 30) + 1,
  ((seq - 1) MOD 40) + 1,
  ((seq - 1) MOD 10) + 1,
  ((seq MOD 3) + 1),
  500.0 + ((seq * 17) MOD 5000),
  CONCAT('Bulk-generated shipment row #', seq, '. ', REPEAT('Lorem ipsum dolor sit amet, consectetur adipiscing elit. ', 3))
FROM seq_400201_to_500000;
COMMIT;

-- ===========================================================================
-- 3. Bulk shipment_legs (1 per bulk shipment)
-- ===========================================================================
INSERT IGNORE INTO shipment_legs
  (shipment_id, seq_no, from_location, to_location, driver_id, vehicle_id,
   departure_time, arrival_time, status)
SELECT
  shipment_id, 1, origin, destination, driver_id, vehicle_id,
  booked_date, delivery_date,
  CASE status
    WHEN 'DRAFT'      THEN 'PLANNED'
    WHEN 'BOOKED'     THEN 'SCHEDULED'
    WHEN 'IN_TRANSIT' THEN 'IN_TRANSIT'
    ELSE 'COMPLETED'
  END
FROM shipments
WHERE shipment_id > 200;
COMMIT;

-- ===========================================================================
-- 4. Bulk shipment_status_history
--    "created" entry for every shipment + a transition entry for non-DRAFT.
-- ===========================================================================
INSERT IGNORE INTO shipment_status_history
  (shipment_id, old_status, new_status, changed_by, changed_at, notes)
SELECT shipment_id, NULL, 'DRAFT', 'system', booked_date, 'Created (bulk seed)'
FROM shipments
WHERE shipment_id > 200;
COMMIT;

INSERT IGNORE INTO shipment_status_history
  (shipment_id, old_status, new_status, changed_by, changed_at, notes)
SELECT shipment_id, 'DRAFT', status, 'system',
       DATE_ADD(booked_date, INTERVAL 1 DAY),
       'Auto-progressed (bulk seed)'
FROM shipments
WHERE shipment_id > 200 AND status <> 'DRAFT';
COMMIT;

-- ===========================================================================
-- 5. Bulk invoices (one per bulk shipment in INVOICED or CLOSED)
-- ===========================================================================
INSERT IGNORE INTO invoices
  (invoice_no, customer_id, invoice_date, due_date, status,
   subtotal, tax, total, amount_paid)
SELECT
  CONCAT('INV-BULK-', LPAD(shipment_id, 8, '0')),
  customer_id,
  IFNULL(delivery_date, booked_date),
  DATE_ADD(IFNULL(delivery_date, booked_date), INTERVAL 30 DAY),
  CASE status WHEN 'CLOSED' THEN 'PAID' ELSE 'OPEN' END,
  total_charge,
  ROUND(total_charge * 0.0825, 2),
  ROUND(total_charge * 1.0825, 2),
  CASE status WHEN 'CLOSED' THEN ROUND(total_charge * 1.0825, 2) ELSE 0 END
FROM shipments
WHERE shipment_id > 200
  AND status IN ('INVOICED', 'CLOSED');
COMMIT;

-- ===========================================================================
-- 6. Bulk invoice_line_items (freight + fuel surcharge per invoice)
-- ===========================================================================
INSERT IGNORE INTO invoice_line_items
  (invoice_id, shipment_id, description, quantity, unit_price, line_total)
SELECT
  i.invoice_id,
  CAST(SUBSTRING(i.invoice_no, 10) AS UNSIGNED),
  CONCAT('Freight charge for shipment #',
         CAST(SUBSTRING(i.invoice_no, 10) AS UNSIGNED)),
  1.0,
  i.subtotal,
  i.subtotal
FROM invoices i
WHERE i.invoice_no LIKE 'INV-BULK-%';
COMMIT;

INSERT IGNORE INTO invoice_line_items
  (invoice_id, shipment_id, description, quantity, unit_price, line_total)
SELECT
  i.invoice_id,
  NULL,
  'Fuel surcharge',
  1.0,
  ROUND(i.subtotal * 0.10, 2),
  ROUND(i.subtotal * 0.10, 2)
FROM invoices i
WHERE i.invoice_no LIKE 'INV-BULK-%';
COMMIT;

-- ===========================================================================
-- 7. Bulk audit_log (1,000,000 rows with chunky TEXT details)
-- ===========================================================================
INSERT IGNORE INTO audit_log
  (user_id, username, action, entity_type, entity_id, details, audit_time)
SELECT
  ((seq - 1) MOD 10) + 1,
  ELT((seq MOD 7) + 1, 'admin', 'rkumar', 'miyer', 'lchen', 'spatel', 'ops1', 'billing1'),
  ELT((seq MOD 8) + 1, 'CREATE', 'UPDATE', 'STATUS', 'PAYMENT', 'INVOICE', 'CONFIG', 'LOGIN', 'LOGOUT'),
  ELT((seq MOD 5) + 1, 'Shipment', 'Customer', 'Invoice', 'User', 'Vehicle'),
  CAST(((seq * 31) MOD 500000) AS CHAR),
  CONCAT('Audit detail line ', seq, ': operation completed by user with parameters ',
         REPEAT('lorem ipsum dolor sit amet ', 6),
         '. context=bulk-seed batch=', FLOOR(seq / 10000)),
  DATE_SUB(NOW(), INTERVAL (seq MOD 8760) HOUR)
FROM seq_1_to_500000;
COMMIT;

INSERT IGNORE INTO audit_log
  (user_id, username, action, entity_type, entity_id, details, audit_time)
SELECT
  ((seq - 1) MOD 10) + 1,
  ELT((seq MOD 7) + 1, 'admin', 'rkumar', 'miyer', 'lchen', 'spatel', 'ops1', 'billing1'),
  ELT((seq MOD 8) + 1, 'CREATE', 'UPDATE', 'STATUS', 'PAYMENT', 'INVOICE', 'CONFIG', 'LOGIN', 'LOGOUT'),
  ELT((seq MOD 5) + 1, 'Shipment', 'Customer', 'Invoice', 'User', 'Vehicle'),
  CAST(((seq * 31) MOD 500000) AS CHAR),
  CONCAT('Audit detail line ', seq + 500000, ': operation completed by user with parameters ',
         REPEAT('lorem ipsum dolor sit amet ', 6),
         '. context=bulk-seed batch=', FLOOR((seq + 500000) / 10000)),
  DATE_SUB(NOW(), INTERVAL (seq MOD 8760) HOUR)
FROM seq_1_to_500000;
COMMIT;

-- ===========================================================================
-- 8. Bulk notifications (200,000 with ~1.5 KB body each)
-- ===========================================================================
INSERT IGNORE INTO notifications
  (user_id, subject, body, sent_at, delivered_flag)
SELECT
  ((seq - 1) MOD 10) + 1,
  CONCAT(
    ELT((seq MOD 5) + 1,
        '[CargoTrak] Shipment update',
        '[CargoTrak] Invoice generated',
        '[CargoTrak] Payment received',
        '[CargoTrak] Schedule reminder',
        '[CargoTrak] Driver license expiry warning'),
    ' #', seq),
  CONCAT('Hello,\n\nThis is an automated notification from the CargoTrak system. ',
         REPEAT('Your scheduled item has been processed and the relevant parties have been notified. ', 8),
         '\n\nReference: BULK-NOTIFY-', seq,
         '\n\nRegards,\nCargoTrak Notifications Service'),
  DATE_SUB(NOW(), INTERVAL (seq MOD 8760) HOUR),
  IF(seq MOD 5 = 0, 'N', 'Y')
FROM seq_1_to_200000;
COMMIT;

-- ===========================================================================
-- 9. Bulk payments (50K)
-- ===========================================================================
INSERT IGNORE INTO payments
  (customer_id, payment_date, amount, method, reference, notes)
SELECT
  ((seq - 1) MOD 5000) + 1,
  DATE_SUB(NOW(), INTERVAL (seq MOD 730) DAY),
  ROUND(500 + ((seq * 13) MOD 50000), 2),
  ELT((seq MOD 4) + 1, 'WIRE', 'CHECK', 'ACH', 'CARD'),
  CONCAT('REF-', LPAD(seq, 8, '0')),
  CONCAT('Bulk payment ', seq, ' - ', REPEAT('payment processing notes ', 3))
FROM seq_1_to_50000;
COMMIT;

-- ===========================================================================
-- Restore session settings + analyze
-- ===========================================================================
SET unique_checks = 1;
SET foreign_key_checks = 1;
SET autocommit = 1;

ANALYZE TABLE customers, shipments, shipment_legs, shipment_status_history,
              invoices, invoice_line_items, audit_log, notifications, payments;

-- Show final table sizes for sanity
SELECT table_name,
       table_rows,
       ROUND(((data_length + index_length) / 1024 / 1024), 1) AS mb
FROM information_schema.tables
WHERE table_schema = 'cargotrak'
ORDER BY (data_length + index_length) DESC;
