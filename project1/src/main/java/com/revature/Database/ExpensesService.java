package com.revature.Database;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.model.Approval;
import com.revature.model.Employee;
import com.revature.model.Expense;

public class ExpensesService {

	final static Logger log = Logger.getLogger(EmployeeDataService.class);
	private static ExpensesService instance;
	private ExpensesDAO expensesDao;
	
	private ExpensesService() {
		this.expensesDao = ExpensesDAO.getExpensesDAO();
	}
	
	public static ExpensesService getExpensesService() {
		instance = instance == null ? new ExpensesService() : instance;
		return instance;
	}
	
	public void submitExpense(Expense expense) {
		this.expensesDao.submitExpense(expense);
	}
	
	public Expense getExpense(Employee employee, Expense expense) {
		return this.expensesDao.getExpense(employee, expense);
	}
	
	public List<Expense> getAllRequestorsExpenses(Employee employee) {
		return this.expensesDao.getAllRequestorsExpenses(employee);
	}
	
	public List<Expense> getAllExpenses() {
		return this.expensesDao.getAllExpenses();
	}
	
	public void resolveExpense(int eid, Approval approval, String approvingManager) {
		this.expensesDao.resolveExpense(eid, approval, approvingManager);
	}
	
}
