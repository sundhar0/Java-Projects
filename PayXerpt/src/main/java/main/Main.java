package main;

import dao.EmployeeService;
import dao.FinacialRecordService;
import dao.LeaveService;
import dao.PayRollService;
import model.Employee;
import model.EmployeeFinaceRecord;
import model.FinacialRecords;
import model.PayRoll;
import Exceptions.EmployeeNotFoundException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, EmployeeNotFoundException {
		EmployeeService employeeService = new EmployeeService();
		PayRoll payroll = new PayRoll();
		PayRollService payrollService = new PayRollService();
		LeaveService leaveService = new LeaveService();
		FinacialRecords finacialRecords = new FinacialRecords();
		FinacialRecordService finacialService = new FinacialRecordService();
		try (Scanner scanner = new Scanner(System.in)) {
			while(true) {
				System.out.println("\n=== Employee Management System ===");
				System.out.println("1. Search Employee by ID");
				System.out.println("2. View All Employees");
				System.out.println("3. Search Employee by Name");
				System.out.println("4. Add New Employee");
				System.out.println("5. Apply for a leave");
				System.out.println("6. Search by Leave Id");
				System.out.println("7. Employee who had Leave");
				System.out.println("8. Add Payroll Data");
				System.out.println("9. Search for payroll date with EmployeeId and SalaryDate");
				System.out.println("10. Add Finacial Record for an Employee");
				System.out.println("11. Search by RecordId");
				System.out.println("12. View All employee Financial Record");
				System.out.println("13. View Last Three months Financial record for Employee by EmployeeID");
				System.out.println("14. Employee Financial Info");
				System.out.println("15. View All Financial Info with Employee Info");
				System.out.println("16. View Employees who has not started payroll");
				System.out.println("17. Exit");
				System.out.println("Enter your choice: ");

				int choice = scanner.nextInt();
				scanner.nextLine(); 

				switch (choice) {
				case 1 -> {
					System.out.print("Enter Employee ID: ");
					int id = scanner.nextInt();
					
						Employee employee = employeeService.getEmployeeById(id);
						if(employee != null){
							System.out.println("Employee Details: " + employee);
						}
						else {
							System.out.println("Employee Not Found...");
						}
					
				}
				
				case 2 -> {
					List<Employee> employees = employeeService.getAllEmployees();
					if (employees.isEmpty()) {
						System.out.println("No employees found.");
					} else {
						employees.forEach(System.out::println);
					}
					break;
				}
				case 3 ->{
					
					System.out.print("Enter Firstname : ");
					String firstName = scanner.nextLine();
					
					System.out.print("Enter Lastname : ");
					String lastName = scanner.nextLine();
					Employee employee = employeeService.getEmployeesByName(firstName, lastName);

			        if (employee == null) {
			            System.out.println("No employees found with the given name.");
			        } else {
			        	System.out.println("Employee Details: " + employee);
			        }
			        break;
				}
				case 4 -> {
					Employee newEmployee = new Employee();
					System.out.print("Enter First Name: ");
					newEmployee.setFirstName(scanner.nextLine());
					System.out.print("Enter Last Name: ");
					newEmployee.setLastName(scanner.nextLine());
					System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
					newEmployee.setDateOfBirth(LocalDate.parse(scanner.nextLine()));
					System.out.print("Enter Gender: ");
					newEmployee.setGender(scanner.nextLine());
					System.out.print("Enter Email: ");
					newEmployee.setEmail(scanner.nextLine());
					System.out.print("Enter Phone Number: ");
					newEmployee.setPhoneNumber(scanner.nextLine());
					System.out.print("Enter Address: ");
					newEmployee.setAddress(scanner.nextLine());
					System.out.print("Enter Position: ");
					newEmployee.setPosition(scanner.nextLine());
					System.out.print("Enter Joining Date (YYYY-MM-DD): ");
					newEmployee.setJoiningDate(LocalDate.parse(scanner.nextLine()));
					System.out.print("Enter Termination Date (YYYY-MM-DD) or leave blank if not applicable: ");
					String terminationDate = scanner.nextLine();
					if (!terminationDate.isBlank()) {
						newEmployee.setTerminationDate(LocalDate.parse(terminationDate));
					}
					employeeService.addEmployee(newEmployee);
					System.out.println("Employee added successfully!");
					break;
				}
				
				case 5 ->{
					System.out.print("Enter EmployeeID : ");
					int employeeId = scanner.nextInt();
					System.out.print("Enter LeaveType (Sick/Vacation/Others): ");
					scanner.nextLine();
					String leaveType = scanner.nextLine();
					System.out.print("Enter StartDate (YYYY-MM-DD): ");
					String startDate = scanner.nextLine(); 
					
					System.out.print("Enter EndDate (YYYY-MM-DD): ");
					String endDate = scanner.nextLine();
					
					if(leaveService.applyLeave(employeeId, leaveType, startDate, endDate)) {
						System.out.println("Leave Successfully applied.");
					}
					else {
						System.out.println("Failed to apply leave.");
					}
					break;
				}
				
				case 6 ->{
					System.out.print("Enter Leave ID : ");
					int leaveId = scanner.nextInt();
					System.out.println(leaveService.searchLeaveByID(leaveId));
					break;
				}
				
				case 7 -> {
					System.out.print("Enter Leave ID : ");
					int leaveID = scanner.nextInt();
					System.out.println(leaveService.employeeByLeaveID(leaveID));
					break;
				}
				
				case 8 -> {
					
					System.out.print("Enter EmployeeId : ");
					payroll.setEmployeeID(scanner.nextInt());
					scanner.nextLine();
					System.out.print("Enter PayPeriodStartDate (YYYY-MM-DD) : ");
					payroll.setPayPeriodStartDate(LocalDate.parse(scanner.nextLine()));
					System.out.print("Enter PayPeriodEndDate (YYYY-MM-DD) : ");
					payroll.setPayPeriodEndDate(LocalDate.parse(scanner.nextLine()));
					System.out.print("Enter BasicSalary : ");
					payroll.setBasicSalary(scanner.nextDouble());
					System.out.print("Enter OverTimePay : ");
					payroll.setOvertimePay(scanner.nextDouble());
					System.out.print("Enter Deduction : ");
					payroll.setDeductions(scanner.nextDouble());
					
					payrollService.addPayRoll(payroll);
					System.out.print("PayRoll Data added sucessfully...");
					break;
				}
				
				case 9 ->{
					System.out.print("Enter Employee ID: ");
			        int employeeId = scanner.nextInt();

			        System.out.print("Enter Salary Date (YYYY-MM-DD): ");
			        String salDateString = scanner.next();
			        
			        LocalDate salDate = LocalDate.parse(salDateString);
 
			            
			            PayRoll payrollList = payrollService.getPayrollByEmpIdAndSalDate(employeeId, salDate);
			            
			            if (payrollList != null) {
			                   System.out.println(payrollList);
			             
			            } else {
			                System.out.println("No payroll found for EmployeeID: " + employeeId + " on " + salDate);
			            }
			       
				}
				
				case 10 ->{
					System.out.print("Enter EmployeeID : ");
					finacialRecords.setEmployeeId(scanner.nextInt());
					scanner.nextLine();
					System.out.print("Enter RecordDate (YYYY-MM-DD): ");
					finacialRecords.setRecordDate(LocalDate.parse(scanner.nextLine()));
					System.out.print("Descrition : ");
					finacialRecords.setDescription(scanner.nextLine());
					System.out.print("Enter the Amount : ");
					finacialRecords.setAmount(scanner.nextDouble());
					scanner.nextLine();
					System.out.print("Enter Record type : ");
					finacialRecords.setRecordType(scanner.nextLine());
					
					if(finacialService.addFinacialRecord(finacialRecords)) {
						System.out.print("Record Added SucessFully...");
					}
					
				}
				
				case 11 ->{
					System.out.print("Enter RecordID : ");
					int recordId = scanner.nextInt();
					if(finacialService.infoByRecordId(recordId) == null) {
						System.out.print("Data Not Found...");
					}
					else {
						System.out.print(finacialService.infoByRecordId(recordId));
					}
				}
				
				case 12 ->{
					List<FinacialRecords> records = finacialService.getAllEmployeeFinacialRecord();
					if(records.isEmpty()) {
						System.out.print("No Data found...");
					}
					
					else {
						records.forEach(System.out::println);;
					}
				}
				
				case 13 ->{
					System.out.print("Enter EmployeeId : ");
					int employeeID = scanner.nextInt();
					List<FinacialRecords> records = finacialService.getLastThreeMonthsPaySlips(employeeID);
					if(!records.isEmpty()) {
						records.forEach(System.out::println);
					}
					else {
						System.out.print("Record Not Found");
					}
				}
				
				case 14 ->{
					System.out.print("Enter EmployeeId : ");
					int employeeID = scanner.nextInt();
					List<FinacialRecords> histroy = finacialService.employeeFinacialInfo(employeeID);
					if(!histroy.isEmpty()) {
						histroy.forEach(System.out::println);
					}
					else {
						System.out.print("No Data found...");
					}
				}
				
				case 15 ->{
					
					List<EmployeeFinaceRecord> records = finacialService.getEmployeeAndFinancialRecords();
					if(!records.isEmpty()) {
						records.forEach(System.out::println);
					}
					else {
						System.out.print("Record Not Found");
					}
				}
				
				case 16 ->{
					List<Employee> records = payrollService.displayEmployeesWithoutPayroll();
					if(records.isEmpty()) {
						System.out.print("No Data found...");
					}
					else {
						records.forEach(System.out::println);;
					}
					
				}
				
				case 17 ->{
					System.out.println("Exit SucessFully...");
					System.exit(0);
				}
				
				default -> System.out.println("Invalid choice. Please try again.");
				}
			 }
		}
	}
}
