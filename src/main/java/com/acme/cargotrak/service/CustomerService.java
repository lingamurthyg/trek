package com.acme.cargotrak.service;

import java.util.Date;
import java.util.List;

import com.acme.cargotrak.dao.CustomerAddressDao;
import com.acme.cargotrak.dao.CustomerContactDao;
import com.acme.cargotrak.dao.CustomerDao;
import com.acme.cargotrak.domain.Customer;
import com.acme.cargotrak.domain.CustomerAddress;
import com.acme.cargotrak.domain.CustomerContact;

public class CustomerService {

    private CustomerDao customerDao;
    private CustomerContactDao customerContactDao;
    private CustomerAddressDao customerAddressDao;

    public void setCustomerDao(CustomerDao customerDao) { this.customerDao = customerDao; }
    public void setCustomerContactDao(CustomerContactDao d) { this.customerContactDao = d; }
    public void setCustomerAddressDao(CustomerAddressDao d) { this.customerAddressDao = d; }

    public Customer findById(Integer id) { return (Customer) customerDao.findById(Customer.class, id); }
    public Customer findByCode(String code) { return customerDao.findByCode(code); }

    public List search(String namePart, String industry, String activeFlag, int page, int pageSize) {
        return customerDao.search(namePart, industry, activeFlag, page * pageSize, pageSize);
    }

    public int countSearch(String namePart, String industry, String activeFlag) {
        return customerDao.countSearch(namePart, industry, activeFlag);
    }

    public List listActive() { return customerDao.findActive(); }

    public Integer createCustomer(Customer c) {
        if (c.getCreatedDate() == null) c.setCreatedDate(new Date());
        if (c.getActiveFlag() == null) c.setActiveFlag("Y");
        return (Integer) customerDao.save(c);
    }

    public void updateCustomer(Customer c) { customerDao.update(c); }

    public void deactivate(Integer customerId) {
        Customer c = findById(customerId);
        if (c == null) return;
        c.setActiveFlag("N");
        customerDao.update(c);
    }

    public List listContacts(Integer customerId) { return customerContactDao.findByCustomer(customerId); }
    public List listAddresses(Integer customerId) { return customerAddressDao.findByCustomer(customerId); }

    public Integer addContact(Integer customerId, CustomerContact contact) {
        Customer c = findById(customerId);
        if (c == null) return null;
        contact.setCustomer(c);
        return (Integer) customerContactDao.save(contact);
    }

    public Integer addAddress(Integer customerId, CustomerAddress address) {
        Customer c = findById(customerId);
        if (c == null) return null;
        address.setCustomer(c);
        return (Integer) customerAddressDao.save(address);
    }
}
