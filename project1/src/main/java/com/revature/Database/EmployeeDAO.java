package com.revature.Database;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.Connection.JDBCConnection;
import com.revature.exceptions.EmployeeException;
import com.revature.model.Employee;
import com.revature.model.Position;

/**
 * Data Access Object for Employee table
 * @author jonathankuhl
 *
 */
public class EmployeeDAO implements EmployeeDAOInterface {
	final static Logger log = Logger.getLogger(EmployeeDAO.class);
	
	private static EmployeeDAO instance;
	
	private EmployeeDAO() {
		
	}
	
	public static EmployeeDAO getEmployeeDAO() {
		instance = instance == null ? new EmployeeDAO() : instance;
		return instance;
	}

	public void createEmployee(Employee employee) throws EmployeeException {
		try {
			String sql = "CALL add_employee(?,?,?,?,?,?,?,?,?,?)";
			
			List<String> employeeIds = this.getEmployeeIds();
			if(employeeIds.contains(employee.getEmployeeId())) {
				throw new EmployeeException();
			}
			
			Connection conn = JDBCConnection.getConnection();
			if(conn == null) {
				log.warn("Problem connectiong to Oracle");
				return;
			}
			CallableStatement cs = conn.prepareCall(sql);
			
			int i = 0;
			cs.setString(++i, employee.getFname());
			cs.setString(++i, employee.getLname());
			cs.setString(++i, employee.getPosition().getPosition());
			cs.setString(++i, employee.getEmployeeId());
			cs.setString(++i, employee.getStreet());
			cs.setString(++i, employee.getCity());
			cs.setString(++i, employee.getState());
			cs.setString(++i, employee.getZip());
			cs.setString(++i, employee.getPhone());
			cs.setString(++i, employee.getEmail());
			
			cs.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
			log.error(e.getCause());
			log.error(e.getMessage());
		}
	}

	public List<String> getEmployeeIds() {
		List<Employee> employees = this.getAllEmployees();
		if(employees == null) {
			log.warn("Null employees, check Oracle connection");
			return null;
		}
		List<String> employeeIds = new ArrayList<String>();
		
		for(Employee emp: employees) {
			employeeIds.add(emp.getEmployeeId());
		}
		
		return employeeIds;
	}
	

	public Employee getEmployeeById(int id) {
		try {
			String sql = String.format("SELECT * FROM employees WHERE u_id = ?");
			
			Connection conn = JDBCConnection.getConnection();
			if(conn == null) {
				log.warn("Problem connectiong to Oracle");
				return null;
			}
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
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
			
			
		} catch(SQLException e) {
			e.printStackTrace();
			log.error(e.getCause());
			log.error(e.getMessage());
		}
		return null;
	}
	
	public Employee getEmployeeByIdAndName(String employeeId, String fname, String lname) {
		try {
			String sql = String.format("SELECT * FROM employees WHERE employeeId = ? AND fname = ? AND lname = ?");
			
			Connection conn = JDBCConnection.getConnection();
			if(conn == null) {
				log.warn("Problem connectiong to Oracle");
				return null;
			}
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, employeeId);
			ps.setString(2, fname);
			ps.setString(3, lname);
			
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
			
			
		} catch(SQLException e) {
			e.printStackTrace();
			log.error(e.getCause());
			log.error(e.getMessage());
		}
		
		return null;
	}
	
	public Employee getEmployeeById(String employeeId) {
		try {
			String sql = String.format("SELECT * FROM employees WHERE employeeId = ?");
			
			Connection conn = JDBCConnection.getConnection();
			if(conn == null) {
				log.warn("Problem connectiong to Oracle");
				return null;
			}
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, employeeId);
			
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
			
			
		} catch(SQLException e) {
			e.printStackTrace();
			log.error(e.getCause());
			log.error(e.getMessage());
		}
		return null;
	}


	public List<Employee> getAllEmployees() {
		try {
			String sql = "SELECT * FROM employees";
			
			Connection conn = JDBCConnection.getConnection();
			if(conn == null) {
				log.warn("Problem connectiong to Oracle");
				return null;
			}
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			List<Employee> employees = new ArrayList<Employee>();
			
			while(rs.next()) {
				employees.add(new Employee(
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
				));
			}
			
			return employees;
			
		} catch(SQLException e) {
			e.printStackTrace();
			log.error(e.getCause());
			log.error(e.getMessage());
		}
		return null;
	}

	public void updateEmployee(Employee employee, EmpField field, String newVal) {
		try {
			String sql = String.format("UPDATE employees SET %s = ? WHERE employeeid = ?", 
					field.getField()
			);
			
			Connection conn = JDBCConnection.getConnection();
			if(conn == null) {
				log.warn("Problem connectiong to Oracle");
			}
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, newVal);
			ps.setString(2, employee.getEmployeeId());
			ps.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
			log.error(e.getCause());
			log.error(e.getMessage());
		}
	}
	
	public void updateEmployee(Employee employee, Position newPos) {
		try {
			String sql = "UPDATE employees SET position = ? WHERE employeeid = ?";
			
			Connection conn = JDBCConnection.getConnection();
			if(conn == null) {
				log.warn("Problem connectiong to Oracle");
			}
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, newPos.getPosition());
			ps.setString(2, employee.getEmployeeId());
			ps.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
			log.error(e.getCause());
			log.error(e.getMessage());
		}
	}

	public void deleteEmployee(Employee employee) {
		try {
			String sql = "DELETE FROM employees WHERE employeeid = ?";
			
			Connection conn = JDBCConnection.getConnection();
			if(conn == null) {
				log.warn("Problem connectiong to Oracle");
			}
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, employee.getEmployeeId());
			ps.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
			log.error(e.getCause());
			log.error(e.getMessage());
			
		}
	}
}
