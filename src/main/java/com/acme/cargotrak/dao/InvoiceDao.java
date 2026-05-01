package com.acme.cargotrak.dao;

import java.util.Date;
import java.util.List;

import com.acme.cargotrak.domain.Invoice;

public interface InvoiceDao extends BaseDao {

    Invoice findByInvoiceNo(String invoiceNo);

    List search(String invoiceFragment, Integer customerId, String status,
                Date fromDate, Date toDate, int firstResult, int maxResults);

    int countSearch(String invoiceFragment, Integer customerId, String status,
                    Date fromDate, Date toDate);

    List findOpenForCustomer(Integer customerId);

    List findOverdueAsOf(Date asOf);

    void applyPayment(Integer invoiceId, java.math.BigDecimal amount);
}
