package com.acme.cargotrak.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.acme.cargotrak.dao.CustomerDao;
import com.acme.cargotrak.dao.DriverDao;
import com.acme.cargotrak.dao.InvoiceDao;
import com.acme.cargotrak.dao.NotificationDao;
import com.acme.cargotrak.dao.PaymentDao;
import com.acme.cargotrak.dao.ShipmentDao;
import com.acme.cargotrak.dao.SystemConfigDao;
import com.acme.cargotrak.dao.UserDao;
import com.acme.cargotrak.dao.VehicleDao;
import com.acme.cargotrak.dao.WarehouseDao;
import com.acme.cargotrak.domain.Customer;
import com.acme.cargotrak.domain.Driver;
import com.acme.cargotrak.domain.Invoice;
import com.acme.cargotrak.domain.Notification;
import com.acme.cargotrak.domain.Payment;
import com.acme.cargotrak.domain.Shipment;
import com.acme.cargotrak.domain.User;
import com.acme.cargotrak.domain.Vehicle;
import com.acme.cargotrak.domain.Warehouse;
import com.acme.cargotrak.util.Constants;
import com.acme.cargotrak.util.DateUtils;
import com.acme.cargotrak.util.FileUtil;
import com.acme.cargotrak.util.MD5Util;
import com.acme.cargotrak.util.Util;

/**
 * The infamous CargoFacade.
 *
 * @author Rajesh Kumar 2008-12 (original)
 * @author M. Iyer 2010-04 ("we'll split this in v2")
 * @author L. Chen 2012-09 ("v2 isn't happening, just adding more here")
 * @author S. Patel 2014-03 ("apparently this is the API now")
 *
 * Spans every module. Don't extend it; we know.
 *
 * Static mutable cache below is shared, unsynchronized. Has occasionally caused
 * stale dashboards. Not fixing it. miyer 2011.
 */
public class CargoFacade {

    private static final Logger logger = Logger.getLogger(CargoFacade.class);

    // shared across all threads, unsynchronized. yes, we know.
    private static final Map<String, Object> CACHE = new HashMap<String, Object>();

    private UserDao userDao;
    private CustomerDao customerDao;
    private DriverDao driverDao;
    private VehicleDao vehicleDao;
    private WarehouseDao warehouseDao;
    private ShipmentDao shipmentDao;
    private InvoiceDao invoiceDao;
    private PaymentDao paymentDao;
    private NotificationDao notificationDao;
    private SystemConfigDao systemConfigDao;

    private AuthService authService;
    private UserService userService;
    private CustomerService customerService;
    private ShipmentService shipmentService;
    private DriverService driverService;
    private VehicleService vehicleService;
    private WarehouseService warehouseService;
    private BillingService billingService;
    private ReportService reportService;
    private MailService mailService;
    private SystemConfigService systemConfigService;
    private AuditService auditService;

    public void setUserDao(UserDao d) { this.userDao = d; }
    public void setCustomerDao(CustomerDao d) { this.customerDao = d; }
    public void setDriverDao(DriverDao d) { this.driverDao = d; }
    public void setVehicleDao(VehicleDao d) { this.vehicleDao = d; }
    public void setWarehouseDao(WarehouseDao d) { this.warehouseDao = d; }
    public void setShipmentDao(ShipmentDao d) { this.shipmentDao = d; }
    public void setInvoiceDao(InvoiceDao d) { this.invoiceDao = d; }
    public void setPaymentDao(PaymentDao d) { this.paymentDao = d; }
    public void setNotificationDao(NotificationDao d) { this.notificationDao = d; }
    public void setSystemConfigDao(SystemConfigDao d) { this.systemConfigDao = d; }
    public void setAuthService(AuthService s) { this.authService = s; }
    public void setUserService(UserService s) { this.userService = s; }
    public void setCustomerService(CustomerService s) { this.customerService = s; }
    public void setShipmentService(ShipmentService s) { this.shipmentService = s; }
    public void setDriverService(DriverService s) { this.driverService = s; }
    public void setVehicleService(VehicleService s) { this.vehicleService = s; }
    public void setWarehouseService(WarehouseService s) { this.warehouseService = s; }
    public void setBillingService(BillingService s) { this.billingService = s; }
    public void setReportService(ReportService s) { this.reportService = s; }
    public void setMailService(MailService s) { this.mailService = s; }
    public void setSystemConfigService(SystemConfigService s) { this.systemConfigService = s; }
    public void setAuditService(AuditService s) { this.auditService = s; }

    // ===== auth section =====

