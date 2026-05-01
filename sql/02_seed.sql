-- CargoTrak seed data.
-- Default password for all users below is "admin123" -> MD5 = 0192023a7bbd73250516f069df18b500
-- yes, MD5, no salt. legacy.

USE cargotrak;

-- Roles
INSERT INTO roles (role_id, role_name, description) VALUES
  (1, 'ADMIN',     'System administrator'),
  (2, 'OPS',       'Operations'),
  (3, 'BILLING',   'Billing & finance'),
  (4, 'DRIVER',    'Driver portal access'),
  (5, 'READONLY',  'Read-only auditor');

-- Permissions
INSERT INTO permissions (permission_id, perm_code, description) VALUES
  (1, 'USER_MANAGE',     'Manage users and roles'),
  (2, 'CUSTOMER_MANAGE', 'Manage customers'),
  (3, 'SHIPMENT_MANAGE', 'Manage shipments'),
  (4, 'BILLING_MANAGE',  'Manage invoices and payments'),
  (5, 'REPORT_VIEW',     'View reports'),
  (6, 'ADMIN_PANEL',     'Access admin panel'),
  (7, 'SQL_RUNNER',      'Run arbitrary SQL'),
  (8, 'FILE_INGEST',     'Upload and process files');

INSERT INTO role_permissions (role_id, permission_id) VALUES
  (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),
  (2,2),(2,3),(2,5),(2,8),
  (3,4),(3,5),
  (4,3),
  (5,5);

-- Users (password = 'admin123' -> MD5 0192023a7bbd73250516f069df18b500)
INSERT INTO users (user_id, username, password_hash, email, full_name, active_flag, created_date, last_login) VALUES
  (1, 'admin',   '0192023a7bbd73250516f069df18b500', 'admin@cargotrak.local',   'Admin User',     'Y', '2008-11-01', NULL),
  (2, 'rkumar',  '0192023a7bbd73250516f069df18b500', 'rkumar@cargotrak.local',  'Rajesh Kumar',   'Y', '2008-11-15', NULL),
  (3, 'miyer',   '0192023a7bbd73250516f069df18b500', 'miyer@cargotrak.local',   'M. Iyer',        'Y', '2010-03-21', NULL),
  (4, 'lchen',   '0192023a7bbd73250516f069df18b500', 'lchen@cargotrak.local',   'L. Chen',        'Y', '2012-08-09', NULL),
  (5, 'spatel',  '0192023a7bbd73250516f069df18b500', 'spatel@cargotrak.local',  'S. Patel',       'Y', '2014-03-02', NULL),
  (6, 'ops1',    '0192023a7bbd73250516f069df18b500', 'ops1@cargotrak.local',    'Ops User One',   'Y', '2015-01-10', NULL),
  (7, 'billing1','0192023a7bbd73250516f069df18b500', 'bill1@cargotrak.local',   'Billing One',    'Y', '2015-01-10', NULL),
  (8, 'driver1', '0192023a7bbd73250516f069df18b500', 'd1@cargotrak.local',      'Driver One',     'Y', '2015-06-01', NULL),
  (9, 'auditor', '0192023a7bbd73250516f069df18b500', 'audit@cargotrak.local',   'Auditor',        'Y', '2016-01-01', NULL),
  (10,'inactive','0192023a7bbd73250516f069df18b500', 'inactive@cargotrak.local','Inactive User',  'N', '2009-05-01', NULL);

INSERT INTO user_roles (user_id, role_id) VALUES
  (1,1), (2,1), (3,1),
  (4,2), (5,2), (6,2),
  (7,3),
  (8,4),
  (9,5);

