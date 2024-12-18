USE CalcMaster2000;

-- Opret Admin data
INSERT INTO Admin (Username, Password, Email) VALUES
    ('KlausPetersen', '#NGGYU1975', 'KP@alpha-solutions.com'),
    ('admin', 'admin', 'AD@alpha-solutions.com');

-- Opret Project data
INSERT INTO Project (Name, Description, StartDate, EndDate, AdminID) VALUES
    ('Webpage for Copenhagen ZOO', 'Fullstack ticket system', '2024-11-23', '2025-02-25', 1),
    ('Oles automekanik', 'Appointment App', '2025-01-01', '2025-03-15', 2),
    ('AI-driven Chatbot', 'Customer service automation tool', '2024-12-01', '2025-03-01', 1),
    ('Cloud Migration for Financial App', 'Migrate to Azure Cloud', '2024-10-15', '2025-01-30', 2),
    ('E-commerce Optimization', 'Revamp website for better user experience', '2024-09-01', '2025-03-31', 1);

-- Opret Task data
INSERT INTO Task (ProjectID, Name, Description, Priority, TimeEstimate, Status) VALUES
    (1, 'Prototype', 'Create prototype for each view', 'High', 3, 'IN_PROGRESS'),
    (1, 'Develop Frontend', 'Build user interface', 'Medium', 60, 'NOT_STARTED'),
    (2, 'Login formula', 'Develop a login formula for the user', 'High', 20, 'IN_PROGRESS'),
    (3, 'Initial Design', 'Design wireframes and layouts', 'High', 30, 'IN_PROGRESS'),
    (3, 'Backend Integration', 'Develop API services', 'High', 50, 'NOT_STARTED'),
    (4, 'Data Migration', 'Transfer all data securely', 'High', 70, 'IN_PROGRESS'),
    (4, 'Testing', 'Conduct performance and security tests', 'Medium', 40, 'NOT_STARTED'),
    (5, 'UX Redesign', 'Revise navigation and visuals', 'High', 45, 'IN_PROGRESS'),
    (5, 'Analytics Setup', 'Integrate user analytics tools', 'Medium', 20, 'NOT_STARTED');

-- Subtask data med præcis 3-5 subtasks pr. task
INSERT INTO Subtask (TaskID, Name, Description, Priority, TimeEstimate, Status) VALUES
    -- Task 1: Prototype
    (1, 'Create map view of park', 'Make a detailed overview of the park', 'High', 10, 'DONE'),
    (1, 'Create list of restaurants and shops', 'Make a detailed list of restaurants and shops', 'Medium', 15, 'IN_PROGRESS'),
    (1, 'Design prototype layout', 'Create a clickable prototype', 'High', 8, 'NOT_STARTED'),

    -- Task 2: Develop Frontend
    (2, 'Setup basic components', 'Create reusable frontend components', 'Medium', 15, 'NOT_STARTED'),
    (2, 'Integrate with backend', 'Connect frontend to backend services', 'High', 20, 'NOT_STARTED'),
    (2, 'User interface testing', 'Test UI with sample users', 'Medium', 10, 'IN_PROGRESS'),

    -- Task 3: Initial Design
    (3, 'Create homepage wireframe', 'Draft wireframe for the homepage', 'Medium', 8, 'DONE'),
    (3, 'User feedback on wireframe', 'Collect and implement feedback', 'High', 12, 'IN_PROGRESS'),
    (3, 'Finalize designs', 'Complete high-fidelity designs', 'High', 10, 'NOT_STARTED'),

    -- Task 4: Data Migration
    (4, 'Prepare migration scripts', 'Write scripts for data transfer', 'High', 20, 'IN_PROGRESS'),
    (4, 'Validate data integrity', 'Ensure transferred data is accurate', 'High', 15, 'NOT_STARTED'),
    (4, 'Optimize database indexes', 'Improve query performance', 'Medium', 10, 'NOT_STARTED'),
    (4, 'Backup old data', 'Ensure old data is backed up', 'Medium', 5, 'DONE'),
    (4, 'Schedule downtime', 'Coordinate migration downtime with users', 'High', 5, 'NOT_STARTED'),

    -- Task 5: Testing
    (5, 'Setup testing environment', 'Prepare testing environment for performance tests', 'High', 15, 'DONE'),
    (5, 'Write test cases', 'Write detailed test cases for all modules', 'Medium', 12, 'IN_PROGRESS'),
    (5, 'Run automated tests', 'Automate testing process', 'High', 10, 'NOT_STARTED'),

    -- Task 6: UX Redesign
    (6, 'Redesign navigation bar', 'Make navigation more intuitive', 'High', 10, 'IN_PROGRESS'),
    (6, 'Mobile-first design', 'Optimize layout for mobile devices', 'Medium', 20, 'NOT_STARTED'),
    (6, 'A/B Testing', 'Test new designs with users', 'Medium', 15, 'NOT_STARTED'),

    -- Task 7: Analytics Setup
    (7, 'Setup Google Analytics', 'Integrate tracking code', 'Medium', 5, 'DONE'),
    (7, 'Generate reports', 'Build dashboards for key metrics', 'High', 12, 'NOT_STARTED'),
    (7, 'Setup event tracking', 'Track button clicks and conversions', 'High', 10, 'NOT_STARTED');

