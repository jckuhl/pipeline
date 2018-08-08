package com.revature.tests;

import org.junit.Test;

import com.revature.Connection.JDBCConnection;
import com.revature.Database.EmployeeDataService;
import com.revature.model.Employee;
import com.revature.model.Position;

import java.util.List;

import junit.framework.TestCase;

public class EmployeeDAOTest extends TestCase {

	public static EmployeeDataService eds = EmployeeDataService.getEmployeeDataService();
	
	@Test
	public void testConnection() {
		assertNotNull(JDBCConnection.getConnection());
	}
	
	@Test
	public void testGettingEmployeeReturnsEmployee() {
		Employee mscott = new Employee(0, 
				"Michael", "Scott", 
				"789261", 
				Position.MANAGER, 
				"123 Any Street", 
				"Scranton", 
				"PA", "18350", 
				"5558675309",
				"m.scott@dundermifflin.com", 
				1
		);
		Employee employee = eds.getEmployeeById(mscott);
		assertTrue(eds.getEmployeeById(employee) instanceof Employee);
	}
	
	@Test
	public void testGetAllEmployeesReturnsList() {
		assertTrue(eds.getAllEmployees() instanceof List<?>);
		assertTrue(eds.getAllEmployees().get(0) instanceof Employee);
	}
	
	@Test
	public void testThatMichaelScottIsInFactMichaelScott() {
		Employee mscott = new Employee(0, 
				"Michael", "Scott", 
				"789261", 
				Position.MANAGER, 
				"123 Any Street", 
				"Scranton", 
				"PA", "18350", 
				"5558675309",
				"m.scott@dundermifflin.com", 
				1
		);
		Employee employee = eds.getEmployeeById(mscott);
		
		assertEquals(46, employee.getUid());
		assertEquals("Michael", employee.getFname());
		assertEquals("Scott", employee.getLname());
		assertEquals("Regional Manager", employee.getPosition().getPosition());
		assertEquals("789261", employee.getEmployeeId());
		assertEquals("123 Any Street", employee.getStreet());
		assertEquals("Scranton", employee.getCity());
		assertEquals("PA", employee.getState());
		assertEquals("18503", employee.getZip());
		assertEquals("5558675309", employee.getPhone());
		assertEquals("m.scott@dundermifflin.com", employee.getEmail());
	}
	
	@Test
	public void testThatMichaelScottIsInFactManager() {
		Employee mscott = new Employee(0, 
				"Michael", "Scott", 
				"789261", 
				Position.MANAGER, 
				"123 Any Street", 
				"Scranton", 
				"PA", "18350", 
				"5558675309",
				"m.scott@dundermifflin.com", 
				1
		);
		Employee employee = eds.getEmployeeById(mscott);
		assertTrue(employee.isManager());
	}
	
	@Test
	public void testThatDwightSchruteIsNOTtheManager() {
		Employee dschrute = new Employee(0,
				"Dwight", "Schrute",
				"780281",
				Position.ASSISTANT,
				"93 Oak Street",
				"Scranton",
				"PA",
				"18350",
				"5559381272",
				"d.schrute@dundermifflin.com",
				0
		);
		
		assertFalse(eds.getEmployeeById(dschrute).isManager());
	}
	
	@Test
	public void allManagersAreManagers() {
		List<Employee> managers = eds.getAllManagers();
		boolean theyAreAllManagers = true;
		for(Employee manager: managers) {
			if(!manager.isManager())
				theyAreAllManagers = false;
		}
		assertTrue(theyAreAllManagers);
	}
	
	@Test
	public void deletingAnEmployeeDeletesAnEmployee() {
		Employee test = new Employee(0,
				"Delete", "Me",
				"392817",
				Position.HR,
				"89 Strangler Way",
				"Scranton",
				"PA",
				"18350",
				"5559182872",
				"delete.me@dundermifflin.com",
				0
		);
		eds.createEmployee(test);
		assertNotNull(eds.getEmployeeById(test));
		eds.deleteEmployee(test);
		assertNull(eds.getEmployeeById(test));
	}
	

}
