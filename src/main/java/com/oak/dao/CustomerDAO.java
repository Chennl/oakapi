package com.oak.dao;

import com.oak.entity.Customer;

import java.util.List;

/**
 * Created by Chennl on 6/23/2017.
 */
public interface CustomerDAO {
    List<Customer> getAllCustomers();
    Customer getCustomerById(String customerId);
    void addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(String customerId);
    boolean customerExists(String customerName, String address);

}
