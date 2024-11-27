drop database if exists cs;

create database cs;

use cs;

CREATE TABLE Employees (
    EmployeeID INT PRIMARY KEY,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    DateOfBirth DATE,
    Gender VARCHAR(10),
    Email VARCHAR(100),
    PhoneNumber VARCHAR(20),
    Address VARCHAR(200),
    Position VARCHAR(50),
    JoiningDate DATE,
    TerminationDate DATE
);

CREATE TABLE Payrolls (
    PayrollID INT PRIMARY KEY,
    EmployeeID INT,
    PayPeriodStartDate DATE,
    PayPeriodEndDate DATE,
    BasicSalary DECIMAL(10, 2),
    OvertimePay DECIMAL(10, 2),
    Deductions DECIMAL(10, 2),
    NetSalary DECIMAL(10, 2),
    FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID)
);

CREATE TABLE LeaveDetails (
    LeaveId INT PRIMARY KEY AUTO_INCREMENT,
    EmployeeID INT NOT NULL,
    LeaveStartDate DATE NOT NULL,
    LeaveEndDate DATE NOT NULL,
    Lop DECIMAL(10, 2) DEFAULT 0.00,
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID) ON DELETE CASCADE
);

CREATE TABLE FinancialRecords (
    RecordID INT PRIMARY KEY,
    EmployeeID INT,
    RecordDate DATE,
    Description VARCHAR(100),
    Amount DECIMAL(10, 2),
    RecordType VARCHAR(50),
    FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID)
);


INSERT INTO Employees (EmployeeID, FirstName, LastName, DateOfBirth, Gender, Email, PhoneNumber, Address, Position, JoiningDate, TerminationDate)
VALUES
(1, 'A', 'B', '2000-01-01', 'Male', 'ab@gmail.com', '123-456-7890', '123 Main St', 'Software Engineer', '2020-01-01', NULL),
(2, 'B', 'C', '2001-06-15', 'Female', 'bc@gmail.com.com', '987-654-3210', '456 Elm St', 'Marketing Manager', '2020-06-01', NULL),
(3, 'C', 'D', '2002-03-12', 'Male', 'cd@gmail.com.com', '555-123-4567', '789 Oak St', 'Sales Representative', '2018-03-01', NULL),
(4, 'D', 'E', '2003-09-25', 'Female', 'de@gmail.com.com', '111-222-3333', '321 Pine St', 'HR Generalist', '2022-01-01', NULL),
(5, 'E', 'F', '2004-02-20', 'Male', 'ef@gmail.com.com', '777-888-9999', '901 Maple St', 'IT Manager', '2015-02-01', '2020-12-31');

INSERT INTO Payrolls (PayrollID, EmployeeID, PayPeriodStartDate, PayPeriodEndDate, BasicSalary, OvertimePay, Deductions, NetSalary)
VALUES
(1, 1, '2022-01-01', '2022-01-31', 5000.00, 0.00, 1000.00, 4000.00),
(2, 2, '2022-01-01', '2022-01-31', 6000.00, 0.00, 1200.00, 4800.00),
(3, 3, '2022-01-01', '2022-01-31', 7000.00, 0.00, 1400.00, 5600.00),
(4, 4, '2022-02-01', '2022-02-28', 5500.00, 0.00, 1100.00, 4400.00),
(5, 5, '2022-01-01', '2022-01-31', 8000.00, 0.00, 1600.00, 6400.00);


INSERT INTO FinancialRecords (RecordID, EmployeeID, RecordDate, Description, Amount, RecordType)
VALUES
(1, 1, '2022-01-31', 'Salary Payment', 4000.00, 'Income'),
(2, 2, '2022-01-31', 'Salary Payment', 4800.00, 'Income'),
(3, 3, '2022-01-31', 'Salary Payment', 5600.00, 'Income'),
(4, 4, '2022-02-28', 'Salary Payment', 4400.00, 'Income'),
(5, 5, '2022-01-31', 'Salary Payment', 6400.00, 'Income');


select *from Employees;
select *from Payrolls;
select *from FinancialRecords;