    public synchronized User login(String username, String password, String ip) {
        return authService.login(username, password, ip);
    }

    public synchronized void logout(User u, String ip) {
        authService.logout(u, ip);
    }

    public boolean changePassword(User u, String oldPwd, String newPwd) {
        return authService.changePassword(u, oldPwd, newPwd);
    }

    public String resetPassword(String username, String resetBy) {
        return authService.resetPassword(username, resetBy);
    }

    public boolean userInRole(User u, String roleName) {
        return authService.userInRole(u, roleName);
    }

    public boolean userHasPermission(User u, String permCode) {
        return authService.userHasPermission(u, permCode);
    }

    // ===== user mgmt section =====

    public List listUsers() { return userService.listAll(); }
    public List listActiveUsers() { return userService.listActive(); }
    public User findUser(Integer id) { return userService.findById(id); }
    public User findUserByUsername(String name) { return userService.findByUsername(name); }
    public Integer createUser(String username, String password, String email, String fullName) {
        return userService.createUser(username, password, email, fullName);
    }
    public void updateUser(User u) { userService.updateUser(u); }
    public void deactivateUser(Integer id) { userService.deactivate(id); }
    public void assignRole(Integer userId, String roleName) { userService.assignRole(userId, roleName); }
    public void removeRole(Integer userId, String roleName) { userService.removeRole(userId, roleName); }
    public List listRoles() { return userService.listRoles(); }

    // ===== customer mgmt section =====

    public List listCustomers() { return customerDao.findAll(Customer.class); }
    public List listActiveCustomers() { return customerService.listActive(); }
    public Customer findCustomer(Integer id) { return customerService.findById(id); }
    public Customer findCustomerByCode(String code) { return customerService.findByCode(code); }
    public List searchCustomers(String name, String industry, String activeFlag, int page, int size) {
        return customerService.search(name, industry, activeFlag, page, size);
    }
    public int countCustomers(String name, String industry, String activeFlag) {
        return customerService.countSearch(name, industry, activeFlag);
    }
    public Integer createCustomer(Customer c) { return customerService.createCustomer(c); }
    public void updateCustomer(Customer c) { customerService.updateCustomer(c); }
    public void deactivateCustomer(Integer id) { customerService.deactivate(id); }
    public List listCustomerContacts(Integer cid) { return customerService.listContacts(cid); }
    public List listCustomerAddresses(Integer cid) { return customerService.listAddresses(cid); }

    // ===== shipment section =====

    public Shipment findShipment(Integer id) { return shipmentService.findById(id); }
    public Shipment findShipmentByTracking(String tracking) { return shipmentService.findByTrackingNo(tracking); }
    public List searchShipments(String tracking, Integer customerId, String status,
                                Date from, Date to, int page, int size) {
        return shipmentService.search(tracking, customerId, status, from, to, page, size);
    }
    public int countShipments(String tracking, Integer customerId, String status, Date from, Date to) {
        return shipmentService.countSearch(tracking, customerId, status, from, to);
    }
    public Integer createShipment(Shipment s, String createdBy) {
        BigDecimal charge = shipmentService.calculateCharge(s);
        if (charge != null && charge.compareTo(BigDecimal.ZERO) > 0) {
            s.setTotalCharge(charge);
        }
        return shipmentService.createShipment(s, createdBy);
    }
    public boolean transitionShipment(Integer id, String newStatus, String by) {
        return shipmentService.transition(id, newStatus, by);
    }
    public void assignShipment(Integer id, Integer driverId, Integer vehicleId, Integer routeId, String by) {
        shipmentService.assignDriverVehicleRoute(id, driverId, vehicleId, routeId, by);
    }
    public List listShipmentLegs(Integer shipmentId) { return shipmentService.listLegs(shipmentId); }
    public List listShipmentHistory(Integer shipmentId) { return shipmentService.listStatusHistory(shipmentId); }
    public List listShipmentsByCustomer(Integer customerId) { return shipmentDao.findByCustomer(customerId); }
    public List listShipmentsByStatus(String status) { return shipmentDao.findByStatus(status); }
    public BigDecimal calculateShipmentCharge(Shipment s) { return shipmentService.calculateCharge(s); }

    // ===== driver/vehicle section =====

