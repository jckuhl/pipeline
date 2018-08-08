package com.revature.model;

public class Expense {

	private int eid;
	private String provider;
	private String date;
	private double amount;
	private String reason;
	private Approval approval;	//1 approved, 2 pending, 3 denied
	private String approvingManager;
	private Employee owner;
	
	public Expense(int eid, String provider, String date, double amount, String reason, Approval approval, String approvingManager, Employee owner) {
		super();
		this.eid = eid;
		this.provider = provider;
		this.date = date;
		this.amount = amount;
		this.reason = reason;
		this.approval = approval;
		this.approvingManager = approvingManager;
		this.owner = owner;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Approval getApproval() {
		return approval;
	}

	public void setApproval(Approval approval) {
		this.approval = approval;
	}

	public String getApprovingManager() {
		return approvingManager;
	}

	public void setApprovingManager(Employee employee) {
		String approvingManager = employee.getFname() + " " + employee.getLname();
		this.approvingManager = approvingManager;
	}

	public Employee getOwner() {
		return owner;
	}

	public void setOwner(Employee owner) {
		this.owner = owner;
	}
	
	public int getEid() {
		return eid;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Expense [eid=" + eid + ", provider=" + provider + ", date=" + date + ", amount=" + amount + ", reason="
				+ reason + ", approval=" + approval + ", approvingManager=" + approvingManager + ", owner=" + owner
				+ "]";
	}
	
	
 }
