package com.revature.Database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.Connection.JDBCConnection;
import com.revature.exceptions.LoginException;
import com.revature.model.Employee;
import com.revature.model.Position;

public class LoginDAO implements LoginDAOInterface {
	final static Logger log = Logger.getLogger(LoginDAO.class);
	
	private static LoginDAO instance;
	
	private LoginDAO() {
		
	}
	
	public static LoginDAO getLoginDAO() {
		instance = instance == null ? new LoginDAO() : instance;
		return instance;
	}

	public Employee getEmployeeByLogin(String username, String password) {
		try {
			String sql = "SELECT * FROM employees INNER JOIN login ON u_id = owner WHERE login.username = ? AND login.password = ?";
			
			Connection conn = JDBCConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				return new Employee(
						rs.getInt("u_id"),
						rs.getString("fname"),
						rs.getString("lname"), 
						rs.getString("employeeid"), 
						Position.fromString(rs.getString("position")), 
						rs.getString("street"), 
						rs.getString("city"), 
						rs.getString("state"), 
						rs.getString("zip"), 
						rs.getString("phone"), 
						rs.getString("email"), 
						rs.getInt("manager")
				);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}

	public void setEmployeeLogin(Employee employee, String username, String password) throws LoginException {
		try {
			
			String sql = "CALL add_login(?,?,?)";
			
			List<Integer> owners = this.getAllLoginOwners();
			if(owners != null &&  owners.contains(employee.getUid())) {
				throw new LoginException();
			}
			
			Connection conn = JDBCConnection.getConnection();
			CallableStatement cs = conn.prepareCall(sql);
			cs.setString(1, username);
			cs.setString(2, password);
			cs.setInt(3, employee.getUid());
			
			cs.executeUpdate();
			
		} catch(SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public List<String> getAllUsernames() {
		try {
			String sql = "SELECT username FROM login";
			
			Connection conn = JDBCConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			List<String> usernames = new ArrayList<String>();
			
			while(rs.next()) {
				usernames.add(rs.getString("username"));
			}
			
			return usernames;
			
			
		} catch(SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Returns a list of EmployeeIds for all employees with a log in
	 * Used to verify that the employee doesn't already have a log in
	 * before creating a new one.
	 */
	public List<Integer> getAllLoginOwners() {
		try {
			String sql = "Select owner FROM login";
			
			Connection conn = JDBCConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Integer> owners = new ArrayList<Integer>();
			
			while(rs.next()) {
				owners.add(rs.getInt("owner"));
			}
			
			return owners;
		} catch(SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
		
		return null;
	}

	public void updateEmployeeLogin(Employee employee, LoginField field, String newVal) throws LoginException {
		try {
			if(field.equals(LoginField.USERNAME)) {
				List<String> usernames = this.getAllUsernames();
				if(usernames.contains(newVal)) {
					throw new LoginException("Username is already taken!");
				}
			}
			
			
			String sql = String.format("UPDATE login SET %s = ? WHERE owner = ?",
					field.getField()
			);
			
			Connection conn = JDBCConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, newVal);
			ps.setInt(2, employee.getUid());
			
			ps.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
	}

	public void deleteEmployeeLogin(Employee employee) {
		try {
			String sql = "DELETE FROM login WHERE owner = ?";
			
			Connection conn = JDBCConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, employee.getUid());
			
			ps.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	public String getUserName(Employee employee) {
		try {
			String sql = "SELECT username FROM employees INNER JOIN login ON u_id = owner WHERE employeeId = ?";
			
			Connection conn = JDBCConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, employee.getEmployeeId());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return rs.getString("username");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}

}