    public List listDrivers() { return driverService.listAll(); }
    public List listActiveDrivers() { return driverService.listActive(); }
    public Driver findDriver(Integer id) { return driverService.findById(id); }
    public Integer createDriver(Driver d) { return driverService.create(d); }
    public void updateDriver(Driver d) { driverService.update(d); }
    public List driversWithExpiringLicense(int days) { return driverService.findExpiringWithin(days); }
    public List listVehicles() { return vehicleService.listAll(); }
    public List listAvailableVehicles() { return vehicleService.listAvailable(); }
    public Vehicle findVehicle(Integer id) { return vehicleService.findById(id); }
    public Integer createVehicle(Vehicle v) { return vehicleService.create(v); }
    public void updateVehicle(Vehicle v) { vehicleService.update(v); }
    public void setVehicleStatus(Integer id, String status) { vehicleService.setStatus(id, status); }
    public List listVehicleMaintenance(Integer vehicleId) { return vehicleService.listMaintenance(vehicleId); }

    // ===== warehouse section =====

    public List listWarehouses() { return warehouseService.listWarehouses(); }
    public Warehouse findWarehouse(Integer id) { return warehouseService.findWarehouse(id); }
    public List listWarehouseZones(Integer warehouseId) { return warehouseService.listZones(warehouseId); }
    public List listInventory(Integer zoneId) { return warehouseService.listInventory(zoneId); }
    public List searchInventory(String fragment) { return warehouseService.searchInventory(fragment); }

    // ===== billing section =====

    public Invoice findInvoice(Integer id) { return billingService.findById(id); }
    public Invoice findInvoiceByNo(String no) { return billingService.findByInvoiceNo(no); }
    public List searchInvoices(String fragment, Integer customerId, String status, Date from, Date to,
                               int page, int size) {
        return billingService.search(fragment, customerId, status, from, to, page, size);
    }
    public int countInvoices(String fragment, Integer customerId, String status, Date from, Date to) {
        return billingService.countSearch(fragment, customerId, status, from, to);
    }
    public Integer generateInvoice(Integer shipmentId, String by) {
        return billingService.generateInvoiceForShipment(shipmentId, by);
    }
    public int generateAllOpenInvoices(String by) {
        return billingService.generateInvoicesForAllDelivered(by);
    }
    public Integer recordPayment(Payment p) { return billingService.recordPayment(p); }
    public void allocatePayment(Integer paymentId, Integer invoiceId, BigDecimal amount) {
        billingService.allocatePayment(paymentId, invoiceId, amount);
    }
    public List overdueInvoices() { return billingService.listOverdueInvoices(); }
    public List paymentsForCustomer(Integer customerId) { return billingService.listPaymentsForCustomer(customerId); }
    public File generateInvoicePdf(Integer invoiceId) { return reportService.generateInvoicePdf(invoiceId); }

    // ===== reporting section =====

    public List revenueReport(Date from, Date to) { return reportService.revenueByCustomer(from, to); }
    public List fleetReport(Date from, Date to) { return reportService.fleetUtilization(from, to); }
    public List onTimeDeliveryReport(Date from, Date to) { return reportService.onTimeDelivery(from, to); }
    public List arAgingReport(Date asOf) { return reportService.arAging(asOf); }
    public List shipmentsByRouteReport(Date from, Date to) { return reportService.shipmentsByRoute(from, to); }
    public List driverPerformanceReport(Date from, Date to) { return reportService.driverPerformance(from, to); }
    public File generateRevenueExcel(Date from, Date to) { return reportService.generateRevenueExcel(from, to); }
    public File generateFleetExcel(Date from, Date to) { return reportService.generateFleetExcel(from, to); }
    public File generateArAgingPdf(Date asOf) { return reportService.generateArAgingPdf(asOf); }

    // ===== notification section =====

    public List recentNotifications(int limit) { return notificationDao.findRecent(limit); }
    public List undeliveredNotifications() { return notificationDao.findUndelivered(); }
    public void sendMail(String to, String subject, String body) {
        mailService.sendAsync(to, subject, body);
    }
    public void sendTemplatedMail(String code, String to, Map params) {
        mailService.sendByTemplate(code, to, params);
    }

    // ===== system config section =====

    public String configGet(String key) { return systemConfigService.get(key); }
    public String configGet(String key, String def) { return systemConfigService.getOrDefault(key, def); }
    public void configPut(String key, String value) { systemConfigService.put(key, value); }
    public List configList() { return systemConfigService.listAll(); }

    // ===== dashboard helpers (called from JSP scriptlets directly, sometimes) =====

