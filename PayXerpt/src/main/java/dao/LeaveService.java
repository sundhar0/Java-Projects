package dao;

import java.sql.Connection;
 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Exceptions.EmployeeNotFoundException;
import model.Employee;
import model.LeaveDetails;
import util.ConnectionHelper;

public class LeaveService implements ILeaveService {
	
	private static final String APPLY_LEAVE = "INSERT INTO leavedetails (EmployeeID,LeaveType, startDate, endDate) VALUES(?, ?, ?, ?)";
	private static final String SEARCH_BY_LEAVE_ID = "SELECT * FROM leavedetails WHERE LeaveID = ?";
	private static final String EMPLOYEE_BY_LEAVE_ID = "SELECT * FROM employees WHERE EmployeeID = (SELECT EmployeeID FROM leavedetails WHERE LeaveID = ?)";
	

	@Override
	public boolean applyLeave(int EmployeeId, String LeaveType, String startDate, String endDate) 
	        throws ClassNotFoundException, SQLException, EmployeeNotFoundException {
	    Connection conn = ConnectionHelper.getConnection();
	    
	    String checkEmployeeQuery = "SELECT COUNT(*) FROM employees WHERE EmployeeID = ?";
	    PreparedStatement checkStmt = conn.prepareStatement(checkEmployeeQuery);
	    checkStmt.setInt(1, EmployeeId);
	    ResultSet rs = checkStmt.executeQuery();
	    
	    if (rs.next() && rs.getInt(1) == 0) {
	        throw new EmployeeNotFoundException("Employee with ID " + EmployeeId + " not found.");
	    }

	    PreparedStatement stmt = conn.prepareStatement(APPLY_LEAVE);
	    stmt.setInt(1, EmployeeId);
	    stmt.setString(2, LeaveType);
	    stmt.setString(3, startDate);
	    stmt.setString(4, endDate);

	    int rowsInserted = stmt.executeUpdate();
	    return rowsInserted > 0;
	}

	
	public LeaveDetails searchLeaveByID(int leaveID) throws ClassNotFoundException, SQLException {
		LeaveDetails leaveDetails = new LeaveDetails();
		Connection conn = ConnectionHelper.getConnection();
		PreparedStatement stmt = conn.prepareStatement(SEARCH_BY_LEAVE_ID);
		
		stmt.setInt(1, leaveID);
		
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()) {
			leaveDetails = new LeaveDetails(
                    rs.getInt("LeaveID"),
                    rs.getInt("EmployeeID"),
                    rs.getString("leaveType"),
                    rs.getString("startDate"),
                    rs.getString("endDate")
                );
		}
		
		return leaveDetails;
	}
	
	public Employee employeeByLeaveID(int leaveID) throws ClassNotFoundException, SQLException {
		Employee employee = new Employee();
		Connection conn = ConnectionHelper.getConnection();
		PreparedStatement stmt = conn.prepareStatement(EMPLOYEE_BY_LEAVE_ID);
		
		stmt.setInt(1, leaveID);
		
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()) {
            
			
			return new Employee(
                    rs.getInt("EmployeeID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getDate("DateOfBirth").toLocalDate(),
                    rs.getString("Gender"),
                    rs.getString("Email"),
                    rs.getString("PhoneNumber"),
                    rs.getString("Address"),
                    rs.getString("Position"),
                    rs.getDate("JoiningDate").toLocalDate(),
                    rs.getDate("TerminationDate") != null ? rs.getDate("TerminationDate").toLocalDate(): null
                );
		}
		
		return employee;
	}

}
