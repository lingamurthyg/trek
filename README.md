# CargoTrak

CargoTrak is the internal Cargo & Logistics Management System for ACME Freight.
It tracks customers, shipments, drivers, vehicles, warehouses, billing, and
generates the reports finance and ops need every morning.

> Internal use only. Maintained by the Logistics IT team.
> Last major revamp: 2013. Last hotfix: 2016.

## Tech stack

| Layer        | Technology                       |
| ------------ | -------------------------------- |
| Language     | Java 1.7                         |
| App server   | Apache Tomcat 7.0.109            |
| Web tier     | Apache Struts 1.3.10 + JSP/JSTL  |
| AJAX         | DWR 2.0.10                       |
| DI           | Spring 3.0.7.RELEASE (XML only)  |
| ORM          | Hibernate 3.6.10.Final (HBM XML) |
| DB           | MySQL 5.5                        |
| Pool         | Apache Commons DBCP 1.4          |
| Logging      | Log4j 1.2.17                     |
| SOAP WS      | Apache Axis 1.4                  |
| Mail         | JavaMail 1.4.7                   |
| Scheduling   | Quartz 1.8.6                     |
| PDF          | iText 2.1.7                      |
| Excel        | Apache POI 3.7                   |
| Build        | Apache Ant 1.9                   |

## Building

```
ant -f build.xml clean dist
```

This produces `dist/cargotrak.war`. Drop into Tomcat 7's `webapps/`.

## Running locally (bare metal)

1. Install Java 7, Tomcat 7.0.109, MySQL 5.5 on the box.
2. `mysql -uroot < sql/01_schema.sql`
3. `mysql -uroot cargotrak < sql/02_seed.sql`
4. `mysql -uroot cargotrak < sql/03_procs.sql`
5. Create directories on the app server:
   ```
   mkdir -p /var/app/cargo/uploads /var/app/cargo/reports
   mkdir -p /var/app/cargo/logs /var/app/cargo/inbound /var/app/cargo/archive
   chown -R tomcat:tomcat /var/app/cargo
   ```
6. Drop `dist/cargotrak.war` into `$CATALINA_HOME/webapps/`.
7. Start Tomcat. App is at `http://<host>:8080/cargotrak/`.

Default login: `admin` / `admin123`.

## Running in Docker (dev only)

See `docker/README.md`. The application code itself is Docker-unaware;
the Docker setup exists purely so we don't have to install Java 7 / MySQL 5.5
on every developer laptop.

## Filesystem layout (on the app server)

```
/var/app/cargo/uploads/    customer manifest CSVs, EDI 204/214 files
/var/app/cargo/reports/    generated PDFs and Excels
/var/app/cargo/logs/       app log files (also tailed by LogViewerServlet)
/var/app/cargo/inbound/    nightly carrier feed drops, polled hourly
/var/app/cargo/archive/    nightly backup of uploads/
```

## Modules

1. Auth & user management
2. Customer management
3. Shipment management (DRAFT → BOOKED → IN_TRANSIT → DELIVERED → INVOICED → CLOSED)
4. Driver & vehicle management
5. Warehouse & inventory
6. Billing & invoicing
7. Reporting (PDF + Excel)
8. File ingestion (CSV manifest, EDI 204/214)
9. Admin panel
10. SOAP web service (`ShipmentTrackingService`)
11. Scheduled jobs (Quartz)
12. Notifications (email)

## Layout

```
cargotrak/
├── build.xml                 Apache Ant build script
├── lib/                      Third-party JARs (see lib/README.txt)
├── sql/                      MySQL schema, seed data, stored procedures
├── src/main/java/            Application source
├── src/main/resources/       Spring, Hibernate, Struts, Quartz, log4j config
├── src/main/webapp/          JSPs, static assets, web.xml
└── docker/                   Containerised dev runtime (separate)
```

## Authors / history

Originally written by Rajesh Kumar, M. Iyer (offshore), L. Chen, and S. Patel
between 2008 and 2013. Many of the comments and HACK markers are theirs.
