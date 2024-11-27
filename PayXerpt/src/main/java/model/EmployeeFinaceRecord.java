package model;

import java.time.LocalDate;

public class EmployeeFinaceRecord {
    private int EmployeeId;
    private String FirstName;
    private String LastName;
    private int RecordID;
    private LocalDate RecordDate;
    private String Description;
    private double Amount;
    private String RecordType;
    
    
	public EmployeeFinaceRecord(int employeeId, String firstName, String lastName, int recordID, LocalDate recordDate,
			String description, double amount, String recordType) {
		super();
		EmployeeId = employeeId;
		FirstName = firstName;
		LastName = lastName;
		RecordID = recordID;
		RecordDate = recordDate;
		Description = description;
		Amount = amount;
		RecordType = recordType;
	}


	@Override
	public String toString() {
		return "EmployeeFinaceRecord [EmployeeId=" + EmployeeId + ", FirstName=" + FirstName + ", LastName=" + LastName
				+ ", RecordID=" + RecordID + ", RecordDate=" + RecordDate + ", Description=" + Description + ", Amount="
				+ Amount + ", RecordType=" + RecordType + "]";
	}
    
    
    
    
    
	

    
}