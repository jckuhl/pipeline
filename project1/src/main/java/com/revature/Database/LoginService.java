package com.revature.Database;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.exceptions.LoginException;
import com.revature.model.Employee;

public class LoginService {

	final static Logger log = Logger.getLogger(LoginService.class);
	
	private static LoginService instance;
	private LoginDAO loginDAO;
	
	private LoginService() {
		this.loginDAO = LoginDAO.getLoginDAO();
	}
	
	public static LoginService getLoginService() {
		instance = instance == null ? new LoginService() : instance;
		return instance;
	}
	
	public Employee getEmployeeByLogin(String username, String password) {
		return this.loginDAO.getEmployeeByLogin(username, password);
	}
	
	public void setEmployeeLogin(Employee employee, String username, String password) {
		try {
			this.loginDAO.setEmployeeLogin(employee, username, password);
		} catch (LoginException e) {
			log.warn(e.getMessage());
		}
	}
	
	public List<String> getAllUserNames() {
		return this.loginDAO.getAllUsernames();
	}
	
	public List<Integer> getAllLoginOwners() {
		return this.loginDAO.getAllLoginOwners();
	}
	
	public void updateLogin(Employee employee, LoginField field, String newVal) {
		try {
			this.loginDAO.updateEmployeeLogin(employee, field, newVal);
		} catch (LoginException e) {
			System.out.println(e.getMessage());
			log.warn(e.getMessage());
		}
	}
	
	public void deleteEmployeeLogin(Employee employee) {
		this.loginDAO.deleteEmployeeLogin(employee);
	}

	public String getUserName(Employee employee) {
		return this.loginDAO.getUserName(employee);
	}
	
}
