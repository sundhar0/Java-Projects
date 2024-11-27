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
import model.EmployeeFinaceRecord;
import model.FinacialRecords;
import util.ConnectionHelper;

public class FinacialRecordService implements IFinacialRecordService {
	
	private static final String ADD_FINACIAL_RECORD = "INSERT INTO financialrecords (EmployeeID, RecordDate, Description, Amount, RecordType) VALUES( ?, ?, ?, ?, ?)";
	private static final String INFO_BY_RECORD_ID = "SELECT * FROM financialrecords WHERE RecordID = ?";
	private static final String GET_ALL_EMPLOYEE_FINACAIL = "SELECT * FROM financialrecords";
	private static final String GET_LAST_THREE_MONTHS_PAYSLIPS = "SELECT * FROM financialrecords WHERE RecordDate >= ? AND EmployeeID = ?";
	private static final String GET_EMPLOYEE_FINACE_HISTROY = "SELECT * FROM financialrecords WHERE EmployeeID = ?";
	private static final String GET_EMPLOYE_AND_FINACE_DETAILS = "SELECT * FROM employees e INNER JOIN financialRecords f ON e.EmployeeID = f.EmployeeID";
	 
	
	
	public boolean addFinacialRecord(FinacialRecords finacialrecord) 
	        throws ClassNotFoundException, SQLException, EmployeeNotFoundException {
	    Connection connection = ConnectionHelper.getConnection();

	    String checkEmployeeQuery = "SELECT COUNT(*) FROM employees WHERE EmployeeID = ?";
	    PreparedStatement checkStmt = connection.prepareStatement(checkEmployeeQuery);
	    checkStmt.setInt(1, finacialrecord.getEmployeeId());
	    ResultSet rs = checkStmt.executeQuery();

	    if (rs.next() && rs.getInt(1) == 0) {
	        throw new EmployeeNotFoundException("Employee with ID " + finacialrecord.getEmployeeId() + " not found.");
	    }

	    PreparedStatement stmt = connection.prepareStatement(ADD_FINACIAL_RECORD);
	    setFinacialParameters(stmt, finacialrecord);

	    int rowsInserted = stmt.executeUpdate();
	    return rowsInserted > 0;
	}


	private void setFinacialParameters(PreparedStatement stmt, FinacialRecords finacialrecord) throws SQLException {
		stmt.setInt(1, finacialrecord.getEmployeeId());
		stmt.setDate(2,Date.valueOf(finacialrecord.getRecordDate()));
		stmt.setString(3, finacialrecord.getDescription());
		stmt.setDouble(4, finacialrecord.getAmount());
		stmt.setString(5, finacialrecord.getRecordType());
	}

	@Override
	public FinacialRecords infoByRecordId(int recordId) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionHelper.getConnection();
		PreparedStatement stmt = connection.prepareStatement(INFO_BY_RECORD_ID);
		
		stmt.setInt(1, recordId);
		
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()) {
			FinacialRecords finacialRecords = mapToFinacial(rs);
			return finacialRecords;
		}
		
		return null;
	}

	@Override
	public List<FinacialRecords> getAllEmployeeFinacialRecord() throws ClassNotFoundException, SQLException {
		List<FinacialRecords> finacialRecords = new ArrayList<>();
		Connection connection = ConnectionHelper.getConnection();
		PreparedStatement stmt = connection.prepareStatement(GET_ALL_EMPLOYEE_FINACAIL);
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			finacialRecords.add(mapToFinacial(rs));
		}
		
		return finacialRecords;
	}

	@Override
	public List<FinacialRecords> getLastThreeMonthsPaySlips(int employeeId) throws ClassNotFoundException, SQLException {
		List<FinacialRecords> payslips = new ArrayList<>();
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);

        Connection conn = ConnectionHelper.getConnection();
        PreparedStatement stmt = conn.prepareStatement(GET_LAST_THREE_MONTHS_PAYSLIPS);

        stmt.setDate(1, Date.valueOf(threeMonthsAgo));
        stmt.setInt(2, employeeId);
        ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                FinacialRecords record = new FinacialRecords(
                    rs.getInt("RecordID"),
                    rs.getInt("EmployeeId"),
                    rs.getDate("RecordDate").toLocalDate(),
                    rs.getString("Description"),
                    rs.getInt("Amount"),
                    rs.getString("RecordType")
                );
                payslips.add(record);
            }


        return payslips;
	}

	@Override
	public List<FinacialRecords> employeeFinacialInfo(int employeeId) throws ClassNotFoundException, SQLException {
		List<FinacialRecords> histroy = new ArrayList<>();
		Connection connection = ConnectionHelper.getConnection();
		PreparedStatement stmt = connection.prepareStatement(GET_EMPLOYEE_FINACE_HISTROY);
		
		stmt.setInt(1, employeeId);
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			histroy.add(mapToFinacial(rs));
		}
		
		return histroy;
		
	}

	@Override
	public List<EmployeeFinaceRecord> getEmployeeAndFinancialRecords() throws ClassNotFoundException, SQLException {
		List<EmployeeFinaceRecord> employee = new ArrayList<>();
		Connection conn = ConnectionHelper.getConnection();
	    PreparedStatement stmt = conn.prepareStatement(GET_EMPLOYE_AND_FINACE_DETAILS);
	    ResultSet rs = stmt.executeQuery();

	            while (rs.next()) {
	                employee.add(mapToEmployeeFinacerecord(rs));
	            }
	        

	        return employee;
	}

	

	private FinacialRecords mapToFinacial(ResultSet rs) throws SQLException {
		return new FinacialRecords (rs.getInt("RecordID"),
		rs.getInt("EmployeeID"),
		rs.getDate("RecordDate").toLocalDate(),
		rs.getString("Description"),
		rs.getDouble("Amount"),
		rs.getString("RecordType"));
	}
	
	
	private EmployeeFinaceRecord mapToEmployeeFinacerecord(ResultSet resultSet) throws SQLException {
		return new EmployeeFinaceRecord(resultSet.getInt("EmployeeID"), resultSet.getString("FirstName"),
				resultSet.getString("LastName"),
				resultSet.getInt("RecordID"), resultSet.getDate("RecordDate").toLocalDate(), resultSet.getString("Description"),
				resultSet.getDouble("Amount"), resultSet.getString("RecordType")
				);
	}

}
