package dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import Exceptions.EmployeeNotFoundException;
import model.Employee;
import model.PayRoll;

public interface IPayRoll {
	public void addPayRoll(PayRoll payroll) throws ClassNotFoundException, SQLException, EmployeeNotFoundException;
	public PayRoll getPayrollByEmpIdAndSalDate(int employeeId, LocalDate salDate) throws SQLException, ClassNotFoundException ;
	List<Employee> displayEmployeesWithoutPayroll() throws ClassNotFoundException, SQLException;

}
