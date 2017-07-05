package com.oak.controller;

import com.oak.model.Customer;
import com.oak.model.Employee;
import com.oak.service.CustomerService;
import com.oak.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Created by Chennl on 6/23/2017.
 */
@RestController
@RequestMapping("api")
public class CustomerController {
    public static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    private CustomerService customerService;


    // -------------------Retrieve All Users---------------------------------------------
    @RequestMapping(value = "customers", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCustomers()
    {
        List<Customer> csts = customerService.getAllCustomers();
        if(csts.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Customer>>(csts,HttpStatus.OK);
    }

    @RequestMapping(value = "customer/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCustomerById(@PathVariable("id") String id){
        logger.info("Fetching Customer with id {}", id);
        Customer cst = customerService.getCustomerById(id);
        if(cst == null){
            logger.error("Customer with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Customer with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Customer>(cst, HttpStatus.OK);
    }


    @RequestMapping(value="/customer/{id}",method = RequestMethod.PUT)
    public ResponseEntity<?>  updateUser(@PathVariable("id") String id, @RequestBody Customer customer) {
        logger.info("Updating Customer with id {}", id);
        Customer currentCustomer = customerService.getCustomerById(id);
        if(currentCustomer == null){
            logger.error("Unable to update. Customer with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. Customer with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        currentCustomer.setCustomerName(customer.getCustomerName());
        currentCustomer.setAddress(customer.getAddress());
        currentCustomer.setCompanyCode(customer.getCompanyCode());
        customerService.updateCustomer(currentCustomer);
        return new ResponseEntity<Customer>(currentCustomer, HttpStatus.OK);

    }

    // -------------------Create a Employee-------------------------------------------

    @RequestMapping(value = "/customer/", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody Customer customer, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Customer : {}", customer);

        if (customerService.customerExist(customer)) {
            logger.error("Unable to create. A customer with name {} already exist", customer.getCustomerName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A customer with name " +
                    customer.getCustomerName() + " already exist."),HttpStatus.CONFLICT);
        }
        customerService.addCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/customer/{id}").buildAndExpand(customer.getCustomerId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    // ------------------- Delete a Employee-----------------------------------------

    @RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Customer with id {}", id);

        Customer customer = customerService.getCustomerById(id);
        if (customer == null) {
            logger.error("Unable to delete. Customer with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Customer with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        customerService.deleteCustomer(id);
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }
}
