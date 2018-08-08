package com.revature.Database;

import com.revature.exceptions.EmployeeException;
import com.revature.model.Employee;
import com.revature.model.Position;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class EmployeeDataService {

	final static Logger log = Logger.getLogger(EmployeeDataService.class);
	private static EmployeeDataService instance;
	private EmployeeDAO employeeDAO;
	
	private EmployeeDataService() {
		this.employeeDAO = EmployeeDAO.getEmployeeDAO();
	}
	
	public static EmployeeDataService getEmployeeDataService() {
		instance = instance == null ? new EmployeeDataService() : instance;
		return instance;
	}
	
	public void createEmployee(Employee employee) {
		try {
			this.employeeDAO.createEmployee(employee);
		} catch (EmployeeException e) {
			log.warn(e.getMessage());
		}
	}
	
	public List<String> getEmployeeIds() {
		return this.employeeDAO.getEmployeeIds();
	}
	
	public Employee getEmployeeById(Employee employee) {
		if(employee.getUid() != 0) {
			return this.employeeDAO.getEmployeeById(employee.getUid());
		} else {
			return this.employeeDAO.getEmployeeById(employee.getEmployeeId());
		}
	}
	
	public Employee getEmployeeById(String employeeid) {
		return this.employeeDAO.getEmployeeById(employeeid);
	}
	
	public List<Employee> getAllEmployees() {
		return this.employeeDAO.getAllEmployees();
	}
	
	public List<Employee> getAllManagers() {
		ArrayList<Employee> managers = new ArrayList<Employee>();
		List<Employee> employees = this.getAllEmployees();
		for(Employee employee: employees) {
			if(employee.isManager()) {
				managers.add(employee);
			}
		}
		return managers;
	}
	
	public void updateEmployee(Employee employee, EmpField field, String newVal) {
		this.employeeDAO.updateEmployee(employee, field, newVal);
	}
	
	public void updateEmployee(Employee employee, Position position) {
		this.employeeDAO.updateEmployee(employee, position);
	}
	
	public void deleteEmployee(Employee employee) {
		this.employeeDAO.deleteEmployee(employee);
	}
}
