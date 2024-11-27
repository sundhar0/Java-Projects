package model;

public class LeaveDetails {
	private int leaveID;
	private int employeeID;
	private String leavetype;
	private String startDate;
	private String endDate;
	
	public LeaveDetails(int leaveID, int employeeID, String leavetype, String startDate, String endDate) {
		super();
		this.leaveID = leaveID;
		this.employeeID = employeeID;
		this.leavetype = leavetype;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	
	
	public LeaveDetails() {
	}



	public int getLeaveID() {
		return leaveID;
	}



	public void setLeaveID(int leaveID) {
		this.leaveID = leaveID;
	}



	public int getEmployeeID() {
		return employeeID;
	}



	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}



	public String getLeavetype() {
		return leavetype;
	}



	public void setLeavetype(String leavetype) {
		this.leavetype = leavetype;
	}



	public String getStartDate() {
		return startDate;
	}



	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}



	public String getEndDate() {
		return endDate;
	}



	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



	@Override
	public String toString() {
		return "LeaveDetails [leaveID=" + leaveID + ", employeeID=" + employeeID + ", leavetype=" + leavetype
				+ ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
	
}
