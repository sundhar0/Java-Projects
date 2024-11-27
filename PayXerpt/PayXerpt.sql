drop database if exists cs;

create database cs;

use cs;

CREATE TABLE Employees (
    EmployeeID INT PRIMARY KEY auto_increment,
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
    PayrollID INT PRIMARY KEY auto_increment,
    EmployeeID INT,
    PayPeriodStartDate DATE,
    PayPeriodEndDate DATE,
    BasicSalary DECIMAL(10, 2),
    OvertimePay DECIMAL(10, 2),
    Deductions DECIMAL(10, 2),
    NetSalary DECIMAL(10, 2),
    FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID) ON DELETE CASCADE
);



CREATE TABLE LeaveDetails (
    LeaveId INT PRIMARY KEY AUTO_INCREMENT,
    EmployeeID INT NOT NULL,
    Leavetype varchar(10),
    startDate DATE NOT NULL,
    endDate DATE NOT NULL,
    FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID) ON DELETE CASCADE
);



CREATE TABLE FinancialRecords (
    RecordID INT PRIMARY KEY auto_increment,
    EmployeeID INT,
    RecordDate DATE,
    Description VARCHAR(100),
    Amount DECIMAL(10, 2),
    RecordType VARCHAR(50),
    FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID) ON DELETE CASCADE
);



INSERT INTO Employees (FirstName, LastName, DateOfBirth, Gender, Email, PhoneNumber, Address, Position, JoiningDate, TerminationDate)
VALUES
('John', 'Doe', '1985-05-15', 'Male', 'john.doe@example.com', '1234567890', '123 Elm St, Springfield', 'Manager', '2020-01-10', NULL),
('Jane', 'Smith', '1990-08-22', 'Female', 'jane.smith@example.com', '9876543210', '456 Maple Ave, Springfield', 'Developer', '2021-03-15', NULL),
('Bob', 'Brown', '1988-12-05', 'Male', 'bob.brown@example.com', '4561237890', '789 Oak St, Springfield', 'Designer', '2019-07-01', NULL),
('Alice', 'Johnson', '1992-11-11', 'Female', 'alice.johnson@example.com', '3216549870', '321 Pine Rd, Springfield', 'Analyst', '2022-02-01', NULL),
('Charlie', 'White', '1987-03-19', 'Male', 'charlie.white@example.com', '6547891230', '654 Birch Blvd, Springfield', 'Engineer', '2018-06-20', '2023-10-31');



INSERT INTO Payrolls (EmployeeID, PayPeriodStartDate, PayPeriodEndDate, BasicSalary, OvertimePay, Deductions, NetSalary)
VALUES
(1, '2022-01-01', '2022-01-31', 5000.00, 0.00, 1000.00, 4000.00),
(2, '2022-01-01', '2022-01-31', 6000.00, 0.00, 1200.00, 4800.00),
(3, '2022-01-01', '2022-01-31', 7000.00, 0.00, 1400.00, 5600.00),
(4, '2022-02-01', '2022-02-28', 5500.00, 0.00, 1100.00, 4400.00),
(5, '2022-01-01', '2022-01-31', 8000.00, 0.00, 1600.00, 6400.00);


INSERT INTO FinancialRecords (EmployeeID, RecordDate, Description, Amount, RecordType)
VALUES
(1, '2024-01-31', 'Salary Payment', 4000.00, 'Income'),
(2, '2024-05-30', 'Salary Payment', 4800.00, 'Income'),
(3, '2024-02-05', 'Salary Payment', 5600.00, 'Income'),
(4, '2024-10-28', 'Salary Payment', 4400.00, 'Income'),
(5, '2024-09-30', 'Salary Payment', 6400.00, 'Income');




select *from Employees;
select *from Payrolls;
select *from FinancialRecords;
select *from leavedetails;