-- Customers (50)
INSERT INTO customers (customer_id, customer_code, name, industry, credit_limit, payment_terms, active_flag, created_date) VALUES
  (1,'ACM001','Acme Manufacturing','Manufacturing',500000.00,'NET30','Y',NOW()),
  (2,'GLB002','Globex Foods','Food & Beverage',250000.00,'NET45','Y',NOW()),
  (3,'INI003','Initech Tech','Technology',150000.00,'NET30','Y',NOW()),
  (4,'UMB004','Umbrella Pharma','Pharmaceutical',750000.00,'NET60','Y',NOW()),
  (5,'WLI005','Wonka Industries','Food & Beverage',300000.00,'NET30','Y',NOW()),
  (6,'STK006','Stark Industries','Defense',1000000.00,'NET45','Y',NOW()),
  (7,'WAY007','Wayne Enterprises','Manufacturing',900000.00,'NET30','Y',NOW()),
  (8,'CYB008','Cyberdyne Systems','Technology',200000.00,'NET30','Y',NOW()),
  (9,'TYR009','Tyrell Corp','Biotech',600000.00,'NET45','Y',NOW()),
  (10,'WEY010','Weyland-Yutani','Aerospace',1200000.00,'NET60','Y',NOW()),
  (11,'CST011','Costas Logistics','Logistics',180000.00,'NET30','Y',NOW()),
  (12,'NPC012','NorthPole Co','Retail',90000.00,'NET30','Y',NOW()),
  (13,'BLD013','BlueDot Beverages','Food & Beverage',220000.00,'NET45','Y',NOW()),
  (14,'GRC014','Greenchem','Chemicals',430000.00,'NET30','Y',NOW()),
  (15,'OCS015','Oceanic Shipping','Logistics',310000.00,'NET30','Y',NOW()),
  (16,'PNW016','Pinewood Lumber','Construction',150000.00,'NET30','Y',NOW()),
  (17,'KKL017','Kahn Klothes','Retail',75000.00,'NET30','Y',NOW()),
  (18,'AAA018','Atlas Auto Parts','Automotive',410000.00,'NET45','Y',NOW()),
  (19,'BLK019','BlackRock Mining','Mining',720000.00,'NET60','Y',NOW()),
  (20,'CMG020','Camargo Electronics','Electronics',520000.00,'NET30','Y',NOW()),
  (21,'DLT021','DeltaTextiles','Textile',95000.00,'NET30','Y',NOW()),
  (22,'EVR022','Evergreen Pulp','Paper',230000.00,'NET45','Y',NOW()),
  (23,'FTC023','Fortuna Coffee','Food & Beverage',140000.00,'NET30','Y',NOW()),
  (24,'GRV024','Granville Glass','Manufacturing',180000.00,'NET30','Y',NOW()),
  (25,'HLM025','Helm Hardware','Retail',88000.00,'NET30','Y',NOW()),
  (26,'IRC026','Iron Crown Steel','Manufacturing',900000.00,'NET60','Y',NOW()),
  (27,'JNT027','Junto Toys','Retail',125000.00,'NET30','Y',NOW()),
  (28,'KLN028','Kelvin Cooling','HVAC',340000.00,'NET30','Y',NOW()),
  (29,'LMR029','Lambert Distillers','Beverage',280000.00,'NET45','Y',NOW()),
  (30,'MRD030','Meridian Marine','Marine',610000.00,'NET60','Y',NOW()),
  (31,'NTL031','Nautilus Labs','Biotech',380000.00,'NET45','Y',NOW()),
  (32,'OPL032','Opaline Optical','Medical',195000.00,'NET30','Y',NOW()),
  (33,'PLT033','Pilot Plastics','Manufacturing',260000.00,'NET30','Y',NOW()),
  (34,'QRR034','Quarry Stone','Construction',440000.00,'NET45','Y',NOW()),
  (35,'RDN035','Rondo Foods','Food',155000.00,'NET30','Y',NOW()),
  (36,'STR036','Sterling Silver','Jewelry',300000.00,'NET30','Y',NOW()),
  (37,'TLC037','Tellico Timber','Construction',225000.00,'NET30','Y',NOW()),
  (38,'UNF038','Unified Bakeries','Food',135000.00,'NET30','Y',NOW()),
  (39,'VRT039','Vertex Vinyl','Manufacturing',175000.00,'NET30','Y',NOW()),
  (40,'WLW040','Willow Wines','Beverage',290000.00,'NET45','Y',NOW()),
  (41,'XAN041','Xantia Apparel','Textile',110000.00,'NET30','Y',NOW()),
  (42,'YLO042','Yellowstone Outdoor','Retail',165000.00,'NET30','Y',NOW()),
  (43,'ZNC043','Zenith Camera','Electronics',210000.00,'NET30','Y',NOW()),
  (44,'AAB044','Albright Builders','Construction',520000.00,'NET45','Y',NOW()),
  (45,'BRC045','Brackett Robotics','Technology',380000.00,'NET30','Y',NOW()),
  (46,'CRO046','Croft Antiques','Retail',60000.00,'NET30','N',NOW()),
  (47,'DSL047','Daisy Solar','Energy',780000.00,'NET60','Y',NOW()),
  (48,'EML048','Emerald Mining','Mining',650000.00,'NET60','Y',NOW()),
  (49,'FRG049','Forge Foundry','Manufacturing',410000.00,'NET45','Y',NOW()),
  (50,'GRT050','Grant Furniture','Retail',120000.00,'NET30','Y',NOW());

