package daoTest;

import dao.PayRollService;
import model.Employee;
import model.PayRoll;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PayRollServiceTest {

    private PayRollService payRollService;

    @BeforeAll
    void setUp() {
        payRollService = new PayRollService();
    }

    @Test
    void testAddPayRoll() throws Exception {
        PayRoll payRoll = new PayRoll(
                1,
                1,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 31),
                5000.00,
                200.00,
                100.00,
                5100.00
        );
        
        payRollService.addPayRoll(payRoll);
        PayRoll fetchedPayRoll = payRollService.getPayrollByEmpIdAndSalDate(1, LocalDate.of(2024, 1, 15));
        assertNotNull(fetchedPayRoll);
        assertEquals(1, fetchedPayRoll.getEmployeeID());
        assertEquals(5100.00, fetchedPayRoll.getNetSalary());
    }

    @Test
    void testGetPayrollByEmpIdAndSalDate() throws Exception {
        PayRoll payRoll = payRollService.getPayrollByEmpIdAndSalDate(1, LocalDate.of(2024, 1, 15));
        assertNotNull(payRoll);
        assertEquals(1, payRoll.getEmployeeID());
        assertEquals(5000.00, payRoll.getBasicSalary());
    }

    @Test
    void testDisplayEmployeesWithoutPayroll() throws Exception {
        List<Employee> employees = payRollService.displayEmployeesWithoutPayroll();
        assertNotNull(employees);
        assertTrue(employees.size() > 0);
    }

    
}
