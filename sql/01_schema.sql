-- CargoTrak schema. MySQL 5.5.
-- Run as root.
-- Authoring: Rajesh Kumar 2008-11. Patches: M. Iyer 2010, L. Chen 2012-08, S. Patel 2014-03.

CREATE DATABASE IF NOT EXISTS cargotrak DEFAULT CHARACTER SET utf8;
USE cargotrak;

-- ========================================================================
-- Auth
-- ========================================================================

CREATE TABLE roles (
    role_id        INT(11)      NOT NULL AUTO_INCREMENT,
    role_name      VARCHAR(255) NOT NULL,
    description    VARCHAR(255),
    PRIMARY KEY (role_id),
    UNIQUE KEY uq_role_name (role_name)
) ENGINE=InnoDB;

CREATE TABLE permissions (
    permission_id  INT(11)      NOT NULL AUTO_INCREMENT,
    perm_code      VARCHAR(255) NOT NULL,
    description    VARCHAR(255),
    PRIMARY KEY (permission_id),
    UNIQUE KEY uq_perm_code (perm_code)
) ENGINE=InnoDB;

CREATE TABLE role_permissions (
    role_id        INT(11) NOT NULL,
    permission_id  INT(11) NOT NULL,
    PRIMARY KEY (role_id, permission_id)
) ENGINE=InnoDB;
-- intentionally no FKs on role_permissions (legacy oversight)

CREATE TABLE users (
    user_id        INT(11)      NOT NULL AUTO_INCREMENT,
    username       VARCHAR(255) NOT NULL,
    password_hash  VARCHAR(255) NOT NULL,         -- MD5, no salt
    email          VARCHAR(255),
    full_name      VARCHAR(255),
    active_flag    VARCHAR(255) DEFAULT 'Y',      -- Y/N stored as varchar
    created_date   VARCHAR(255),                  -- yes, dates as varchar in this table
    last_login     DATETIME,
    PRIMARY KEY (user_id),
    UNIQUE KEY uq_username (username)
) ENGINE=InnoDB;

CREATE TABLE user_roles (
    user_id        INT(11) NOT NULL,
    role_id        INT(11) NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_ur_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_ur_role FOREIGN KEY (role_id) REFERENCES roles(role_id)
) ENGINE=InnoDB;

CREATE TABLE login_history (
    login_id       BIGINT       NOT NULL AUTO_INCREMENT,
    user_id        INT(11),
    username       VARCHAR(255),
    login_time     DATETIME,
    ip_address     VARCHAR(255),
    success_flag   VARCHAR(255),
    PRIMARY KEY (login_id)
) ENGINE=InnoDB;

CREATE TABLE audit_log (
    audit_id       BIGINT       NOT NULL AUTO_INCREMENT,
    user_id        INT(11),
    username       VARCHAR(255),
    action         VARCHAR(255),
    entity_type    VARCHAR(255),
    entity_id      VARCHAR(255),
    details        TEXT,
    audit_time     DATETIME,
    PRIMARY KEY (audit_id),
    KEY ix_audit_time (audit_time)
) ENGINE=InnoDB;

-- ========================================================================
-- Customer
-- ========================================================================

CREATE TABLE customers (
    customer_id    INT(11)      NOT NULL AUTO_INCREMENT,
    customer_code  VARCHAR(255) NOT NULL,
    name           VARCHAR(255) NOT NULL,
    industry       VARCHAR(255),
    credit_limit   DECIMAL(15,2),
    payment_terms  VARCHAR(255),
    active_flag    VARCHAR(255) DEFAULT 'Y',
    created_date   DATETIME,
    PRIMARY KEY (customer_id),
    UNIQUE KEY uq_customer_code (customer_code)
) ENGINE=InnoDB;

CREATE TABLE customer_contacts (
    contact_id     INT(11)      NOT NULL AUTO_INCREMENT,
    customer_id    INT(11)      NOT NULL,
    contact_name   VARCHAR(255),
    title          VARCHAR(255),
    email          VARCHAR(255),
    phone          VARCHAR(255),
    primary_flag   VARCHAR(255) DEFAULT 'N',
    PRIMARY KEY (contact_id),
    CONSTRAINT fk_cc_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
) ENGINE=InnoDB;