-- Customer contacts (one per customer, 50 total)
INSERT INTO customer_contacts (customer_id, contact_name, title, email, phone, primary_flag)
  SELECT customer_id, CONCAT('Contact-',customer_code), 'Logistics Manager', CONCAT('contact.',LOWER(customer_code),'@example.com'), CONCAT('555-01',LPAD(customer_id,2,'0')), 'Y'
  FROM customers;

-- Customer addresses
INSERT INTO customer_addresses (customer_id, address_type, line1, city, state, postal_code, country)
  SELECT customer_id, 'BILLING', CONCAT(customer_id,' Main St'), 'Springfield','IL','62701','USA'
  FROM customers;
INSERT INTO customer_addresses (customer_id, address_type, line1, city, state, postal_code, country)
  SELECT customer_id, 'SHIPPING', CONCAT(customer_id,' Industrial Way'), 'Peoria','IL','61602','USA'
  FROM customers WHERE customer_id <= 30;

-- Drivers (30)
INSERT INTO drivers (driver_id, employee_code, full_name, license_number, license_expiry, phone, active_flag) VALUES
  (1,'D001','James Holden','DL10001','2027-05-12','555-1001','Y'),
  (2,'D002','Naomi Nagata','DL10002','2026-09-30','555-1002','Y'),
  (3,'D003','Alex Kamal','DL10003','2026-01-15','555-1003','Y'),
  (4,'D004','Amos Burton','DL10004','2027-11-22','555-1004','Y'),
  (5,'D005','Bobbie Draper','DL10005','2028-02-14','555-1005','Y'),
  (6,'D006','Chrisjen Avasarala','DL10006','2026-06-08','555-1006','Y'),
  (7,'D007','Camina Drummer','DL10007','2027-03-19','555-1007','Y'),
  (8,'D008','Joe Miller','DL10008','2025-12-05','555-1008','Y'),
  (9,'D009','Klaes Ashford','DL10009','2026-08-23','555-1009','Y'),
  (10,'D010','Marco Inaros','DL10010','2027-07-04','555-1010','Y'),
  (11,'D011','Filip Inaros','DL10011','2028-01-09','555-1011','Y'),
  (12,'D012','Anderson Dawes','DL10012','2026-04-18','555-1012','Y'),
  (13,'D013','Fred Johnson','DL10013','2027-10-27','555-1013','Y'),
  (14,'D014','Praxidike Meng','DL10014','2026-11-11','555-1014','Y'),
  (15,'D015','Roberta Draper','DL10015','2028-03-21','555-1015','Y'),
  (16,'D016','Octavia Butler','DL10016','2027-05-14','555-1016','Y'),
  (17,'D017','Liu Ming','DL10017','2026-12-30','555-1017','Y'),
  (18,'D018','Diego Marquez','DL10018','2027-02-28','555-1018','Y'),
  (19,'D019','Robert Singh','DL10019','2026-07-17','555-1019','Y'),
  (20,'D020','Hannah Cole','DL10020','2028-04-22','555-1020','Y'),
  (21,'D021','Marcus Allen','DL10021','2026-10-09','555-1021','Y'),
  (22,'D022','Sasha Ivanov','DL10022','2027-08-13','555-1022','Y'),
  (23,'D023','Priya Patel','DL10023','2027-01-30','555-1023','Y'),
  (24,'D024','Tomas Castro','DL10024','2026-05-25','555-1024','Y'),
  (25,'D025','Yuki Tanaka','DL10025','2028-06-11','555-1025','Y'),
  (26,'D026','Olu Adekunle','DL10026','2026-02-19','555-1026','Y'),
  (27,'D027','Henrik Olsen','DL10027','2027-04-07','555-1027','Y'),
  (28,'D028','Maria Garcia','DL10028','2026-08-15','555-1028','Y'),
  (29,'D029','Paul Williams','DL10029','2025-11-26','555-1029','N'),
  (30,'D030','Linh Nguyen','DL10030','2027-09-02','555-1030','Y');

