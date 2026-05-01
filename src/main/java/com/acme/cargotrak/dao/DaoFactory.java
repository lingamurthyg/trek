package com.acme.cargotrak.dao;

import org.springframework.context.ApplicationContext;

import com.acme.cargotrak.util.SpringContextHolder;

/**
 * Singleton DAO factory.
 *
 * @author Rajesh Kumar 2008-12
 *
 * Some code uses Spring injection, some uses DaoFactory.getInstance().
 * Both styles coexist. miyer 2010 said "we'll standardise next quarter".
 */
public class DaoFactory {

    private static final DaoFactory INSTANCE = new DaoFactory();

    private DaoFactory() {}

    public static DaoFactory getInstance() { return INSTANCE; }

    private Object lookup(String beanName) {
        ApplicationContext ctx = SpringContextHolder.getContext();
        if (ctx == null) {
            throw new IllegalStateException("Spring context not initialised yet (DaoFactory called too early?)");
        }
        return ctx.getBean(beanName);
    }

    public UserDao getUserDao() { return (UserDao) lookup("userDao"); }
    public RoleDao getRoleDao() { return (RoleDao) lookup("roleDao"); }
    public PermissionDao getPermissionDao() { return (PermissionDao) lookup("permissionDao"); }
    public LoginHistoryDao getLoginHistoryDao() { return (LoginHistoryDao) lookup("loginHistoryDao"); }
    public AuditLogDao getAuditLogDao() { return (AuditLogDao) lookup("auditLogDao"); }
    public CustomerDao getCustomerDao() { return (CustomerDao) lookup("customerDao"); }
    public CustomerContactDao getCustomerContactDao() { return (CustomerContactDao) lookup("customerContactDao"); }
    public CustomerAddressDao getCustomerAddressDao() { return (CustomerAddressDao) lookup("customerAddressDao"); }
    public DriverDao getDriverDao() { return (DriverDao) lookup("driverDao"); }
    public VehicleDao getVehicleDao() { return (VehicleDao) lookup("vehicleDao"); }
    public VehicleMaintenanceDao getVehicleMaintenanceDao() { return (VehicleMaintenanceDao) lookup("vehicleMaintenanceDao"); }
    public WarehouseDao getWarehouseDao() { return (WarehouseDao) lookup("warehouseDao"); }
    public WarehouseZoneDao getWarehouseZoneDao() { return (WarehouseZoneDao) lookup("warehouseZoneDao"); }
    public InventoryDao getInventoryDao() { return (InventoryDao) lookup("inventoryDao"); }
    public RouteDao getRouteDao() { return (RouteDao) lookup("routeDao"); }
    public RateCardDao getRateCardDao() { return (RateCardDao) lookup("rateCardDao"); }
    public ShipmentDao getShipmentDao() { return (ShipmentDao) lookup("shipmentDao"); }
    public ShipmentLegDao getShipmentLegDao() { return (ShipmentLegDao) lookup("shipmentLegDao"); }
    public ShipmentStatusHistoryDao getShipmentStatusHistoryDao() { return (ShipmentStatusHistoryDao) lookup("shipmentStatusHistoryDao"); }
    public ShipmentDocumentDao getShipmentDocumentDao() { return (ShipmentDocumentDao) lookup("shipmentDocumentDao"); }
    public InvoiceDao getInvoiceDao() { return (InvoiceDao) lookup("invoiceDao"); }
    public PaymentDao getPaymentDao() { return (PaymentDao) lookup("paymentDao"); }
    public PaymentAllocationDao getPaymentAllocationDao() { return (PaymentAllocationDao) lookup("paymentAllocationDao"); }
    public SystemConfigDao getSystemConfigDao() { return (SystemConfigDao) lookup("systemConfigDao"); }
    public NotificationDao getNotificationDao() { return (NotificationDao) lookup("notificationDao"); }
    public EmailTemplateDao getEmailTemplateDao() { return (EmailTemplateDao) lookup("emailTemplateDao"); }
    public ScheduledJobInfoDao getScheduledJobInfoDao() { return (ScheduledJobInfoDao) lookup("scheduledJobInfoDao"); }
    public ReportDao getReportDao() { return (ReportDao) lookup("reportDao"); }
}
