package com.acme.cargotrak.dao;

import java.util.Date;
import java.util.List;

import com.acme.cargotrak.domain.Shipment;

public interface ShipmentDao extends BaseDao {

    Shipment findByTrackingNo(String trackingNo);

    List search(String trackingFragment, Integer customerId, String status,
                Date fromDate, Date toDate, int firstResult, int maxResults);

    int countSearch(String trackingFragment, Integer customerId, String status,
                    Date fromDate, Date toDate);

    List findByCustomer(Integer customerId);

    List findByStatus(String status);

    List findUninvoicedDelivered();

    int updateStatus(Integer shipmentId, String newStatus, String changedBy);
}