-- Vehicles (40)
INSERT INTO vehicles (vehicle_id, plate_number, make, model, year, capacity_kg, vehicle_type, status) VALUES
  (1,'IL-AAA-101','Volvo','FH16',2018,26000.00,'TRACTOR','AVAILABLE'),
  (2,'IL-AAA-102','Volvo','FH16',2018,26000.00,'TRACTOR','AVAILABLE'),
  (3,'IL-AAA-103','Volvo','FH16',2019,26000.00,'TRACTOR','IN_USE'),
  (4,'IL-AAB-201','Mercedes-Benz','Actros',2017,24000.00,'TRACTOR','MAINTENANCE'),
  (5,'IL-AAB-202','Mercedes-Benz','Actros',2017,24000.00,'TRACTOR','AVAILABLE'),
  (6,'IL-AAB-203','Mercedes-Benz','Actros',2018,24000.00,'TRACTOR','AVAILABLE'),
  (7,'IL-AAB-204','Mercedes-Benz','Actros',2019,24000.00,'TRACTOR','IN_USE'),
  (8,'IL-AAC-301','Scania','R450',2018,22000.00,'TRACTOR','AVAILABLE'),
  (9,'IL-AAC-302','Scania','R450',2019,22000.00,'TRACTOR','AVAILABLE'),
  (10,'IL-AAC-303','Scania','R450',2019,22000.00,'TRACTOR','AVAILABLE'),
  (11,'IL-AAD-401','MAN','TGX',2017,21000.00,'TRACTOR','AVAILABLE'),
  (12,'IL-AAD-402','MAN','TGX',2018,21000.00,'TRACTOR','IN_USE'),
  (13,'IL-AAE-501','Iveco','Stralis',2016,20000.00,'TRACTOR','AVAILABLE'),
  (14,'IL-AAE-502','Iveco','Stralis',2017,20000.00,'TRACTOR','AVAILABLE'),
  (15,'IL-BBA-101','Ford','F-650',2018,8000.00,'BOX','AVAILABLE'),
  (16,'IL-BBA-102','Ford','F-650',2018,8000.00,'BOX','AVAILABLE'),
  (17,'IL-BBA-103','Ford','F-650',2019,8000.00,'BOX','IN_USE'),
  (18,'IL-BBB-201','Hino','268',2017,7500.00,'BOX','AVAILABLE'),
  (19,'IL-BBB-202','Hino','268',2018,7500.00,'BOX','AVAILABLE'),
  (20,'IL-BBC-301','Isuzu','NPR',2016,6000.00,'BOX','AVAILABLE'),
  (21,'IL-BBC-302','Isuzu','NPR',2017,6000.00,'BOX','AVAILABLE'),
  (22,'IL-BBD-401','Kenworth','T270',2018,9500.00,'BOX','MAINTENANCE'),
  (23,'IL-BBD-402','Kenworth','T270',2019,9500.00,'BOX','AVAILABLE'),
  (24,'IL-BBE-501','Peterbilt','337',2018,9500.00,'BOX','AVAILABLE'),
  (25,'IL-BBE-502','Peterbilt','337',2018,9500.00,'BOX','AVAILABLE'),
  (26,'IL-CCA-101','Mercedes-Benz','Sprinter',2019,3500.00,'VAN','AVAILABLE'),
  (27,'IL-CCA-102','Mercedes-Benz','Sprinter',2019,3500.00,'VAN','AVAILABLE'),
  (28,'IL-CCB-201','Ford','Transit',2018,3500.00,'VAN','IN_USE'),
  (29,'IL-CCB-202','Ford','Transit',2019,3500.00,'VAN','AVAILABLE'),
  (30,'IL-CCC-301','Ram','ProMaster',2018,2700.00,'VAN','AVAILABLE'),
  (31,'IL-CCC-302','Ram','ProMaster',2019,2700.00,'VAN','AVAILABLE'),
  (32,'IL-DDA-101','Wabash','DryVan',2017,30000.00,'TRAILER','AVAILABLE'),
  (33,'IL-DDA-102','Wabash','DryVan',2018,30000.00,'TRAILER','AVAILABLE'),
  (34,'IL-DDA-103','Wabash','DryVan',2018,30000.00,'TRAILER','AVAILABLE'),
  (35,'IL-DDB-201','Great Dane','DryVan',2018,30000.00,'TRAILER','AVAILABLE'),
  (36,'IL-DDB-202','Great Dane','Reefer',2019,28000.00,'REEFER','AVAILABLE'),
  (37,'IL-DDC-301','Utility','Reefer',2019,28000.00,'REEFER','IN_USE'),
  (38,'IL-DDC-302','Utility','Reefer',2018,28000.00,'REEFER','AVAILABLE'),
  (39,'IL-DDD-401','Hyundai','DryVan',2018,30000.00,'TRAILER','MAINTENANCE'),
  (40,'IL-DDD-402','Hyundai','DryVan',2019,30000.00,'TRAILER','AVAILABLE');