    public Map dashboardCounts() {
        Map m = new HashMap();
        try {
            m.put("users", new Integer(userDao.countAll(User.class)));
            m.put("customers", new Integer(customerDao.countAll(Customer.class)));
            m.put("shipments", new Integer(shipmentDao.countAll(Shipment.class)));
            m.put("invoices", new Integer(invoiceDao.countAll(Invoice.class)));
            m.put("drivers", new Integer(driverDao.countAll(Driver.class)));
            m.put("vehicles", new Integer(vehicleDao.countAll(Vehicle.class)));
            m.put("warehouses", new Integer(warehouseDao.countAll(Warehouse.class)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    public Map cacheSnapshot() {
        return new HashMap(CACHE);
    }

    public void cachePut(String key, Object value) {
        CACHE.put(key, value); // unsynchronized access. don't fix.
    }

    public Object cacheGet(String key) {
        return CACHE.get(key);
    }

    public void cacheClear() {
        CACHE.clear();
    }

    // ===== filesystem helpers (yes, in the facade) =====

    public boolean ensureFilesystemDirs() {
        boolean ok = true;
        ok &= FileUtil.ensureDir(Constants.UPLOADS_DIR);
        ok &= FileUtil.ensureDir(Constants.REPORTS_DIR);
        ok &= FileUtil.ensureDir(Constants.LOGS_DIR);
        ok &= FileUtil.ensureDir(Constants.INBOUND_DIR);
        ok &= FileUtil.ensureDir(Constants.ARCHIVE_DIR);
        return ok;
    }

    public File defaultUploadDir() { return new File(Constants.UPLOADS_DIR); }
    public File defaultReportDir() { return new File(Constants.REPORTS_DIR); }
    public File defaultInboundDir() { return new File(Constants.INBOUND_DIR); }
    public File defaultArchiveDir() { return new File(Constants.ARCHIVE_DIR); }

    // ===== misc one-offs added over the years =====

    public String passwordHashFor(String plaintext) {
        return MD5Util.hash(plaintext);
    }

    public Date defaultDueDate() {
        return DateUtils.addDays(new Date(), 30);
    }

    public String formatMoney(BigDecimal v) { return Util.formatMoney(v); }
    public String formatDate(Date d) { return Util.formatDate(d); }
    public String formatDateTime(Date d) { return Util.formatDateTime(d); }

    public List shipmentsForDriverInLast(int days, Integer driverId) {
        Date from = DateUtils.addDays(new Date(), -days);
        List all = shipmentDao.findAll(Shipment.class);
        List out = new ArrayList();
        Iterator it = all.iterator();
        while (it.hasNext()) {
            Shipment s = (Shipment) it.next();
            if (driverId.equals(s.getDriverId()) && s.getBookedDate() != null && !s.getBookedDate().before(from)) {
                out.add(s);
            }
        }
        return out;
    }

    public List shipmentsForVehicleInLast(int days, Integer vehicleId) {
        Date from = DateUtils.addDays(new Date(), -days);
        List all = shipmentDao.findAll(Shipment.class);
        List out = new ArrayList();
        Iterator it = all.iterator();
        while (it.hasNext()) {
            Shipment s = (Shipment) it.next();
            if (vehicleId.equals(s.getVehicleId()) && s.getBookedDate() != null && !s.getBookedDate().before(from)) {
                out.add(s);
            }
        }
        return out;
    }

    public BigDecimal totalOutstandingForCustomer(Integer customerId) {
        BigDecimal sum = BigDecimal.ZERO;
        List opens = invoiceDao.findOpenForCustomer(customerId);
        Iterator it = opens.iterator();
        while (it.hasNext()) {
            Invoice i = (Invoice) it.next();
            sum = sum.add(i.getOutstanding() == null ? BigDecimal.ZERO : i.getOutstanding());
        }
        return sum;
    }

    public boolean hasOverdueDebt(Integer customerId) {
        List opens = invoiceDao.findOpenForCustomer(customerId);
        Date now = new Date();
        Iterator it = opens.iterator();
        while (it.hasNext()) {
            Invoice i = (Invoice) it.next();
            if (i.getDueDate() != null && i.getDueDate().before(now)) return true;
        }
        return false;
    }

    public synchronized void recordNotification(Integer userId, String subject, String body) {
        Notification n = new Notification();
        n.setUserId(userId);
        n.setSubject(subject);
        n.setBody(body);
        n.setSentAt(new Date());
        n.setDeliveredFlag("Y");
        try { notificationDao.save(n); } catch (Exception e) { e.printStackTrace(); }
    }

    public void auditAction(String username, String action, String entityType, String entityId, String details) {
        auditService.log(username, action, entityType, entityId, details);
    }

    public List recentAudits(int limit) { return auditService.recent(limit); }
}
