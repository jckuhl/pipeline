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
import com.revature.model.Approval;
import com.revature.model.Employee;
import com.revature.model.Expense;

public class ExpensesDAO implements ExpensesDAOInterface{
	final static Logger log = Logger.getLogger(ExpensesDAO.class);

	private static ExpensesDAO instance;
	
	private ExpensesDAO() {
	
	}
	
	public static ExpensesDAO getExpensesDAO() {
		instance = instance == null ? new ExpensesDAO() : instance;
		return instance;
	}

	public void submitExpense(Expense expense) {
		try {
			String sql = "CALL add_expense(?,?,?,?,?,?,?)";
			
			Connection conn = JDBCConnection.getConnection();
			CallableStatement cs = conn.prepareCall(sql);
			cs.setString(1, expense.getProvider());
			cs.setString(2, expense.getDate());
			cs.setString(3, expense.getReason());
			cs.setDouble(4, expense.getAmount());
			cs.setInt(5, expense.getApproval().getApproval());
			cs.setInt(6, expense.getOwner().getUid());
			cs.setString(7, expense.getApprovingManager());
			
			cs.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	public Expense getExpense(Employee employee, Expense expense) {
		List<Expense> expenses = this.getAllRequestorsExpenses(employee);
		for(Expense exp: expenses) {
			if(exp.getEid() == expense.getEid()) {
				return exp;
			}
		}
		return null;
	}

	public List<Expense> getAllRequestorsExpenses(Employee employee) {
		try {
			String sql = "SELECT * FROM expense WHERE owner = ?";
			
			Connection conn = JDBCConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, employee.getUid());
			
			ResultSet rs = ps.executeQuery();
			
			List<Expense> expenses = new ArrayList<Expense>();
			while(rs.next()) {
				expenses.add(new Expense(
						rs.getInt("e_id"),
						rs.getString("provider"),
						rs.getString("expense_date"),
						rs.getDouble("amount"),
						rs.getString("reason"),
						Approval.getApprovalLevel(rs.getInt("approval")),
						rs.getString("approvingmanager"),
						employee
				));
			}
			return expenses;
			
		} catch(SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}

	public List<Expense> getAllExpenses() {
		EmployeeDataService eds = EmployeeDataService.getEmployeeDataService();
		List<Employee> employees = eds.getAllEmployees();
		List<Expense> allExpenses = new ArrayList<Expense>();
		
		for(Employee employee : employees) {
			List<Expense> expenses = this.getAllRequestorsExpenses(employee);
			if(expenses != null && expenses.size() != 0) {
				allExpenses.addAll(expenses);
			}
		}
		
		return allExpenses;
	}

	public void resolveExpense(int eid, Approval approval, String approvingManager) {
		try {
			String sql = "UPDATE expense SET approval = ?, approvingmanager = ? WHERE e_id = ?";
			
			Connection conn = JDBCConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, approval.getApproval());
			ps.setString(2, approvingManager);
			ps.setInt(3, eid);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	public void deleteExpense(Expense expense) {
		try {
			String sql = "DELETE FROM expense WHERE e_id = ?";
			
			Connection conn = JDBCConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,expense.getEid());
			
			ps.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	
	
}