-- Vehicle maintenance (a few records)
INSERT INTO vehicle_maintenance (vehicle_id, maint_date, description, cost, odometer, technician) VALUES
  (1, '2025-12-15', 'Oil change, tire rotation', 450.00, 145000, 'Bob'),
  (4, '2026-01-20', 'Brake pads, rotors', 1200.00, 210000, 'Carl'),
  (4, '2026-03-05', 'Engine diagnostics', 850.00, 213500, 'Carl'),
  (22,'2026-02-10', 'Transmission service', 2200.00, 187000, 'Bob'),
  (39,'2026-03-22', 'Trailer brakes', 950.00, 120000, 'Carl');

-- Warehouses
INSERT INTO warehouses (warehouse_id, code, name, city, capacity_m3) VALUES
  (1,'WH-CHI','Chicago Hub','Chicago',12000.00),
  (2,'WH-DAL','Dallas DC','Dallas',9000.00),
  (3,'WH-LAX','LA Crossdock','Los Angeles',15000.00);

INSERT INTO warehouse_zones (warehouse_id, code, description) VALUES
  (1,'A','Cold storage'),
  (1,'B','General'),
  (1,'C','Hazmat'),
  (2,'A','General'),
  (2,'B','Bulk'),
  (3,'A','Reefer'),
  (3,'B','General'),
  (3,'C','Crossdock staging');

