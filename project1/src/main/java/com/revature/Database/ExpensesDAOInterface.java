package com.revature.Database;

import java.util.List;

import com.revature.model.Approval;
import com.revature.model.Employee;
import com.revature.model.Expense;

public interface ExpensesDAOInterface {

	public void submitExpense(Expense expense);
	public Expense getExpense(Employee employee, Expense expense);
	public List<Expense> getAllRequestorsExpenses(Employee employee);
	public List<Expense> getAllExpenses();
	public void resolveExpense(int eid, Approval approval, String approvingManager);
	public void deleteExpense(Expense expense);
}