CREATE TABLE customer_addresses (
    address_id     INT(11)      NOT NULL AUTO_INCREMENT,
    customer_id    INT(11)      NOT NULL,
    address_type   VARCHAR(255),
    line1          VARCHAR(255),
    line2          VARCHAR(255),
    city           VARCHAR(255),
    state          VARCHAR(255),
    postal_code    VARCHAR(255),
    country        VARCHAR(255),
    PRIMARY KEY (address_id),
    CONSTRAINT fk_ca_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
) ENGINE=InnoDB;

-- ========================================================================
-- Drivers / Vehicles
-- ========================================================================

CREATE TABLE drivers (
    driver_id      INT(11)      NOT NULL AUTO_INCREMENT,
    employee_code  VARCHAR(255) NOT NULL,
    full_name      VARCHAR(255),
    license_number VARCHAR(255),
    license_expiry VARCHAR(255),                  -- date as varchar
    phone          VARCHAR(255),
    active_flag    VARCHAR(255) DEFAULT 'Y',
    PRIMARY KEY (driver_id),
    UNIQUE KEY uq_employee_code (employee_code)
) ENGINE=InnoDB;

CREATE TABLE vehicles (
    vehicle_id     INT(11)      NOT NULL AUTO_INCREMENT,
    plate_number   VARCHAR(255) NOT NULL,
    make           VARCHAR(255),
    model          VARCHAR(255),
    year           INT(11),
    capacity_kg    DECIMAL(15,2),
    vehicle_type   VARCHAR(255),
    status         VARCHAR(255) DEFAULT 'AVAILABLE',
    PRIMARY KEY (vehicle_id),
    UNIQUE KEY uq_plate_number (plate_number)
) ENGINE=InnoDB;

CREATE TABLE vehicle_maintenance (
    maint_id       INT(11)      NOT NULL AUTO_INCREMENT,
    vehicle_id     INT(11)      NOT NULL,
    maint_date     DATETIME,
    description    VARCHAR(255),
    cost           DECIMAL(15,2),
    odometer       INT(11),
    technician     VARCHAR(255),
    PRIMARY KEY (maint_id),
    CONSTRAINT fk_vm_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id)
) ENGINE=InnoDB;

-- ========================================================================
-- Warehouses / Inventory
-- ========================================================================

CREATE TABLE warehouses (
    warehouse_id   INT(11)      NOT NULL AUTO_INCREMENT,
    code           VARCHAR(255) NOT NULL,
    name           VARCHAR(255),
    city           VARCHAR(255),
    capacity_m3    DECIMAL(15,2),
    PRIMARY KEY (warehouse_id),
    UNIQUE KEY uq_warehouse_code (code)
) ENGINE=InnoDB;

CREATE TABLE warehouse_zones (
    zone_id        INT(11)      NOT NULL AUTO_INCREMENT,
    warehouse_id   INT(11)      NOT NULL,
    code           VARCHAR(255),
    description    VARCHAR(255),
    PRIMARY KEY (zone_id),
    CONSTRAINT fk_wz_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id)
) ENGINE=InnoDB;

CREATE TABLE inventory_items (
    item_id        INT(11)      NOT NULL AUTO_INCREMENT,
    sku            VARCHAR(255),
    description    VARCHAR(255),
    zone_id        INT(11),
    quantity       INT(11),
    weight_kg      DECIMAL(15,2),
    last_updated   DATETIME,
    PRIMARY KEY (item_id)
) ENGINE=InnoDB;
-- intentionally no FK on zone_id (legacy oversight)

-- ========================================================================
-- Routes / Rate cards
-- ========================================================================