-- Inventory (sample)
INSERT INTO inventory_items (sku, description, zone_id, quantity, weight_kg, last_updated) VALUES
  ('SKU-1001','Box of widgets',2,500,1200.50,NOW()),
  ('SKU-1002','Pallet of bottles',5,80,3200.00,NOW()),
  ('SKU-1003','Industrial pumps',1,15,750.00,NOW()),
  ('SKU-1004','Refrigerated produce',6,200,4500.00,NOW()),
  ('SKU-1005','Raw chemicals (hazmat)',3,25,1800.00,NOW());

-- Routes (10)
INSERT INTO routes (route_id, code, name, origin, destination) VALUES
  (1,'R-CHI-DAL','Chicago to Dallas','Chicago','Dallas'),
  (2,'R-DAL-LAX','Dallas to LA','Dallas','Los Angeles'),
  (3,'R-CHI-LAX','Chicago to LA','Chicago','Los Angeles'),
  (4,'R-CHI-NYC','Chicago to NY','Chicago','New York'),
  (5,'R-NYC-MIA','NY to Miami','New York','Miami'),
  (6,'R-LAX-SEA','LA to Seattle','Los Angeles','Seattle'),
  (7,'R-DAL-MIA','Dallas to Miami','Dallas','Miami'),
  (8,'R-CHI-MIN','Chicago to Minneapolis','Chicago','Minneapolis'),
  (9,'R-DEN-CHI','Denver to Chicago','Denver','Chicago'),
  (10,'R-PHX-DAL','Phoenix to Dallas','Phoenix','Dallas');

INSERT INTO route_segments (route_id, seq_no, from_location, to_location, distance_km, expected_hours) VALUES
  (1,1,'Chicago','St. Louis',463.0,5.5),
  (1,2,'St. Louis','Dallas',1086.0,11.0),
  (2,1,'Dallas','El Paso',1015.0,10.0),
  (2,2,'El Paso','Los Angeles',1240.0,12.0),
  (3,1,'Chicago','Denver',1480.0,15.0),
  (3,2,'Denver','Los Angeles',1750.0,17.0),
  (4,1,'Chicago','Cleveland',550.0,6.0),
  (4,2,'Cleveland','New York',750.0,8.0),
  (5,1,'New York','Atlanta',1380.0,14.0),
  (5,2,'Atlanta','Miami',1080.0,11.0);

-- Rate cards
INSERT INTO rate_cards (rate_card_id, name, customer_id, effective_date, expiry_date, active_flag) VALUES
  (1,'Standard 2025',NULL,'2025-01-01','2026-12-31','Y'),
  (2,'Acme Premium',1,'2025-06-01','2026-05-31','Y'),
  (3,'Stark Defense Tier',6,'2025-01-01','2027-12-31','Y');

INSERT INTO rate_card_entries (rate_card_id, route_id, vehicle_type, base_charge, per_km_rate, per_kg_rate) VALUES
  (1,1,'TRACTOR',500.00,0.85,0.05),
  (1,2,'TRACTOR',500.00,0.85,0.05),
  (1,3,'TRACTOR',650.00,0.90,0.05),
  (1,4,'TRACTOR',500.00,0.85,0.05),
  (1,5,'TRACTOR',550.00,0.88,0.05),
  (1,6,'BOX',300.00,1.10,0.07),
  (2,1,'TRACTOR',450.00,0.80,0.04),
  (2,3,'TRACTOR',600.00,0.85,0.04),
  (3,1,'TRACTOR',700.00,1.20,0.10);

