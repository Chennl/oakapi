package com.oak.dao.impl;

import com.oak.dao.CustomerDAO;
import com.oak.model.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Chennl on 6/23/2017.
 */
@Transactional
@Repository
public class CustomerDAOImpl implements CustomerDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Customer> getAllCustomers() {
        String hql = "FROM Customer as cust ORDER BY cust.customerId";
        return (List<Customer>) entityManager.createQuery(hql).getResultList();

    }

    @Override
    public Customer getCustomerById(String customerId) {
        return entityManager.find(Customer.class,customerId);
    }

    @Override
    public void addCustomer(Customer customer) {
        entityManager.persist(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        Customer cst = getCustomerById(customer.getCustomerId());
        cst.setCustomerName(customer.getCustomerName());
        cst.setAddress(customer.getAddress());
        entityManager.flush();
    }

    @Override
    public void deleteCustomer(String customerId) {
        entityManager.remove(getCustomerById(customerId));
    }

    @Override
    public boolean customerExists(String customerName, String address) {
        String hql="FROM Customer as cst WHERE cst.outletName = ? and cst.address = ?";
        int count = entityManager.createQuery(hql).setParameter(1, customerName)
                .setParameter(2, address).getResultList().size();
        return count > 0 ? true : false;
    }
}
