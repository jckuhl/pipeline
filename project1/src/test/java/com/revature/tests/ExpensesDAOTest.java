package com.revature.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.revature.Database.EmployeeDataService;
import com.revature.Database.ExpensesService;
import com.revature.model.Employee;
import com.revature.model.Expense;

public class ExpensesDAOTest {

	public static ExpensesService es = ExpensesService.getExpensesService();
	public Employee mscott = EmployeeDataService.getEmployeeDataService().getEmployeeById("789261");
	
	@Test
	public void getAllExpensesGetsExpenses() {
		List<Expense> expenses = es.getAllExpenses();
		assertNotEquals(0, expenses.size());
		assertNotNull(expenses);
		for(Expense expense : expenses) {
			assertTrue(expense instanceof Expense);
		}
	}
	

}
