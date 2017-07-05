package com.oak.controller;

import com.oak.model.Employee;
import com.oak.service.EmployeeService;
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
 * Created by Chennl on 6/4/2017.
 * class Name:UserController
 */
@RestController
@RequestMapping(value = "api")
public class EmployeeController {
    public static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeService employeeService;//Service which will do all data retrieval/manipulation work

    // -------------------Retrieve All Users---------------------------------------------
    @RequestMapping(value = "v1/employees", method = RequestMethod.GET)
    public ResponseEntity<?> getAllEmployees()
    {
        List<Employee> user = employeeService.getAllEmployees();
        if(user.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Employee>>(user,HttpStatus.OK);
    }
    @RequestMapping(value = "v2/employees", method = RequestMethod.GET)
    public ResponseEntity<?> getAllEmployeesV2()
    {
        List<Employee> user = employeeService.getAllEmployees();
        if(user.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Employee>>(user,HttpStatus.OK);
    }
    // -------------------Retrieve Single Employee------------------------------------------

    @RequestMapping(value="/employee/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") int id) {
        logger.info("Fetching Employee with id {}", id);
        Employee user= employeeService.getEmployeeById(id);
        if(user == null){
            logger.error("Employee with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Employee with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Employee>(user, HttpStatus.OK);
    }
    // ------------------- Update a Employee ------------------------------------------------
    @RequestMapping(value="/employee/{id}",method = RequestMethod.PUT)
    public ResponseEntity<?>  updateEmployee(@PathVariable("id") long id, @RequestBody Employee user) {
        logger.info("Updating Employee with id {}", id);
        Employee currentUser = employeeService.getEmployeeById(id);
        if(currentUser == null){
            logger.error("Unable to update. Employee with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. Employee with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        currentUser.setMobile(user.getMobile());
        currentUser.setEmail(user.getEmail());
        currentUser.setAge(user.getAge());
        currentUser.setSalary(user.getSalary());
        currentUser.setSex(user.getSex());
        currentUser.setName(user.getName());

        employeeService.updateEmployee(currentUser);
        return new ResponseEntity<Employee>(currentUser, HttpStatus.OK);

    }

    // -------------------Create a Employee-------------------------------------------

    @RequestMapping(value = "/employee/", method = RequestMethod.POST)
    public ResponseEntity<?> createEmployee(@RequestBody Employee user, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Employee : {}", user);

        if (employeeService.isEmployeeExist(user)) {
            logger.error("Unable to create. A Employee with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A Employee with name " +
                    user.getName() + " already exist."),HttpStatus.CONFLICT);
        }
        employeeService.addEmployee(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/employee/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    // ------------------- Delete a Employee-----------------------------------------

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting Employee with id {}", id);

        Employee user = employeeService.getEmployeeById(id);
        if (user == null) {
            logger.error("Unable to delete. Employee with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Employee with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        employeeService.deleteEmployee(id);
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }


}