CREATE TABLE routes (
    route_id       INT(11)      NOT NULL AUTO_INCREMENT,
    code           VARCHAR(255) NOT NULL,
    name           VARCHAR(255),
    origin         VARCHAR(255),
    destination    VARCHAR(255),
    PRIMARY KEY (route_id),
    UNIQUE KEY uq_route_code (code)
) ENGINE=InnoDB;

CREATE TABLE route_segments (
    segment_id     INT(11)      NOT NULL AUTO_INCREMENT,
    route_id       INT(11)      NOT NULL,
    seq_no         INT(11),
    from_location  VARCHAR(255),
    to_location    VARCHAR(255),
    distance_km    DECIMAL(15,2),
    expected_hours DECIMAL(15,2),
    PRIMARY KEY (segment_id),
    CONSTRAINT fk_rs_route FOREIGN KEY (route_id) REFERENCES routes(route_id)
) ENGINE=InnoDB;

CREATE TABLE rate_cards (
    rate_card_id   INT(11)      NOT NULL AUTO_INCREMENT,
    name           VARCHAR(255),
    customer_id    INT(11),
    effective_date VARCHAR(255),
    expiry_date    VARCHAR(255),
    active_flag    VARCHAR(255) DEFAULT 'Y',
    PRIMARY KEY (rate_card_id)
) ENGINE=InnoDB;

CREATE TABLE rate_card_entries (
    entry_id       INT(11)      NOT NULL AUTO_INCREMENT,
    rate_card_id   INT(11)      NOT NULL,
    route_id       INT(11),
    vehicle_type   VARCHAR(255),
    base_charge    DECIMAL(15,2),
    per_km_rate    DECIMAL(15,2),
    per_kg_rate    DECIMAL(15,2),
    PRIMARY KEY (entry_id),
    CONSTRAINT fk_rce_card FOREIGN KEY (rate_card_id) REFERENCES rate_cards(rate_card_id)
) ENGINE=InnoDB;

-- ========================================================================
-- Shipments
-- ========================================================================

CREATE TABLE shipments (
    shipment_id    INT(11)      NOT NULL AUTO_INCREMENT,
    tracking_no    VARCHAR(255) NOT NULL,
    customer_id    INT(11)      NOT NULL,
    origin         VARCHAR(255),
    destination    VARCHAR(255),
    weight_kg      DECIMAL(15,2),
    volume_m3      DECIMAL(15,2),
    declared_value DECIMAL(15,2),
    status         VARCHAR(255) DEFAULT 'DRAFT',
    booked_date    DATETIME,
    pickup_date    DATETIME,
    delivery_date  DATETIME,
    driver_id      INT(11),
    vehicle_id     INT(11),
    route_id       INT(11),
    rate_card_id   INT(11),
    total_charge   DECIMAL(15,2),
    notes          TEXT,
    PRIMARY KEY (shipment_id),
    UNIQUE KEY uq_tracking_no (tracking_no),
    CONSTRAINT fk_sh_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
) ENGINE=InnoDB;

CREATE TABLE shipment_legs (
    leg_id         INT(11)      NOT NULL AUTO_INCREMENT,
    shipment_id    INT(11)      NOT NULL,
    seq_no         INT(11),
    from_location  VARCHAR(255),
    to_location    VARCHAR(255),
    driver_id      INT(11),
    vehicle_id     INT(11),
    departure_time DATETIME,
    arrival_time   DATETIME,
    status         VARCHAR(255),
    PRIMARY KEY (leg_id),
    CONSTRAINT fk_sl_shipment FOREIGN KEY (shipment_id) REFERENCES shipments(shipment_id)
) ENGINE=InnoDB;

CREATE TABLE shipment_status_history (
    history_id     BIGINT       NOT NULL AUTO_INCREMENT,
    shipment_id    INT(11)      NOT NULL,
    old_status     VARCHAR(255),
    new_status     VARCHAR(255),
    changed_by     VARCHAR(255),
    changed_at     DATETIME,
    notes          VARCHAR(255),
    PRIMARY KEY (history_id),
    KEY ix_ssh_shipment (shipment_id)
) ENGINE=InnoDB;