-- System config
INSERT INTO system_config (config_key, config_value, description, updated_at) VALUES
  ('app.name','CargoTrak','Application display name',NOW()),
  ('app.version','3.4.2','Current version',NOW()),
  ('mail.smtp.host','localhost','SMTP host',NOW()),
  ('mail.smtp.port','25','SMTP port',NOW()),
  ('mail.from','noreply@cargotrak.local','Default from address',NOW()),
  ('finance.team.email','finance@cargotrak.local','AR aging recipient',NOW()),
  ('upload.max.size','52428800','50MB upload cap',NOW()),
  ('invoice.tax.rate','0.0825','Default tax rate',NOW()),
  ('reports.dir','/var/app/cargo/reports','Output directory for generated reports',NOW()),
  ('uploads.dir','/var/app/cargo/uploads','Upload landing directory',NOW()),
  ('inbound.dir','/var/app/cargo/inbound','Carrier feed inbound directory',NOW()),
  ('archive.dir','/var/app/cargo/archive','Daily archive of uploads',NOW()),
  ('logs.dir','/var/app/cargo/logs','Application log directory',NOW());

-- Email templates
INSERT INTO email_templates (template_code, subject, body) VALUES
  ('PASSWORD_RESET','Your CargoTrak temporary password','Hello ${fullName},\n\nYour temporary password is: ${tempPassword}\n\nPlease log in at ${appUrl} and change it immediately.\n\nCargoTrak System'),
  ('SHIPMENT_DELIVERED','Shipment ${trackingNo} delivered','Hello ${customerName},\n\nYour shipment ${trackingNo} was delivered on ${deliveryDate}.\n\nThank you for shipping with ACME.\nCargoTrak'),
  ('INVOICE_GENERATED','Invoice ${invoiceNo} - ${customerName}','Hello ${customerName},\n\nInvoice ${invoiceNo} for ${total} has been generated.\nDue date: ${dueDate}.\n\nCargoTrak Billing'),
  ('AR_AGING_REPORT','Weekly AR aging report','Team,\n\nThe weekly AR aging report is attached.\n\nCargoTrak Scheduler');

-- Scheduled jobs metadata (the actual schedule lives in quartz-jobs.xml)
INSERT INTO scheduled_jobs (job_name, cron_expr, enabled_flag) VALUES
  ('NightlyInvoiceJob','0 0 1 * * ?','Y'),
  ('HourlyCarrierFeedJob','0 0 * * * ?','Y'),
  ('WeeklyArAgingJob','0 0 6 ? * MON','Y'),
  ('DailyBackupJob','0 30 2 * * ?','Y'),
  ('DemoEveryMinuteJob','0 * * * * ?','Y');

-- Shipments (200 across many statuses)
-- We use a quick stored procedure to seed them. Actually keep it inline -- ops complains about too many procs.
INSERT INTO shipments (tracking_no, customer_id, origin, destination, weight_kg, volume_m3, declared_value, status, booked_date, pickup_date, delivery_date, driver_id, vehicle_id, route_id, rate_card_id, total_charge, notes)
SELECT
  CONCAT('TRK-',LPAD(t.n,6,'0')),
  ((t.n - 1) MOD 50) + 1,
  CASE (t.n MOD 5) WHEN 0 THEN 'Chicago' WHEN 1 THEN 'Dallas' WHEN 2 THEN 'New York' WHEN 3 THEN 'Phoenix' ELSE 'Denver' END,
  CASE (t.n MOD 5) WHEN 0 THEN 'Dallas' WHEN 1 THEN 'Los Angeles' WHEN 2 THEN 'Miami' WHEN 3 THEN 'Dallas' ELSE 'Chicago' END,
  100.0 + ((t.n * 13) MOD 5000),
  1.0 + ((t.n * 7) MOD 80),
  500.0 + ((t.n * 11) MOD 50000),
  CASE (t.n MOD 6)
    WHEN 0 THEN 'DRAFT'
    WHEN 1 THEN 'BOOKED'
    WHEN 2 THEN 'IN_TRANSIT'
    WHEN 3 THEN 'DELIVERED'
    WHEN 4 THEN 'INVOICED'
    ELSE 'CLOSED'
  END,
  DATE_SUB(NOW(), INTERVAL (t.n MOD 90) DAY),
  DATE_SUB(NOW(), INTERVAL (t.n MOD 90) DAY),
  CASE WHEN (t.n MOD 6) >= 3 THEN DATE_SUB(NOW(), INTERVAL ((t.n MOD 90) - 5) DAY) ELSE NULL END,
  ((t.n - 1) MOD 30) + 1,
  ((t.n - 1) MOD 40) + 1,
  ((t.n - 1) MOD 10) + 1,
  1,
  500.00 + ((t.n * 17) MOD 5000),
  CONCAT('Auto-seeded shipment #',t.n)
