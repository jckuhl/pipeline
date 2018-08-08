package com.revature.Database;

import java.util.List;

import com.revature.exceptions.LoginException;
import com.revature.model.Employee;

public interface LoginDAOInterface {

	public Employee getEmployeeByLogin(String username, String password);
	public void setEmployeeLogin(Employee employee, String username, String password) throws LoginException;
	public List<String> getAllUsernames();
	public List<Integer> getAllLoginOwners();
	public void updateEmployeeLogin(Employee employee, LoginField field, String newVal) throws LoginException;
	public void deleteEmployeeLogin(Employee employee);
	
}
