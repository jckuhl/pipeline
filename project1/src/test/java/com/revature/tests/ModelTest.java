package com.revature.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.revature.Database.EmpField;
import com.revature.model.Approval;
import com.revature.model.Position;
import com.revature.util.JSONParser;

public class ModelTest {

	@Test
	public void empFieldEnumProvidesProperValues() {
		assertEquals("fname", EmpField.FNAME.getField());
		assertEquals("lname", EmpField.LNAME.getField());
		assertEquals("position", EmpField.POSITION.getField());
		assertEquals("email", EmpField.EMAIL.getField());
		assertEquals("street", EmpField.STREET.getField());
		assertEquals("city", EmpField.CITY.getField());
		assertEquals("state", EmpField.STATE.getField());
		assertEquals("zip", EmpField.ZIP.getField());
		assertEquals("phone", EmpField.PHONE.getField());
	}
	
	@Test
	public void positionEnumDoesntHaveImproperValues() {
		assertNull(Position.fromString("this is invalid"));
	}
	
	@Test
	public void positionEnumDoesHaveProperValues() {
		assertEquals(Position.MANAGER, Position.fromString("Regional Manager"));
		assertEquals(Position.ASSISTANT, Position.fromString("Assistant To The Regional Manager"));
		assertEquals(Position.ACCOUNTING, Position.fromString("Accounting"));
		assertEquals(Position.RECEPTION, Position.fromString("Reception"));
		assertEquals(Position.HR, Position.fromString("Human Resources"));
		assertEquals(Position.SALES, Position.fromString("Sales"));
		assertEquals(Position.CS, Position.fromString("Customer Service"));
		assertEquals(Position.QA, Position.fromString("Quality Assurance"));
	}
	
	@Test
	public void jsonParserHandlesSingleObject() {
		Map<String, String> jsonMap = JSONParser.parse("{\"id\":0,\"fname\":\"Jonathan\",\"lname\":\"Kuhl\"}");
		Map<String, String> testMap = new HashMap<>();
		testMap.put("fname", "Jonathan");
		testMap.put("id", "0");
		testMap.put("lname", "Kuhl");
		assertTrue(jsonMap.equals(testMap));
	}
	
	@Test
	public void jsonParserHandlesSingleArray() {
		List<Map<String, String>> jsonMap = JSONParser.parseArray("[{\"id\":0,\"fname\":\"Jonathan\",\"lname\":\"Kuhl\"},{\"id\":1,\"fname\":\"Millard\",\"lname\":\"Filmore\"}]");
		Map<String, String> testMap1 = new HashMap<>();
		testMap1.put("fname", "Jonathan");
		testMap1.put("id", "0");
		testMap1.put("lname", "Kuhl");
		Map<String, String> testMap2 = new HashMap<>();
		testMap2.put("fname", "Millard");
		testMap2.put("id", "1");
		testMap2.put("lname", "Filmore");
		List<Map<String,String>> testMapList = new ArrayList<>();
		testMapList.add(testMap1);
		testMapList.add(testMap2);
		assertTrue(jsonMap.equals(testMapList));
	}
	
	@Test
	public void approvalReturnsInts() {
		assertEquals(1, Approval.APPROVED.getApproval());
		assertEquals(2, Approval.PENDING.getApproval());
		assertEquals(3, Approval.DENIED.getApproval());
	}
	
	@Test
	public void numericValuesReturnProperApproval() {
		assertEquals(Approval.APPROVED, Approval.getApprovalLevel(1));
		assertEquals(Approval.PENDING, Approval.getApprovalLevel(2));
		assertEquals(Approval.DENIED, Approval.getApprovalLevel(3));
	}
	
	@Test
	public void nonsenseNumericValuesReturnNothing() {
		assertNull(Approval.getApprovalLevel(4));
		assertNull(Approval.getApprovalLevel(-1));
		assertNull(Approval.getApprovalLevel( (int) Math.floor(Math.random() + 4)));
	}
	
}
