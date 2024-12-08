DROP DATABASE IF EXISTS CalcMaster2000;

CREATE DATABASE CalcMaster2000;

USE CalcMaster2000;

CREATE TABLE Admin (
AdminID INT AUTO_INCREMENT PRIMARY KEY,
Username VARCHAR(200) NOT NULL,
Password VARCHAR(200) NOT NULL,
Email VARCHAR(200) NOT NULL
);

CREATE TABLE Project (
ProjectID INT AUTO_INCREMENT PRIMARY KEY,
Name VARCHAR(100) NOT NULL,
Description TEXT,
StartDate DATE,
EndDate DATE,
TimeSpent INT,
AdminID INT,
FOREIGN KEY (AdminID) REFERENCES Admin(AdminID) ON DELETE SET NULL
);

CREATE TABLE Task (
TaskID INT AUTO_INCREMENT PRIMARY KEY,
ProjectID INT NOT NULL,
Name VARCHAR(100) NOT NULL,
Description TEXT,
Priority VARCHAR(50),
TimeEstimate INT,
Status VARCHAR(50),
FOREIGN KEY (ProjectID) REFERENCES Project(ProjectID) ON DELETE CASCADE
);

CREATE TABLE Subtask (
SubtaskID INT AUTO_INCREMENT PRIMARY KEY,
TaskID INT NOT NULL,
Name VARCHAR(100) NOT NULL,
Description TEXT,
Priority VARCHAR(50),
TimeEstimate INT,
Status VARCHAR(50),
FOREIGN KEY (TaskID) REFERENCES Task(TaskID) ON DELETE CASCADE
);

CREATE TABLE Employee (
EmployeeID INT AUTO_INCREMENT PRIMARY KEY,
Name VARCHAR(100) NOT NULL,
PhoneNumber VARCHAR(15),
Email VARCHAR(100) UNIQUE,
Skills VARCHAR(255)
);

CREATE TABLE Task_Assignment (
TaskID INT NOT NULL,
EmployeeID INT NOT NULL,
PRIMARY KEY (TaskID, EmployeeID), -- en medarbejder kan kun knyttes én gang til en specific task
FOREIGN KEY (TaskID) REFERENCES Task(TaskID) ON DELETE CASCADE, -- Hvis en Task slettes fra tabellen Task, slettes alle relaterede ting i Task_Assignment automatisk
FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID) ON DELETE CASCADE -- hvis en employee slettes fra tabellen employee, slettes alle relaterede data i Task_assignment automatisk
);

CREATE TABLE Subtask_Assignment (
SubtaskID INT NOT NULL,
EmployeeID INT NOT NULL,
PRIMARY KEY (SubtaskID, EmployeeID), -- en medarbejder kan kun knyttes én gang til en specific subtask
FOREIGN KEY (SubtaskID) REFERENCES Subtask(SubtaskID) ON DELETE CASCADE,
FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID) ON DELETE CASCADE
);
