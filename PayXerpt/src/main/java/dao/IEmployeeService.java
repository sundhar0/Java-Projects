package dao;

import model.Employee;
import model.LeaveDetails;
import model.PayRoll;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import Exceptions.EmployeeNotFoundException;

public interface IEmployeeService {
	Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException, ClassNotFoundException;
	List<Employee> getAllEmployees() throws ClassNotFoundException;
	void addEmployee(Employee employee) throws ClassNotFoundException;
	Employee getEmployeesByName(String firstName, String lastName) throws ClassNotFoundException;
}
