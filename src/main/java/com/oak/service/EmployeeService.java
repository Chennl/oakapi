package com.oak.service;

import com.oak.model.Employee;

import java.util.List;

/**
 * Created by Chennl on 6/4/2017.
 */
public interface EmployeeService {
     List<Employee> getAllEmployees();
     Employee getEmployeeById(long id);
     void addEmployee(Employee user);
     void updateEmployee(Employee user);
     void deleteEmployee(long userId);
     boolean isEmployeeExist(Employee user);
}
