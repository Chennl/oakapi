package com.oak.service.impl;

import com.oak.dao.EmployeeDAO;
import com.oak.entity.Employee;
import com.oak.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Chennl on 6/4/2017.
 * @ClassName EmployeeServiceImpl
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;


    @Override
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }
    @Override
    public Employee getEmployeeById(long id) {
        Employee employee = employeeDAO.getEmployeeById(id);
        return employee;
    }
    @Override
    public synchronized void addEmployee(Employee employee) {
            employeeDAO.addEmployee(employee);

    }

    @Override
    public void updateEmployee(Employee employee) {
        employeeDAO.updateEmployee(employee);
    }

    @Override
    public void deleteEmployee(long id) {
        employeeDAO.deleteEmployee(id);
    }

    @Override
    public boolean isEmployeeExist(Employee employee){
        return employeeDAO.isEmployeeExist(employee.getName());
    };
}
