package com.revature.Servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.revature.Connection.JDBCConnection;
import com.revature.Database.EmpField;
import com.revature.Database.EmployeeDataService;
import com.revature.Database.ExpensesService;
import com.revature.Database.LoginService;
import com.revature.model.Approval;
import com.revature.model.Employee;
import com.revature.model.Expense;
import com.revature.model.Position;
import com.revature.util.JSONParser;


public class FrontController extends HttpServlet {
	final static Logger log = Logger.getLogger(FrontController.class);
	private static final long serialVersionUID = 1L;
	
	private static LoginService loginService;
	private static EmployeeDataService employeeDataService;
	private static ExpensesService expensesService;
    private static HttpSession session;

    public FrontController() {
        super();
    }


	public void init(ServletConfig config) throws ServletException {
		log.info("FrontController Init");
		loginService = LoginService.getLoginService();
		employeeDataService = EmployeeDataService.getEmployeeDataService();
		expensesService = ExpensesService.getExpensesService();
	}

	public void destroy() {
		log.info("FrontController Destroy");
		try {
			JDBCConnection.getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		System.out.println(request.getRequestURI());
		switch(request.getRequestURI()) {

		case "/project1/logout.do":
			logout(request, response);
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println(request.getRequestURI());
		switch(request.getRequestURI()) {
		case "/project1/Project1/dist/html/login.do":
			handleLogin(request.getParameter("username"), request.getParameter("password"), request, response);
			break;
		case "/project1/Project1/dist/html/register.do":
			handleRegister(request, response);
			break;
		case "/project1/getEmployees.do":
			sendEmployeesToClient(response);
			break;
		case "/project1/getExpenses.do":
			sendExpensesToClient(response);
			break;
		case "/project1/getCurrentEmployee.do":
			getCurrentEmployee(response, request);
			break;
		case "/project1/getEmployeeId.do":
			getEmployeeId(response);
			break;
		case "/project1/create.do":
			createEmployee(request, response);
			break;
		case "/project1/submitExpenses.do":
			submitExpense(request, response);
			break;
		case "/project1/validateUsername.do":
			validateUserName(request, response);
			break;
		case "/project1/editemployee.do":
			editEmployee(request);
			break;
		case "/project1/resolveExpense.do":
			resolveExpense(request, response);
			break;
		case "/project1/forgotusername.do":
			forgotUsername(request, response);
			break;
		}
		
	}

	private void forgotUsername(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = request.getReader().readLine();
		System.out.println(data);
		String username = null;
		Map<String, String> emailData = JSONParser.parse(data);
		String employeeId = emailData.get("employeeId");
		Employee employee = employeeDataService.getEmployeeById(employeeId);
		System.out.println(employee);
		String toEmail = emailData.get("email");
		if(employee != null && toEmail.equals(employee.getEmail())) {
			username = loginService.getUserName(employee);
			String fromEmail = "jckuhl87@gmail.com";
			Properties props = System.getProperties();
			props.setProperty("mail.smtp.host", "localhost");
			Session emailSession = Session.getDefaultInstance(props);
			response.setContentType("text/html");
			
			MimeMessage message = new MimeMessage(emailSession);
			try {
				message.setFrom(fromEmail);
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
				message.setSubject("Your ERS username");
				message.setText("Hello!  Your username for the ERS is " + username);
				Transport.send(message);
				Map<String, String> returnData = new HashMap<>();
				returnData.put("invalid", "false");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				String json = new Gson().toJson(returnData);
				response.getWriter().write(json);
				response.getWriter().close();
				
			} catch (MessagingException e) {
				log.error(e.getMessage());
			}
		} else {

			Map<String, String> returnData = new HashMap<>();
			returnData.put("invalid", "true");
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			String json = new Gson().toJson(returnData);
			response.getWriter().write(json);
			response.getWriter().close();
		}
	}


	private static void resolveExpense(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = request.getReader().readLine();
		Map<String, String> expenseMap = JSONParser.parse(data);
		System.out.println(expenseMap);
		int approval = Integer.parseInt(expenseMap.get("approval"));
		int eid = Integer.parseInt(expenseMap.get("eid"));
		String approvingManager = expenseMap.get("approvingManager");
		expensesService.resolveExpense(eid, Approval.getApprovalLevel(approval), approvingManager);
	}


	private static void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession(false);
		if(session != null) {
			session.invalidate();
		}
		Map<String, String> data = new HashMap<String, String>();
		data.put("redirect", "../html/index.html");
		String json = new Gson().toJson(data);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	private static void handleLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		Employee employee = loginService.getEmployeeByLogin(username, password);
		if(employee == null) {
			try {
				redirectToError(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			};
		} else {
			session = request.getSession();
			session.setAttribute("employee", employee);
			try {
				request.getRequestDispatcher("manage.html").forward(request, response);
				
			} catch (ServletException | IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			};
		}
	}
	
	private static void redirectToError(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("error.html").forward(request, response);
	}


	private static void getCurrentEmployee(HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {
		if(session != null && session.getAttribute("employee") != null) {
			String json = new Gson().toJson(session.getAttribute("employee"));
			response.getWriter().write(json);
		} else {
			Map<String, String> map = new HashMap<>();
			map.put("message", "error");
			String json = new Gson().toJson(map);
			response.getWriter().write(json);
		}
	}
	
	private static void sendEmployeesToClient(HttpServletResponse response) throws IOException {
		List<Employee> employees = employeeDataService.getAllEmployees();
		String json = new Gson().toJson(employees);
		response.getWriter().write(json);
	}
	
	private static void sendExpensesToClient(HttpServletResponse response) throws IOException {
		List<Expense> expenses = expensesService.getAllExpenses();
		if(expenses != null) {
			System.out.println(expenses);
			String json = new Gson().toJson(expenses);
			System.out.println(json);
			response.getWriter().write(json);
		} else {
			log.error("Failed to retrieve expenses");
		}
	}
	
	private static void handleRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String employeeId = request.getParameter("empId");
		Employee employee = employeeDataService.getEmployeeById(employeeId);
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		loginService.setEmployeeLogin(employee, username, password);
		
		request.getRequestDispatcher("index.html").forward(request, response);;
	}
	
	private void getEmployeeId(HttpServletResponse response) throws IOException {
		List<String> emplIds = employeeDataService.getEmployeeIds();
		Set<String> emplSet = new HashSet<String>(emplIds);
		String generatedId = "";
		do {
			double randomNumber = Math.floor(Math.random() * 1_000_000);
			generatedId = Double.toString(randomNumber);
			if(generatedId.length() < 6) {
				int numZeros = 6 - generatedId.length();
				String zeroString = "";
				for(int i = 0; i < numZeros; i++) {
					zeroString += "0";
				}
				generatedId = zeroString + generatedId;
			}
		} while(emplSet.contains(generatedId));
		response.getWriter().write(generatedId);
	}
	
	private static void createEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = request.getReader().readLine();
		Map<String, String> employeeMap = JSONParser.parse(data);

		Employee employee = new Employee(0,
			employeeMap.get("fname"),
			employeeMap.get("lname"),
			employeeMap.get("employeeId"),
			Position.fromString(employeeMap.get("position")),
			employeeMap.get("street"),
			employeeMap.get("city"),
			employeeMap.get("state"),
			employeeMap.get("zip"),
			employeeMap.get("phone"),
			employeeMap.get("email"),
		0);
		employeeDataService.createEmployee(employee);
	}
	
	private static void submitExpense(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = request.getReader().readLine();
		System.out.println(data);
		List<Map<String, String>> expenseMap = JSONParser.parseArray(data);
		for(Map<String, String> expense: expenseMap) {
			System.out.println(expense);
			Expense exp = new Expense(0,
				expense.get("provider"),
				expense.get("date"),
				Double.parseDouble(expense.get("amount")),
				expense.get("reason"),
				Approval.PENDING,
				null,
				null
			);
			Employee owner = employeeDataService.getEmployeeById(expense.get("requestor"));
			exp.setOwner(owner);
			expensesService.submitExpense(exp);
		}
	}

	private static void validateUserName(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getReader().readLine();
		List<String> usernames = loginService.getAllUserNames();
		Map<String, Boolean> validation = new HashMap<>();
		validation.put("validated", !usernames.contains(username.replace("\"", "").trim()));
		String json = new Gson().toJson(validation);
		response.getWriter().write(json);
	}
	
	private void editEmployee(HttpServletRequest request) throws IOException {
		String data = request.getReader().readLine();
		Map<String, String> employeeMap = JSONParser.parse(data);
		Employee employee = employeeDataService.getEmployeeById(employeeMap.get("employeeId"));
		
		if(!employeeMap.get("fname").equals(employee.getFname())) {
			employeeDataService.updateEmployee(employee, EmpField.FNAME, employeeMap.get("fname"));
		}
		if(!employeeMap.get("lname").equals(employee.getLname())) {
			employeeDataService.updateEmployee(employee, EmpField.LNAME, employeeMap.get("lname"));
		}
		if(!employeeMap.get("street").equals(employee.getStreet())) {
			employeeDataService.updateEmployee(employee, EmpField.STREET, employeeMap.get("street"));
		}
		if(!employeeMap.get("city").equals(employee.getCity())) {
			employeeDataService.updateEmployee(employee, EmpField.CITY, employeeMap.get("city"));
		}
		if(!employeeMap.get("state").equals(employee.getState())) {
			employeeDataService.updateEmployee(employee, EmpField.STATE, employeeMap.get("state"));
		}
		if(!employeeMap.get("zip").equals(employee.getZip())) {
			employeeDataService.updateEmployee(employee, EmpField.ZIP, employeeMap.get("zip"));
		}
		if(!employeeMap.get("phone").equals(employee.getPhone())) {
			employeeDataService.updateEmployee(employee, EmpField.PHONE, employeeMap.get("phone"));
		}
		if(!employeeMap.get("email").equals(employee.getPhone())) {
			employeeDataService.updateEmployee(employee, EmpField.EMAIL, employeeMap.get("email"));
		}
	}
}
