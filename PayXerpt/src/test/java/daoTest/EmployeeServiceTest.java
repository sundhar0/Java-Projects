package daoTest;

import model.Employee;
import org.junit.jupiter.api.*;

import Exceptions.EmployeeNotFoundException;
import dao.EmployeeService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeServiceTest {
    private EmployeeService employeeService;

    @BeforeAll
    void setUp() {
        employeeService = new EmployeeService();
    }

    @Test
    void testAddEmployee() throws ClassNotFoundException {
        Employee employee = new Employee(
                0, 
                "John", 
                "Doe", 
                LocalDate.of(1990, 1, 1), 
                "Male", 
                "john.doe@example.com", 
                "1234567890", 
                "123 Street, City", 
                "Developer", 
                LocalDate.of(2020, 1, 1), 
                null
        );

        employeeService.addEmployee(employee);
        Employee fetchedEmployee = employeeService.getEmployeesByName("John", "Doe");
        assertNotNull(fetchedEmployee);
        assertEquals("John", fetchedEmployee.getFirstName());
        assertEquals("Doe", fetchedEmployee.getLastName());
    }

    @Test
    void testGetEmployeeById() throws ClassNotFoundException, EmployeeNotFoundException {
        Employee employee = employeeService.getEmployeeById(1);
        assertNotNull(employee);
        assertEquals(1, employee.getEmployeeId());
    }

    @Test
    void testGetAllEmployees() throws ClassNotFoundException {
        List<Employee> employees = employeeService.getAllEmployees();
        assertNotNull(employees);
        assertTrue(employees.size() > 0);
    }

    @Test
    void testGetEmployeesByName() throws ClassNotFoundException {
        Employee employee = employeeService.getEmployeesByName("John", "Doe");
        assertNotNull(employee);
        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
    }

}
