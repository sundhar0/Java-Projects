package dao;

import model.Employee;

import Exceptions.EmployeeNotFoundException;
import util.ConnectionHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService implements IEmployeeService {
	private static final String GET_EMPLOYEE_BY_ID = "SELECT * FROM employees WHERE EmployeeID = ?";
	private static final String GET_ALL_EMPLOYEES = "SELECT * FROM employees";
	private static final String ADD_EMPLOYEE = "INSERT INTO employees (FirstName, LastName, DateOfBirth, Gender, Email, PhoneNumber, Address, Position, JoiningDate, TerminationDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_BY_NAME = "SELECT * FROM employees WHERE FirstName = ? AND LastName = ?";

	
	@Override
	public Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException, ClassNotFoundException {
		try (Connection conn = ConnectionHelper.getConnection();
		         PreparedStatement stmt = conn.prepareStatement(GET_EMPLOYEE_BY_ID)) {

		        stmt.setInt(1, employeeId);

		        ResultSet rs = stmt.executeQuery();

		        if (rs.next()) {
		            Employee employee = mapToEmployee(rs);
		            return employee;
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
			return null;
	}

	@Override
	public List<Employee> getAllEmployees() throws ClassNotFoundException {
		List<Employee> employees = new ArrayList<>();
		try (Connection connection = ConnectionHelper.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_EMPLOYEES);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				employees.add(mapToEmployee(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error retrieving all employees.", e);
		}
		return employees;
	}




	@Override
	public void addEmployee(Employee employee) throws ClassNotFoundException {
		try (Connection connection = ConnectionHelper.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(ADD_EMPLOYEE)) {
			setEmployeeParameters(preparedStatement, employee);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error adding employee.", e);
		}
	}


	public Employee getEmployeesByName(String firstName, String lastName) throws ClassNotFoundException {
	    try (Connection conn = ConnectionHelper.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(GET_BY_NAME)) {

	        stmt.setString(1, firstName);
	        stmt.setString(2, lastName);

	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            Employee employee = mapToEmployee(rs);
	            return employee;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return null;
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

	private void setEmployeeParameters(PreparedStatement preparedStatement, Employee employee) throws SQLException {
		preparedStatement.setString(1, employee.getFirstName());
		preparedStatement.setString(2, employee.getLastName());
		preparedStatement.setDate(3, Date.valueOf(employee.getDateOfBirth()));
		preparedStatement.setString(4, employee.getGender());
		preparedStatement.setString(5, employee.getEmail());
		preparedStatement.setString(6, employee.getPhoneNumber());
		preparedStatement.setString(7, employee.getAddress());
		preparedStatement.setString(8, employee.getPosition());
		preparedStatement.setDate(9, Date.valueOf(employee.getJoiningDate()));
		preparedStatement.setDate(10,
				employee.getTerminationDate() != null ? Date.valueOf(employee.getTerminationDate()) : null);
	}

}
