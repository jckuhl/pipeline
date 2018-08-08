package com.revature.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.revature.Database.EmployeeDataService;
import com.revature.Database.LoginService;
import com.revature.model.Employee;

public class LoginDAOTest {
	
	public static LoginService ls = LoginService.getLoginService();
	public Employee mscott = EmployeeDataService.getEmployeeDataService().getEmployeeById("789261");
	

	@Test
	public void testLoggingInOnManagerGetsManager() {
		assertTrue(ls.getEmployeeByLogin("best boss", "passw0rd").isManager());
	}
	
	@Test public void testLoggingInOnEmployeeIsNotManager() {
		assertFalse(ls.getEmployeeByLogin("regional_manager", "p@$$w0rd1").isManager());
	}
	
	@Test
	public void testLoggingInAsMScottGetsMScott() {
		Employee mscott2 = ls.getEmployeeByLogin("best boss", "passw0rd");
		assertTrue(mscott.equals(mscott2));
	}
	
	@Test
	public void noNullUsernames() {
		List<String> usernames = ls.getAllUserNames();
		for(String username: usernames) {
			assertNotNull(username);
		}
	}

}
