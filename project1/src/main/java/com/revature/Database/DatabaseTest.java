package com.revature.Database;

import java.sql.Connection;
import java.util.List;

import com.revature.Connection.JDBCConnection;
import com.revature.model.Employee;
import com.revature.model.Expense;
import com.revature.model.Position;

public class DatabaseTest {
	
	public static EmployeeDataService eds = EmployeeDataService.getEmployeeDataService();
	public static LoginService ls = LoginService.getLoginService();
	public static ExpensesService es = ExpensesService.getExpensesService();
	
	public static void main(String[] args) {
		Connection conn = JDBCConnection.getConnection();
		System.out.println(conn);
		
//('Dwight', 'Schrute', 'Assistant To The Regional Manager', '000000', '93 Oak Street', 'Scranton', 'PA', '18350', '5559381272','d.schrute@dundermifflin.com');

		
//		Employee mscott = new Employee(46, 
//				"Michael", "Scott", 
//				"789261", 
//				Position.MANAGER, 
//				"123 Any Street", 
//				"Scranton", 
//				"PA", "18350", 
//				"5558675309",
//				"m.scott@dundermifflin.com", 
//				1
//		);
		

//('David', 'Wallace', 'Regional Manager', '432821', '101 Wally Way', 'Scranton', 'PA', '18350', '5553291828','d.wallace@dundermifflin.com');
		
		Employee dwallace = new Employee(47,
				"David", "Wallace",
				"432821",
				Position.MANAGER,
				"101 Wally Way",
				"Scranton",
				"PA",
				"18350",
				"5553291828",
				"d.wallace@dundermifflin.com",
				1
		);
		
		Employee hflax = new Employee(48,
				"Holly", "Flax",
				"238912",
				Position.HR,
				"456 Some Lane",
				"Scranton",
				"PA",
				"18350",
				"5559381723",
				"h.flax@dundermifflin.com",
				0
		);
		
		Employee tflenderson = new Employee(58,
				"Tobias", "Flenderson",
				"392817",
				Position.HR,
				"89 Strangler Way",
				"Scranton",
				"PA",
				"18350",
				"5559182872",
				"t.flenderson@dundermifflin.com",
				0
		);
		
//		eds.createEmployee(mscott);
//		eds.createEmployee(dschrute);
//		eds.createEmployee(dwallace);
//		eds.createEmployee(hflax);
//		System.out.println(eds.getAllEmployees());
//		System.out.println(eds.getAllManagers());
		//Poor Michael, Holly has moved to Nashua!
//		eds.updateEmployee(hflax, EmpField.STREET, "98 University Drive");
//		eds.updateEmployee(hflax, EmpField.CITY, "Nashua");
//		eds.updateEmployee(hflax, EmpField.STATE, "NH");
//		eds.updateEmployee(hflax, EmpField.ZIP, "03063");
//		System.out.println(eds.getEmployeeById(hflax));
//		//Dwight made Michael angry, he's no longer Assistant to the Regional Manager
//		eds.updateEmployee(dschrute, Position.SALES);
//		System.out.println(eds.getEmployeeById(dschrute));
//		//but Dwight knows how to butter Michael up, so he's AttRM again
//		eds.updateEmployee(dschrute, Position.ASSISTANT);
//		System.out.println(eds.getEmployeeById(dschrute));
//		//Toby has replaced Holly, but Michael hates Toby
//		eds.createEmployee(tflenderson);
//		System.out.println(eds.getEmployeeById(tflenderson));
//		System.out.printf("%s %s: %s, you are the worst!\n", mscott.getFname(), mscott.getLname(), tflenderson.getFname() );
//		eds.deleteEmployee(tflenderson);
//		System.out.println(eds.getEmployeeById(tflenderson));
//		//However, only coroprate can fire Toby
//		eds.createEmployee(tflenderson);
//		System.out.println(eds.getEmployeeById(tflenderson));
//		Michael needs a password
		
//		Employee mscott = eds.getEmployeeById("789261");
//		System.out.println(mscott);
//		ls.setEmployeeLogin(mscott, "best boss", "passw0rd");
//		
//		Employee mscott = ls.getEmployeeByLogin("best boss", "passw0rd");
//		System.out.println(mscott);
//		
//		List<Expense> expenses = es.getAllExpenses();
//		
//		for(Expense expense: expenses) {
//			System.out.println(expense);
//		}
		
		Employee dschrute = eds.getEmployeeById("780281");
		String username = ls.getUserName(dschrute);
		System.out.println(username);
	}

}
