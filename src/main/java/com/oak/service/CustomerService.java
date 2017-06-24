package com.oak.service;

import com.oak.entity.Customer;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

/**
 * Created by Chennl on 6/23/2017.
 */
public interface CustomerService {
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    List<Customer> getAllCustomers();
    @Secured ({"ROLE_ADMIN", "ROLE_USER"})
    Customer getCustomerById(String customerId);
    @Secured ({"ROLE_ADMIN"})
    void addCustomer(Customer customer);
    @Secured ({"ROLE_ADMIN"})
    void updateCustomer(Customer customer);
    @Secured ({"ROLE_ADMIN"})
    void deleteCustomer(String customerId);
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    boolean customerExist(Customer customer);

}
