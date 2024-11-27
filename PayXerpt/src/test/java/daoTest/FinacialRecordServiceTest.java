package daoTest;

import dao.FinacialRecordService;
import model.FinacialRecords;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FinacialRecordServiceTest {

    private FinacialRecordService finacialRecordService;

    @BeforeAll
    void setUp() {
        finacialRecordService = new FinacialRecordService();
    }

    @Test
    void testAddFinacialRecord() throws Exception {
        FinacialRecords finacialRecord = new FinacialRecords(
                1, 
                1, 
                LocalDate.of(2024, 11, 24), 
                "Test description", 
                5000.0, 
                "Income"
        );

        boolean result = finacialRecordService.addFinacialRecord(finacialRecord);
        assertTrue(result);
    }

    @Test
    void testInfoByRecordId() throws Exception {
        FinacialRecords record = finacialRecordService.infoByRecordId(1);
        assertNotNull(record);
        assertEquals(1, record.getRecordID());
    }

    @Test
    void testGetAllEmployeeFinacialRecord() throws Exception {
        List<FinacialRecords> records = finacialRecordService.getAllEmployeeFinacialRecord();
        assertNotNull(records);
        assertTrue(records.size() > 0);
    }

    @Test
    void testGetLastThreeMonthsPaySlips() throws Exception {
        List<FinacialRecords> records = finacialRecordService.getLastThreeMonthsPaySlips(1);
        assertNotNull(records);
        assertTrue(records.size() > 0);
    }

    @Test
    void testEmployeeFinacialInfo() throws Exception {
        List<FinacialRecords> records = finacialRecordService.employeeFinacialInfo(1);
        assertNotNull(records);
        assertTrue(records.size() > 0);
    }

   
}
