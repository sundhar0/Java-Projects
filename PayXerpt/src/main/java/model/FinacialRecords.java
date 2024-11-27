package model;

import java.time.LocalDate;


public class FinacialRecords {
	private int recordID;
	private int employeeId;
	private LocalDate recordDate;
	private String Description;
	private double amount;
	private String recordType;
	
	
	
	public FinacialRecords() {
		super();
		// TODO Auto-generated constructor stub
	}


	public FinacialRecords(int recordID, int employeeId, LocalDate recordDate, String description, double amount,
			String recordType) {
		super();
		this.recordID = recordID;
		this.employeeId = employeeId;
		this.recordDate = recordDate;
		Description = description;
		this.amount = amount;
		this.recordType = recordType;
	}
	
	
	public int getRecordID() {
		return recordID;
	}
	public void setRecordID(int recordID) {
		this.recordID = recordID;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public LocalDate getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(LocalDate recordDate) {
		this.recordDate = recordDate;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}


	@Override
	public String toString() {
		return "FinacialRecords [recordID=" + recordID + ", employeeId=" + employeeId + ", recordDate=" + recordDate
				+ ", Description=" + Description + ", amount=" + amount + ", recordType=" + recordType + "]";
	}
	
	
	
}