-- Opret Employee data
INSERT INTO Employee (Name, PhoneNumber, Email) VALUES
    ('Anna Hansen', '12345678', 'AH@alpha-solutions.com'),
    ('Peter Jensen', '87654321', 'PJ@alpha-solutions.com'),
    ('Maria Larsen', '56781234', 'ML@alpha-solutions.com'),
    ('Jonas Nielsen', '23456789', 'JN@alpha-solutions.com'),
    ('Sofie Kristensen', '87612345', 'SK@alpha-solutions.com'),
    ('Oliver Hansen', '56784321', 'OH@alpha-solutions.com'),
    ('Camilla Sørensen', '12348765', 'CS@alpha-solutions.com'),
    ('Lars Frederiksen', '98765432', 'LF@alpha-solutions.com');

-- Opret Skill data
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

-- Tildel færdigheder til medarbejdere
INSERT INTO Employee_Skill (EmployeeID, SkillID) VALUES
    (1, 1), -- Anna Hansen -> Frontend
    (1, 18), -- Anna Hansen -> Testing/QA
    (2, 2), -- Peter Jensen -> Backend
    (2, 4), -- Peter Jensen -> DevOps
    (3, 5), -- Maria Larsen -> Data Science
    (4, 3), -- Jonas Nielsen -> Fullstack
    (4, 8), -- Jonas Nielsen -> Cybersecurity
    (5, 1), -- Sofie Kristensen -> Frontend
    (5, 16), -- Sofie Kristensen -> UI/UX Design
    (6, 2), -- Oliver Hansen -> Backend
    (6, 19), -- Oliver Hansen -> System Administration
    (7, 5), -- Camilla Sørensen -> Data Science
    (8, 9); -- Lars Frederiksen -> Mobile Development

-- Tildel tasks til medarbejdere
INSERT INTO Task_Assignment (TaskID, EmployeeID) VALUES
    (1, 1), -- Anna Hansen -> Task 'Prototype'
    (2, 2), -- Peter Jensen -> Task 'Develop Frontend'
    (3, 3), -- Maria Larsen -> Task 'Initial Design'
    (4, 4), -- Jonas Nielsen -> Task 'Data Migration'
    (5, 5), -- Sofie Kristensen -> Task 'Testing'
    (6, 6), -- Oliver Hansen -> Task 'UX Redesign'
    (7, 7); -- Camilla Sørensen -> Task 'Analytics Setup'

-- Tildel subtasks til medarbejdere
INSERT INTO Subtask_Assignment (SubtaskID, EmployeeID) VALUES
    (1, 1), -- Anna Hansen -> Subtask 'Create map view of park'
    (2, 1), -- Anna Hansen -> Subtask 'Create list of restaurants and shops'
    (3, 1), -- Anna Hansen -> Subtask 'Design prototype layout'
    (4, 2), -- Peter Jensen -> Subtask 'Setup basic components'
    (5, 2), -- Peter Jensen -> Subtask 'Integrate with backend'
    (6, 2), -- Peter Jensen -> Subtask 'User interface testing'
    (7, 3), -- Maria Larsen -> Subtask 'Create homepage wireframe'
    (8, 3), -- Maria Larsen -> Subtask 'User feedback on wireframe'
    (9, 3), -- Maria Larsen -> Subtask 'Finalize designs'
    (10, 4), -- Jonas Nielsen -> Subtask 'Prepare migration scripts'
    (11, 4), -- Jonas Nielsen -> Subtask 'Validate data integrity'
    (12, 4), -- Jonas Nielsen -> Subtask 'Optimize database indexes'
    (13, 4), -- Jonas Nielsen -> Subtask 'Backup old data'
    (14, 5), -- Sofie Kristensen -> Subtask 'Setup testing environment'
    (15, 5), -- Sofie Kristensen -> Subtask 'Write test cases'
    (16, 5), -- Sofie Kristensen -> Subtask 'Run automated tests'
    (17, 6), -- Oliver Hansen -> Subtask 'Redesign navigation bar'
    (18, 6), -- Oliver Hansen -> Subtask 'Mobile-first design'
    (19, 6), -- Oliver Hansen -> Subtask 'A/B Testing'
    (20, 7), -- Camilla Sørensen -> Subtask 'Setup Google Analytics'
    (21, 7), -- Camilla Sørensen -> Subtask 'Generate reports'
    (22, 7); -- Camilla Sørensen -> Subtask 'Setup event tracking'