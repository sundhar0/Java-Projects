package dao;

import java.sql.SQLException;
import java.util.List;

import Exceptions.EmployeeNotFoundException;
import model.Employee;
import model.EmployeeFinaceRecord;
import model.FinacialRecords;

public interface IFinacialRecordService {
	boolean addFinacialRecord(FinacialRecords finacialrecord) throws ClassNotFoundException, SQLException, EmployeeNotFoundException;
	FinacialRecords infoByRecordId(int recordId) throws ClassNotFoundException, SQLException;
	List<FinacialRecords> getAllEmployeeFinacialRecord() throws ClassNotFoundException, SQLException;
	List<FinacialRecords> getLastThreeMonthsPaySlips(int employeeId) throws ClassNotFoundException, SQLException;
	List<FinacialRecords> employeeFinacialInfo(int employeeId) throws ClassNotFoundException, SQLException;
	List<EmployeeFinaceRecord> getEmployeeAndFinancialRecords() throws ClassNotFoundException, SQLException;
}
