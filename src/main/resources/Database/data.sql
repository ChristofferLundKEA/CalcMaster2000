USE CalcMaster2000;

INSERT INTO Admin (Username, Password, Email) VALUES
('KlausPetersen', '#NGGYU1975', 'KP@alpha-solutions.com'),
('CelineLM', 'Fuckjegelskermonner123!', 'CLM@alpha-solutions.com');


INSERT INTO Project (Name, Description, StartDate, EndDate, AdminID) VALUES
('Webpage for Copenhagen ZOO', 'Fullstack ticket system', '2024-11-23', '2025-02-25', 1),
('Oles automekanik', 'Appointment App', '2025-01-01', '2025-03-15', 2);


INSERT INTO Task (ProjectID, Name, Description, Priority, TimeEstimate, Status) VALUES
(1, 'Prototype', 'Create prototype for each view', 'High', 3, 'In Progress'),
(1, 'Develop Frontend', 'Build user interface', 'Medium', 60, 'Not Started'),
(2, 'Login formula', 'Develop a login formula for the user', 'High', 20, 'In Progress');

-- Indsæt testdata i Subtask
INSERT INTO Subtask (TaskID, Name, Description, Priority, TimeEstimate, Status) VALUES
(1, 'Create map view of park', 'Make a detailed overview of the park', 'High', 10, 'Completed'),
(1, 'Create list of restaurants and shops', 'Make a detailed list of restaurants and shops', 'Medium', 15, 'In Progress'),
(2, 'Create Login Page', 'Design login page UI', 'High', 20, 'Not Started');

-- Indsæt testdata i Employee
INSERT INTO Employee (Name, PhoneNumber, Email, Skills) VALUES
('Anna Hansen', '12345678', 'AH@apha-solutions.com', 'UX design, Graphic design'),
('Peter Jensen', '87654321', 'PJ@alpha-solutions.com', 'Frontend Development, HTML, CSS, JS'),
('Maria Larsen', '56781234', 'ML@alpha-solutions.com', 'Backend Development, Java, APIs');

-- Indsæt testdata i Task_Assignment
INSERT INTO Task_Assignment (TaskID, EmployeeID) VALUES
(1, 1), -- Anna Hansen arbejder på 'Design Protoype'
(2, 2); -- Peter Jensen arbejder på 'Develop Frontend'


-- Indsæt testdata i Subtask_Assignment
INSERT INTO Subtask_Assignment (SubtaskID, EmployeeID) VALUES
(1, 1), -- Anna Hansen arbejder på 'Create map view of park'
(2, 1), -- Anna Hansen arbejder på 'Create list of restaurants and shops'
(3, 2); -- Peter Jensen arbejder på 'Create login Page'