FROM (
  SELECT a.N + b.N * 10 + c.N * 100 + 1 AS n
  FROM
    (SELECT 0 AS N UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) a,
    (SELECT 0 AS N UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) b,
    (SELECT 0 AS N UNION SELECT 1) c
) t
WHERE t.n <= 200;

-- One leg per shipment (kept simple; some shipments have legs at all)
INSERT INTO shipment_legs (shipment_id, seq_no, from_location, to_location, driver_id, vehicle_id, departure_time, arrival_time, status)
SELECT shipment_id, 1, origin, destination, driver_id, vehicle_id, booked_date, delivery_date,
  CASE status WHEN 'DRAFT' THEN 'PLANNED' WHEN 'BOOKED' THEN 'SCHEDULED' ELSE 'COMPLETED' END
FROM shipments;

INSERT INTO shipment_status_history (shipment_id, old_status, new_status, changed_by, changed_at, notes)
SELECT shipment_id, NULL, 'DRAFT', 'system', booked_date, 'Created' FROM shipments;
INSERT INTO shipment_status_history (shipment_id, old_status, new_status, changed_by, changed_at, notes)
SELECT shipment_id, 'DRAFT', status, 'system', booked_date, 'Auto-progressed' FROM shipments WHERE status <> 'DRAFT';

-- Invoices for shipments in INVOICED/CLOSED
INSERT INTO invoices (invoice_no, customer_id, invoice_date, due_date, status, subtotal, tax, total, amount_paid)
SELECT CONCAT('INV-',LPAD(shipment_id,6,'0')), customer_id, delivery_date, DATE_ADD(delivery_date, INTERVAL 30 DAY),
  CASE status WHEN 'CLOSED' THEN 'PAID' ELSE 'OPEN' END,
  total_charge, total_charge * 0.0825, total_charge * 1.0825,
  CASE status WHEN 'CLOSED' THEN total_charge * 1.0825 ELSE 0 END
FROM shipments WHERE status IN ('INVOICED','CLOSED');

INSERT INTO invoice_line_items (invoice_id, shipment_id, description, quantity, unit_price, line_total)
SELECT i.invoice_id, s.shipment_id, CONCAT('Freight charge for ',s.tracking_no), 1.0, s.total_charge, s.total_charge
FROM invoices i JOIN shipments s ON s.shipment_id = CAST(SUBSTRING(i.invoice_no,5) AS UNSIGNED);

-- A handful of payments
INSERT INTO payments (customer_id, payment_date, amount, method, reference, notes) VALUES
  (1, DATE_SUB(NOW(), INTERVAL 5 DAY), 5500.00, 'WIRE','WIRE-AC1','Acme partial payment'),
  (2, DATE_SUB(NOW(), INTERVAL 10 DAY), 2200.00, 'CHECK','CHK-2001','Globex monthly'),
  (3, DATE_SUB(NOW(), INTERVAL 15 DAY), 1800.00, 'ACH','ACH-3001','Initech'),
  (5, DATE_SUB(NOW(), INTERVAL 7 DAY),  3200.00, 'WIRE','WIRE-WK1','Wonka');

-- Audit log seed
INSERT INTO audit_log (user_id, username, action, entity_type, entity_id, details, audit_time) VALUES
  (1,'admin','LOGIN','User','1','Initial admin login',NOW()),
  (2,'rkumar','CREATE','Customer','1','Seeded customer',NOW()),
  (3,'miyer','UPDATE','Shipment','42','Status -> IN_TRANSIT',NOW());
