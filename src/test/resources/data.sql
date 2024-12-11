INSERT INTO Admin (Username, Password, Email) VALUES
    ('KlausPetersen', '#NGGYU1975', 'KP@alpha-solutions.com'),
    ('CelineLM', 'Fuckjegelskermonner123!', 'CLM@alpha-solutions.com'),
    ('admin', 'admin', 'AD@alpha-solutions.com'),
    ('lars', 'larsen', 'LL@alpha-solutions.com');


INSERT INTO Project (Name, Description, StartDate, EndDate, AdminID) VALUES
    ('Webpage for Copenhagen ZOO', 'Fullstack ticket system', '2024-11-23', '2025-02-25', 1),
    ('Oles automekanik', 'Appointment App', '2025-01-01', '2025-03-15', 2);


INSERT INTO Task (ProjectID, Name, Description, Priority, TimeEstimate, Status) VALUES
    (1, 'Prototype', 'Create prototype for each view', 'High', 3, 'IN_PROGRESS'),
    (1, 'Develop Frontend', 'Build user interface', 'Medium', 60, 'NOT_STARTED'),
    (2, 'Login formula', 'Develop a login formula for the user', 'High', 20, 'IN_PROGRESS');

-- Indsæt testdata i Subtask
INSERT INTO Subtask (TaskID, Name, Description, Priority, TimeEstimate, Status) VALUES
    (1, 'Create map view of park', 'Make a detailed overview of the park', 'High', 10, 'DONE'),
    (1, 'Create list of restaurants and shops', 'Make a detailed list of restaurants and shops', 'Medium', 15, 'IN_PROGRESS'),
    (2, 'Create Login Page', 'Design login page UI', 'High', 20, 'NOT_STARTED');

-- Indsæt testdata i Employee
INSERT INTO Employee (Name, PhoneNumber, Email) VALUES
    ('Anna Hansen', '12345678', 'AH@apha-solutions.com'),
    ('Peter Jensen', '87654321', 'PJ@alpha-solutions.com'),
    ('Maria Larsen', '56781234', 'ML@alpha-solutions.com');

-- Indsæt testdata i Skill
INSERT INTO Skill (Name, Description) VALUES
    ('Frontend', 'Frontend development, focusing on user interface and experience.'),
    ('Backend', 'Backend development, server-side logic and database management.'),
    ('Fullstack', 'Combination of frontend and backend development skills.'),
    ('DevOps', 'Development and IT operations automation expertise.'),
    ('Data Science', 'Analyzing and interpreting complex data for decision-making.'),
    ('Machine Learning', 'Creating algorithms that allow computers to learn from data.'),
    ('AI Development', 'Artificial intelligence solutions and implementation.'),
    ('Cybersecurity', 'Protecting systems and networks from digital attacks.'),
    ('Mobile Development', 'Developing applications for Android and iOS platforms.'),
    ('Cloud Computing', 'Designing and managing cloud-based solutions.'),
    ('Database Management', 'Designing, implementing, and maintaining databases.'),
    ('Web Development', 'Building and maintaining websites and web applications.'),
    ('Game Development', 'Creating games for consoles, PCs, and mobile devices.'),
    ('AR/VR Development', 'Augmented and virtual reality application development.'),
    ('Embedded Systems', 'Programming hardware-specific software for devices.'),
    ('UI/UX Design', 'User interface and experience design for software.'),
    ('Blockchain', 'Developing decentralized solutions using blockchain technology.'),
    ('Testing/QA', 'Ensuring software quality through testing and debugging.'),
    ('System Administration', 'Maintaining and managing IT infrastructure.'),
    ('Technical Writing', 'Documenting technical systems and creating user guides.');

INSERT INTO Employee_Skill (EmployeeID, SkillID) VALUES
    (1, 1), -- Anna Hansen -> Frontend
    (1, 18), -- Anna Hansen -> Testing/QA
    (1, 16), -- Anna Hansen -> UI/UX Design
    (2, 2), -- Peter Jensen -> Backend
    (2, 4), -- Peter Jensen -> DevOps
    (2, 19), -- Peter Jensen -> System Administration
    (3, 5), -- Maria Larsen -> Data Science
    (3, 10), -- Maria Larsen -> Cloud Computing
    (3, 7); -- Maria Larsen -> AI Development


-- Indsæt testdata i Task_Assignment
INSERT INTO Task_Assignment (TaskID, EmployeeID) VALUES
    (1, 1), -- Anna Hansen arbejder på 'Design Protoype'
    (2, 2); -- Peter Jensen arbejder på 'Develop Frontend'


-- Indsæt testdata i Subtask_Assignment
INSERT INTO Subtask_Assignment (SubtaskID, EmployeeID) VALUES
    (1, 1), -- Anna Hansen arbejder på 'Create map view of park'
    (2, 1), -- Anna Hansen arbejder på 'Create list of restaurants and shops'
    (3, 2); -- Peter Jensen arbejder på 'Create login Page'