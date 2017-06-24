package com.oak.dao.impl;

import com.oak.dao.EmployeeDAO;
import com.oak.entity.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Chennl on 6/6/2017.
 */
@Transactional
@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Employee> getAllEmployees() {
        String hql = "FROM Employee as u ORDER BY u.id";
        return (List<Employee>) entityManager.createQuery(hql).getResultList();
    }

    @Override
    public Employee getEmployeeById(long id) {
        return entityManager.find(Employee.class,id);
    }

    @Override
    public void addEmployee(Employee employee) {
        entityManager.persist(employee);
    }

    @Override
    public void updateEmployee(Employee employee) {
        Employee usr = getEmployeeById(employee.getId());
        usr.setName(employee.getName());
        usr.setEmail(employee.getEmail());
        usr.setSex(employee.getSex());
        usr.setMobile(employee.getMobile());
        usr.setAge(employee.getAge());
        usr.setSalary(employee.getSalary());
        usr.setMobile(employee.getMobile());
        usr.setEnabled(employee.getEnabled());
        entityManager.flush();
    }

    @Override
    public void deleteEmployee(long employeeId) {
        entityManager.remove(getEmployeeById(employeeId));
    }

    @Override
    public boolean isEmployeeExist(String employeename) {
        String hql = "FROM Employee as u WHERE u.name = ? ";
        int count = entityManager.createQuery(hql).setParameter(1, employeename)
                .getResultList().size();
        return count > 0 ? true : false;
    }
}
