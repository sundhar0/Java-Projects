package daoTest;

import dao.LeaveService;
import model.Employee;
import model.LeaveDetails;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LeaveServiceTest {

    private LeaveService leaveService;

    @BeforeAll
    void setUp() {
        leaveService = new LeaveService();
    }

    @Test
    void testApplyLeave() throws Exception {
        boolean result = leaveService.applyLeave(1, "Sick", "2024-11-24", "2024-11-25");
        assertTrue(result);
    }

    @Test
    void testSearchLeaveByID() throws Exception {
        LeaveDetails leaveDetails = leaveService.searchLeaveByID(1);
        assertNotNull(leaveDetails);
        assertEquals(1, leaveDetails.getLeaveID());
    }

    @Test
    void testEmployeeByLeaveID() throws Exception {
        Employee employee = leaveService.employeeByLeaveID(1);
        assertNotNull(employee);
        assertEquals(6, employee.getEmployeeId());
    }

    
}
