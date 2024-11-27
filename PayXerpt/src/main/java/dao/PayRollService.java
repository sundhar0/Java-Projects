package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Exceptions.EmployeeNotFoundException;
import model.Employee;
import model.PayRoll;
import util.ConnectionHelper;

public class PayRollService implements IPayRoll {
	
	private static final String ADD_PAYROLL = "INSERT INTO payrolls (EmployeeID, PayPeriodStartDate, PayPeriodEndDate, BasicSalary,OvertimePay, Deductions, NetSalary) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String PAYROLL_BY_EMPLOYEE_AND_SALDATE = "SELECT * FROM payrolls WHERE EmployeeID = ? AND PayPeriodStartDate <= ? AND PayPeriodEndDate >= ?";
	private static final String GET_EMPLOYEE_WHO_NOT_STARTED_PAYROLL = "SELECT * FROM employees WHERE EmployeeID NOT IN (SELECT EmployeeID FROM payrolls)";
	
	
	public void addPayRoll(PayRoll payroll) throws ClassNotFoundException, SQLException, EmployeeNotFoundException {
	    Connection connection = ConnectionHelper.getConnection();

	    String checkEmployeeQuery = "SELECT COUNT(*) FROM employees WHERE EmployeeID = ?";
	    PreparedStatement checkStmt = connection.prepareStatement(checkEmployeeQuery);
	    checkStmt.setInt(1, payroll.getEmployeeID());
	    ResultSet rs = checkStmt.executeQuery();

	    if (rs.next() && rs.getInt(1) == 0) {
	        throw new EmployeeNotFoundException("Employee with ID " + payroll.getEmployeeID() + " not found.");
	    }

	    PreparedStatement preparedStatement = connection.prepareStatement(ADD_PAYROLL);
	    setEmployeeParametersForPayRoll(preparedStatement, payroll);
	    preparedStatement.executeUpdate();
	}


	private void setEmployeeParametersForPayRoll(PreparedStatement stmt, PayRoll payroll) throws SQLException {
		stmt.setInt(1, payroll.getEmployeeID());
		stmt.setDate(2, Date.valueOf(payroll.getPayPeriodStartDate()));
		stmt.setDate(3, Date.valueOf(payroll.getPayPeriodEndDate()));
		stmt.setDouble(4, payroll.getBasicSalary());
		stmt.setDouble(5, payroll.getOvertimePay());
		stmt.setDouble(6,  payroll.getDeductions());
		stmt.setDouble(7, payroll.getNetSalary());
		
	}

	public PayRoll getPayrollByEmpIdAndSalDate(int employeeId, LocalDate salDate) throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionHelper.getConnection();
        PreparedStatement stmt = conn.prepareStatement(PAYROLL_BY_EMPLOYEE_AND_SALDATE);
        
        stmt.setInt(1, employeeId);
        stmt.setDate(2, Date.valueOf(salDate)); 
        stmt.setDate(3, Date.valueOf(salDate));  
        
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            PayRoll payroll = new PayRoll(	
                rs.getInt("PayrollID"),
                rs.getInt("EmployeeID"),
                rs.getDate("PayPeriodStartDate").toLocalDate(),
                rs.getDate("PayPeriodEndDate").toLocalDate(),
                rs.getDouble("BasicSalary"),
                rs.getDouble("OvertimePay"),
                rs.getDouble("Deductions"),
                rs.getDouble("NetSalary")
            );
            return payroll;
        }
        
        return null;
    }
	
	@Override
	public List<Employee> displayEmployeesWithoutPayroll() throws ClassNotFoundException, SQLException {
		List<Employee> employee = new ArrayList<>(); 
		Connection conn = ConnectionHelper.getConnection();
	    PreparedStatement stmt = conn.prepareStatement(GET_EMPLOYEE_WHO_NOT_STARTED_PAYROLL);
	    ResultSet rs = stmt.executeQuery();
	    
	    while(rs.next()) {
	    	employee.add(mapToEmployee(rs));
	    }
	    
	    return employee;
	}
	
	private Employee mapToEmployee(ResultSet resultSet) throws SQLException {
		return new Employee(resultSet.getInt("EmployeeID"), resultSet.getString("FirstName"),
				resultSet.getString("LastName"), resultSet.getDate("DateOfBirth").toLocalDate(),
				resultSet.getString("Gender"), resultSet.getString("Email"), resultSet.getString("PhoneNumber"),
				resultSet.getString("Address"), resultSet.getString("Position"),
				resultSet.getDate("JoiningDate").toLocalDate(),
				resultSet.getDate("TerminationDate") != null ? resultSet.getDate("TerminationDate").toLocalDate()
						: null);
	}
}
