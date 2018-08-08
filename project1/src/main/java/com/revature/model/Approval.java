package com.revature.model;

public enum Approval {
	APPROVED (1),
	PENDING (2),
	DENIED (3);
	
	private int approval;
	Approval(int approval) {
		this.approval = approval;
	}
	
	public int getApproval() {
		return this.approval;
	}
	
	public static Approval getApprovalLevel(int level) {
		for(Approval app: Approval.values()) {
			if(app.getApproval() == level) {
				return app;
			}
		}
		return null;
	}
}
