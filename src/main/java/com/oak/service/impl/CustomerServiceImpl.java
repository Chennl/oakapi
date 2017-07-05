package com.oak.service.impl;

import com.oak.dao.CustomerDAO;
import com.oak.model.Customer;
import com.oak.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Chennl on 6/23/2017.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDAO customerDAO;

    @Override
    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    @Override
    public Customer getCustomerById(String customerId) {
        Customer obj = customerDAO.getCustomerById(customerId);
        return obj;
    }

    @Override
    public synchronized void addCustomer(Customer customer) {

            customerDAO.addCustomer(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerDAO.updateCustomer(customer);
    }

    @Override
    public void deleteCustomer(String customerId) {
        customerDAO.deleteCustomer(customerId);
    }

    @Override
    public boolean customerExist(Customer customer){
        return customerDAO.customerExists(customer.getCustomerName(),customer.getAddress());
    };
}