CREATE TABLE shipment_documents (
    document_id    INT(11)      NOT NULL AUTO_INCREMENT,
    shipment_id    INT(11)      NOT NULL,
    document_type  VARCHAR(255),
    file_name      VARCHAR(255),
    file_path      VARCHAR(255),
    uploaded_by    VARCHAR(255),
    uploaded_at    DATETIME,
    PRIMARY KEY (document_id)
) ENGINE=InnoDB;

-- ========================================================================
-- Billing
-- ========================================================================

CREATE TABLE invoices (
    invoice_id     INT(11)      NOT NULL AUTO_INCREMENT,
    invoice_no     VARCHAR(255) NOT NULL,
    customer_id    INT(11)      NOT NULL,
    invoice_date   DATETIME,
    due_date       DATETIME,
    status         VARCHAR(255) DEFAULT 'OPEN',
    subtotal       DECIMAL(15,2),
    tax            DECIMAL(15,2),
    total          DECIMAL(15,2),
    amount_paid    DECIMAL(15,2) DEFAULT 0,
    PRIMARY KEY (invoice_id),
    UNIQUE KEY uq_invoice_no (invoice_no)
) ENGINE=InnoDB;

CREATE TABLE invoice_line_items (
    line_id        INT(11)      NOT NULL AUTO_INCREMENT,
    invoice_id     INT(11)      NOT NULL,
    shipment_id    INT(11),
    description    VARCHAR(255),
    quantity       DECIMAL(15,2),
    unit_price     DECIMAL(15,2),
    line_total     DECIMAL(15,2),
    PRIMARY KEY (line_id),
    CONSTRAINT fk_ili_invoice FOREIGN KEY (invoice_id) REFERENCES invoices(invoice_id)
) ENGINE=InnoDB;

CREATE TABLE payments (
    payment_id     INT(11)      NOT NULL AUTO_INCREMENT,
    customer_id    INT(11)      NOT NULL,
    payment_date   DATETIME,
    amount         DECIMAL(15,2),
    method         VARCHAR(255),
    reference      VARCHAR(255),
    notes          VARCHAR(255),
    PRIMARY KEY (payment_id)
) ENGINE=InnoDB;

CREATE TABLE payment_allocations (
    allocation_id  INT(11)      NOT NULL AUTO_INCREMENT,
    payment_id     INT(11)      NOT NULL,
    invoice_id     INT(11)      NOT NULL,
    amount         DECIMAL(15,2),
    PRIMARY KEY (allocation_id)
) ENGINE=InnoDB;
-- intentionally no FK on payment_allocations (legacy oversight)

-- ========================================================================
-- Misc
-- ========================================================================

CREATE TABLE system_config (
    config_key     VARCHAR(255) NOT NULL,
    config_value   VARCHAR(255),
    description    VARCHAR(255),
    updated_at     DATETIME,
    PRIMARY KEY (config_key)
) ENGINE=InnoDB;

CREATE TABLE notifications (
    notification_id BIGINT      NOT NULL AUTO_INCREMENT,
    user_id        INT(11),
    subject        VARCHAR(255),
    body           TEXT,
    sent_at        DATETIME,
    delivered_flag VARCHAR(255) DEFAULT 'N',
    PRIMARY KEY (notification_id)
) ENGINE=InnoDB;

CREATE TABLE email_templates (
    template_id    INT(11)      NOT NULL AUTO_INCREMENT,
    template_code  VARCHAR(255) NOT NULL,
    subject        VARCHAR(255),
    body           TEXT,
    PRIMARY KEY (template_id),
    UNIQUE KEY uq_template_code (template_code)
) ENGINE=InnoDB;

CREATE TABLE scheduled_jobs (
    job_id         INT(11)      NOT NULL AUTO_INCREMENT,
    job_name       VARCHAR(255) NOT NULL,
    cron_expr      VARCHAR(255),
    last_run       DATETIME,
    next_run       DATETIME,
    enabled_flag   VARCHAR(255) DEFAULT 'Y',
    PRIMARY KEY (job_id)
) ENGINE=InnoDB;
