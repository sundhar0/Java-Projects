package dao;

import java.sql.SQLException;

import Exceptions.EmployeeNotFoundException;
import model.Employee;
import model.LeaveDetails;

public interface ILeaveService {
	Employee employeeByLeaveID(int leaveID) throws ClassNotFoundException, SQLException;
	boolean applyLeave(int EmployeeId, String LeaveType, String startDate, String endDate) throws ClassNotFoundException, SQLException, EmployeeNotFoundException;
	LeaveDetails searchLeaveByID(int leaveID) throws ClassNotFoundException, SQLException;
}
 
