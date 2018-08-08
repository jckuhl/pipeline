package com.revature.Database;

import com.revature.exceptions.EmployeeException;
import com.revature.model.Employee;
import com.revature.model.Position;

import java.util.List;

public interface EmployeeDAOInterface {

	public void createEmployee(Employee employee) throws EmployeeException;
	public List<String> getEmployeeIds();
	public Employee getEmployeeById(String employeeId);	//lol JavaScript reference
	public Employee getEmployeeById(int uid);
	public List<Employee> getAllEmployees();
	public void updateEmployee(Employee employee, EmpField field, String newVal);
	public void updateEmployee(Employee employee, Position newPos);
	public void deleteEmployee(Employee employee);
}
