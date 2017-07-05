package com.oak.dao;

import com.oak.model.Employee;

import java.util.List;

/**
 * Created by Chennl on 6/6/2017.
 */
public interface EmployeeDAO {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(long id);
    void addEmployee(Employee user);
    void updateEmployee(Employee user);
    void deleteEmployee(long id);
    boolean isEmployeeExist(String username);
}
