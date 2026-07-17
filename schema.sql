-- ============================================================
-- Coaching Management System - Database Schema
-- Database: MySQL 8.x
-- ============================================================

CREATE DATABASE IF NOT EXISTS coaching_management;
USE coaching_management;

-- ---------------------------------------------------------------
-- Control panel: institute name / branding (changeable at /controlPanel)
-- ---------------------------------------------------------------
CREATE TABLE institute_config (
    id INT PRIMARY KEY AUTO_INCREMENT,
    institute_name VARCHAR(150) NOT NULL DEFAULT 'My Coaching Institute',
    tagline VARCHAR(255) DEFAULT 'Excellence in Education',
    address VARCHAR(255),
    contact_phone VARCHAR(20),
    contact_email VARCHAR(100),
    logo_path VARCHAR(255),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
INSERT INTO institute_config (institute_name, tagline) VALUES ('My Coaching Institute', 'Excellence in Education');

-- ---------------------------------------------------------------
-- Courses & Subjects
-- ---------------------------------------------------------------
CREATE TABLE courses (
    course_id INT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(120) NOT NULL,
    duration_months INT NOT NULL DEFAULT 12,
    fee_amount DECIMAL(10,2) NOT NULL DEFAULT 0,
    description VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE subjects (
    subject_id INT PRIMARY KEY AUTO_INCREMENT,
    subject_name VARCHAR(120) NOT NULL,
    course_id INT,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
);

-- ---------------------------------------------------------------
-- Parents
-- ---------------------------------------------------------------
CREATE TABLE parents (
    parent_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    occupation VARCHAR(100),
    address VARCHAR(255)
);

-- ---------------------------------------------------------------
-- Students
-- ---------------------------------------------------------------
CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL,
    dob DATE,
    gender VARCHAR(10),
    address VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100),
    photo_path VARCHAR(255),
    parent_id INT,
    course_id INT,
    admission_date DATE DEFAULT (CURRENT_DATE),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    FOREIGN KEY (parent_id) REFERENCES parents(parent_id) ON DELETE SET NULL,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE SET NULL
);

-- ---------------------------------------------------------------
-- Faculty
-- ---------------------------------------------------------------
CREATE TABLE faculty (
    faculty_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL,
    dob DATE,
    gender VARCHAR(10),
    address VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100),
    photo_path VARCHAR(255),
    qualification VARCHAR(150),
    joining_date DATE DEFAULT (CURRENT_DATE),
    subject_id INT,
    basic_pay DECIMAL(10,2) NOT NULL DEFAULT 0,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    FOREIGN KEY (subject_id) REFERENCES subjects(subject_id) ON DELETE SET NULL
);

-- ---------------------------------------------------------------
-- Login / Authentication (separate from profile tables)
-- ---------------------------------------------------------------
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(60) NOT NULL UNIQUE,
    password_hash VARCHAR(256) NOT NULL,
    salt VARCHAR(64) NOT NULL,
    role VARCHAR(20) NOT NULL, -- STUDENT, FACULTY, HEAD
    linked_id INT, -- student_id or faculty_id (NULL for HEAD)
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ---------------------------------------------------------------
-- Class routine (recurring weekly schedule) & generated sessions
-- ---------------------------------------------------------------
CREATE TABLE class_routine (
    routine_id INT PRIMARY KEY AUTO_INCREMENT,
    course_id INT,
    subject_id INT,
    faculty_id INT,
    day_of_week VARCHAR(15) NOT NULL, -- MONDAY..SUNDAY
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    room VARCHAR(50),
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subjects(subject_id) ON DELETE CASCADE,
    FOREIGN KEY (faculty_id) REFERENCES faculty(faculty_id) ON DELETE CASCADE
);

CREATE TABLE class_sessions (
    session_id INT PRIMARY KEY AUTO_INCREMENT,
    routine_id INT NOT NULL,
    session_date DATE NOT NULL,
    topic VARCHAR(255),
    status VARCHAR(20) DEFAULT 'UPCOMING', -- UPCOMING, TAKEN, CANCELLED
    FOREIGN KEY (routine_id) REFERENCES class_routine(routine_id) ON DELETE CASCADE
);

-- ---------------------------------------------------------------
-- Attendance
-- ---------------------------------------------------------------
CREATE TABLE attendance_student (
    att_id INT PRIMARY KEY AUTO_INCREMENT,
    session_id INT NOT NULL,
    student_id INT NOT NULL,
    status VARCHAR(10) NOT NULL DEFAULT 'ABSENT', -- PRESENT, ABSENT
    marked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (session_id) REFERENCES class_sessions(session_id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    UNIQUE KEY uniq_session_student (session_id, student_id)
);

CREATE TABLE attendance_faculty (
    att_id INT PRIMARY KEY AUTO_INCREMENT,
    faculty_id INT NOT NULL,
    att_date DATE NOT NULL,
    status VARCHAR(10) NOT NULL DEFAULT 'PRESENT',
    marked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (faculty_id) REFERENCES faculty(faculty_id) ON DELETE CASCADE,
    UNIQUE KEY uniq_faculty_date (faculty_id, att_date)
);

-- ---------------------------------------------------------------
-- Fees
-- ---------------------------------------------------------------
CREATE TABLE fee_structure (
    fee_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    due_date DATE,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

CREATE TABLE fee_transactions (
    txn_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    txn_date DATE NOT NULL DEFAULT (CURRENT_DATE),
    mode VARCHAR(20) DEFAULT 'CASH',
    remarks VARCHAR(255),
    receipt_no VARCHAR(50),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

-- ---------------------------------------------------------------
-- Salary
-- ---------------------------------------------------------------
CREATE TABLE salary_transactions (
    txn_id INT PRIMARY KEY AUTO_INCREMENT,
    faculty_id INT NOT NULL,
    pay_month INT NOT NULL,
    pay_year INT NOT NULL,
    basic_pay DECIMAL(10,2) NOT NULL DEFAULT 0,
    arrears DECIMAL(10,2) NOT NULL DEFAULT 0,
    deductions DECIMAL(10,2) NOT NULL DEFAULT 0,
    net_pay DECIMAL(10,2) NOT NULL DEFAULT 0,
    paid_date DATE DEFAULT (CURRENT_DATE),
    remarks VARCHAR(255),
    FOREIGN KEY (faculty_id) REFERENCES faculty(faculty_id) ON DELETE CASCADE
);

-- ---------------------------------------------------------------
-- Notifications
-- ---------------------------------------------------------------
CREATE TABLE notifications (
    notif_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NULL, -- NULL = broadcast to all students
    title VARCHAR(150) NOT NULL,
    message VARCHAR(1000) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

-- ---------------------------------------------------------------
-- Exams / Quiz / Marks
-- ---------------------------------------------------------------
CREATE TABLE exams (
    exam_id INT PRIMARY KEY AUTO_INCREMENT,
    exam_name VARCHAR(150) NOT NULL,
    course_id INT,
    subject_id INT,
    faculty_id INT,
    exam_date DATE,
    total_marks INT NOT NULL DEFAULT 100,
    duration_minutes INT DEFAULT 30,
    is_quiz_online TINYINT(1) DEFAULT 0,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subjects(subject_id) ON DELETE CASCADE,
    FOREIGN KEY (faculty_id) REFERENCES faculty(faculty_id) ON DELETE SET NULL
);

CREATE TABLE questions (
    question_id INT PRIMARY KEY AUTO_INCREMENT,
    exam_id INT NOT NULL,
    question_text VARCHAR(1000) NOT NULL,
    image_path VARCHAR(255),
    option_a VARCHAR(255) NOT NULL,
    option_b VARCHAR(255) NOT NULL,
    option_c VARCHAR(255) NOT NULL,
    option_d VARCHAR(255) NOT NULL,
    correct_option CHAR(1) NOT NULL, -- A, B, C, D
    marks INT NOT NULL DEFAULT 1,
    FOREIGN KEY (exam_id) REFERENCES exams(exam_id) ON DELETE CASCADE
);

CREATE TABLE student_marks (
    mark_id INT PRIMARY KEY AUTO_INCREMENT,
    exam_id INT NOT NULL,
    student_id INT NOT NULL,
    marks_obtained DECIMAL(6,2) NOT NULL,
    remarks VARCHAR(255),
    entered_by INT, -- faculty_id
    entered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (exam_id) REFERENCES exams(exam_id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    UNIQUE KEY uniq_exam_student (exam_id, student_id)
);

CREATE TABLE quiz_attempts (
    attempt_id INT PRIMARY KEY AUTO_INCREMENT,
    exam_id INT NOT NULL,
    student_id INT NOT NULL,
    start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_time TIMESTAMP NULL,
    score DECIMAL(6,2) DEFAULT 0,
    status VARCHAR(20) DEFAULT 'IN_PROGRESS', -- IN_PROGRESS, SUBMITTED
    FOREIGN KEY (exam_id) REFERENCES exams(exam_id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

CREATE TABLE quiz_responses (
    response_id INT PRIMARY KEY AUTO_INCREMENT,
    attempt_id INT NOT NULL,
    question_id INT NOT NULL,
    selected_option CHAR(1),
    is_correct TINYINT(1) DEFAULT 0,
    FOREIGN KEY (attempt_id) REFERENCES quiz_attempts(attempt_id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES questions(question_id) ON DELETE CASCADE,
    UNIQUE KEY uniq_attempt_question (attempt_id, question_id)
);

-- ---------------------------------------------------------------
-- Seed data: Institute Head login
--   username: admin
--   password: admin123
-- The hash/salt below were precomputed with the exact same salted
-- SHA-256 scheme used by com.coaching.util.PasswordUtil, so they will
-- verify correctly the first time the app runs. Change this password
-- immediately after first login (Control Panel does not yet expose a
-- "change my own password" screen in v1 — use the users table or ask
-- a developer to extend AuthService.changePassword if needed).
-- ---------------------------------------------------------------
INSERT INTO users (username, password_hash, salt, role, linked_id, status)
VALUES ('admin', 'vN9mq4pNw2E9DvUKNnnklRm/ef0fWk/pwWeN0c4dllM=', 'MTIzNDU2Nzg5MGFiY2RlZg==', 'HEAD', NULL, 'ACTIVE');

-- ===============================================================
-- DUMMY / SAMPLE DATA  (safe to run after the schema above)
-- ===============================================================

-- 1. Institute configuration
INSERT INTO institute_config (institute_name, tagline, address, contact_phone, contact_email)
VALUES ('Brilliant Minds Coaching Centre',
        'Excellence in Education Since 2010',
        '42 Knowledge Park, Near City Mall, Patna – 800001, Bihar',
        '9876543210',
        'info@brilliantminds.edu.in');

-- 2. Courses
INSERT INTO courses (course_name, duration_months, fee_amount, description) VALUES
('JEE Foundation',   24, 48000.00, 'Comprehensive preparation for IIT-JEE Main & Advanced'),
('NEET Preparation', 12, 36000.00, 'Medical entrance exam coaching for class 11-12'),
('Class 10 Board',   12, 24000.00, 'CBSE Class 10 full subject preparation');

-- 3. Subjects
INSERT INTO subjects (subject_name, course_id) VALUES
('Physics',          1),
('Chemistry',        1),
('Mathematics',      1),
('Physics',          2),
('Chemistry',        2),
('Biology',          2),
('Mathematics',      3),
('Science',          3),
('English',          3),
('Social Studies',   3);

-- 4. Parents
INSERT INTO parents (name, phone, email, occupation, address) VALUES
('Ramesh Kumar',  '9811001001', 'ramesh.k@gmail.com',  'Businessman',    'Boring Road, Patna'),
('Suresh Prasad', '9811002002', 'suresh.p@gmail.com',  'Teacher',        'Rajendra Nagar, Patna'),
('Anita Sharma',  '9811003003', 'anita.s@gmail.com',   'Homemaker',      'Kankarbagh, Patna'),
('Vijay Singh',   '9811004004', 'vijay.s@gmail.com',   'Engineer',       'Patliputra Colony'),
('Meena Devi',    '9811005005', 'meena.d@gmail.com',   'Nurse',          'Gardanibagh, Patna'),
('Ajay Gupta',    '9811006006', 'ajay.g@gmail.com',    'Shopkeeper',     'Mithapur, Patna');

-- 5. Students
INSERT INTO students (name, gender, dob, phone, email, address, course_id, parent_id, status, admission_date) VALUES
('Rahul Kumar',     'Male',   '2006-03-12', '9900001111', 'rahul.k@gmail.com',    'Boring Road, Patna',       1, 1, 'ACTIVE', '2024-04-01'),
('Priya Sharma',    'Female', '2006-07-25', '9900002222', 'priya.s@gmail.com',    'Rajendra Nagar, Patna',    1, 2, 'ACTIVE', '2024-04-01'),
('Anjali Singh',    'Female', '2007-01-15', '9900003333', 'anjali.s@gmail.com',   'Kankarbagh, Patna',        2, 3, 'ACTIVE', '2024-06-01'),
('Rohit Verma',     'Male',   '2007-05-20', '9900004444', 'rohit.v@gmail.com',    'Patliputra Colony',        2, 4, 'ACTIVE', '2024-06-01'),
('Sneha Gupta',     'Female', '2008-09-10', '9900005555', 'sneha.g@gmail.com',    'Gardanibagh, Patna',       3, 5, 'ACTIVE', '2024-07-15'),
('Arjun Prasad',    'Male',   '2008-11-30', '9900006666', 'arjun.p@gmail.com',    'Mithapur, Patna',          3, 6, 'ACTIVE', '2024-07-15');

-- 6. Faculty
INSERT INTO faculty (name, gender, dob, phone, email, address, qualification, subject_id, basic_pay, status) VALUES
('Dr. Anil Mishra',   'Male',   '1982-04-10', '9800001111', 'anil.m@cms.edu', 'Boring Road, Patna',     'M.Sc Physics, PhD',     1, 45000.00, 'ACTIVE'),
('Mrs. Neha Sinha',   'Female', '1988-09-14', '9800002222', 'neha.s@cms.edu', 'Rajendra Nagar, Patna',  'M.Sc Chemistry',        2, 38000.00, 'ACTIVE'),
('Mr. Ravi Tiwari',   'Male',   '1985-06-20', '9800003333', 'ravi.t@cms.edu', 'Kankarbagh, Patna',      'M.Sc Mathematics, B.Ed',3, 40000.00, 'ACTIVE');

-- 7. User accounts
-- Head account already inserted above (admin / admin123)
-- Student accounts (password: student123)
INSERT INTO users (username, password_hash, salt, role, linked_id, status) VALUES
('rahul',   'YMjE6TNlYTFpynLqyAmZ8gqgJTYu7Zw6ezfsybzHSTg=', 'c3R1ZGVudDFzYWx0eA==', 'STUDENT', 1, 'ACTIVE'),
('priya',   'St/PC3aZ8R5pihRxc0+sa7nGGYVtAnC9hQLJSIEST2w=', 'c3R1ZGVudDJzYWx0eA==', 'STUDENT', 2, 'ACTIVE'),
('anjali',  'VsNXEliqzhlS23vTOM4Dhp/oXKnp5csKQuEtMUF8IyY=', 'c3R1ZGVudDNzYWx0eA==', 'STUDENT', 3, 'ACTIVE');
-- Faculty accounts (password: faculty123)
INSERT INTO users (username, password_hash, salt, role, linked_id, status) VALUES
('anil',    'FF7YjVMAiZ7LHbyDz9VRkGB39GvLUGbLeSuGFvDzBws=', 'ZmFjdWx0eTFzYWx0eA==', 'FACULTY', 1, 'ACTIVE'),
('neha',    '8rzDAummUSZRK2sCsR+11VOg/NsgE6C5gbxLBN3TnlU=', 'ZmFjdWx0eTJzYWx0eA==', 'FACULTY', 2, 'ACTIVE');

-- 8. Class routines
INSERT INTO class_routine (course_id, subject_id, faculty_id, day_of_week, start_time, end_time, room) VALUES
(1, 1, 1, 'MONDAY',    '09:00:00', '10:30:00', 'Room A'),
(1, 2, 2, 'TUESDAY',   '09:00:00', '10:30:00', 'Room B'),
(1, 3, 3, 'WEDNESDAY', '09:00:00', '10:30:00', 'Room A'),
(2, 4, 1, 'MONDAY',    '11:00:00', '12:30:00', 'Room C'),
(2, 5, 2, 'THURSDAY',  '09:00:00', '10:30:00', 'Room B'),
(3, 7, 3, 'FRIDAY',    '11:00:00', '12:30:00', 'Room A');

-- 9. Class sessions
INSERT INTO class_sessions (routine_id, session_date, topic, status) VALUES
(1, '2025-01-06', 'Laws of Motion – Newton\'s Three Laws',    'TAKEN'),
(1, '2025-01-13', 'Friction and Circular Motion',             'TAKEN'),
(1, '2025-01-20', 'Gravitation – Kepler\'s Laws',             'TAKEN'),
(2, '2025-01-07', 'Atomic Structure – Bohr Model',            'TAKEN'),
(2, '2025-01-14', 'Chemical Bonding – VSEPR Theory',          'TAKEN'),
(3, '2025-01-08', 'Quadratic Equations – Roots & Nature',     'TAKEN'),
(3, '2025-01-15', 'Sequences and Series – AP & GP',           'TAKEN'),
(1, '2025-06-30', NULL, 'UPCOMING'),
(4, '2025-01-06', 'Thermodynamics – Laws & Processes',        'TAKEN'),
(5, '2025-01-07', 'Organic Chemistry Introduction',           'TAKEN'),
(6, '2025-01-10', 'Algebra – Linear Equations',               'TAKEN');

-- 10. Student attendance
INSERT INTO attendance_student (session_id, student_id, status) VALUES
(1, 1, 'PRESENT'), (1, 2, 'PRESENT'),
(2, 1, 'PRESENT'), (2, 2, 'ABSENT'),
(3, 1, 'PRESENT'), (3, 2, 'PRESENT'),
(4, 1, 'PRESENT'), (4, 2, 'PRESENT'),
(5, 1, 'ABSENT'),  (5, 2, 'PRESENT'),
(6, 1, 'PRESENT'), (6, 2, 'PRESENT'),
(7, 1, 'PRESENT'), (7, 2, 'ABSENT'),
(9, 3, 'PRESENT'), (9, 4, 'PRESENT'),
(10, 3, 'PRESENT'), (10, 4, 'ABSENT'),
(11, 5, 'PRESENT'), (11, 6, 'PRESENT');

-- 11. Faculty attendance
INSERT INTO attendance_faculty (faculty_id, att_date, status) VALUES
(1, '2025-01-06', 'PRESENT'), (1, '2025-01-07', 'PRESENT'), (1, '2025-01-08', 'PRESENT'),
(1, '2025-01-09', 'PRESENT'), (1, '2025-01-10', 'ABSENT'),  (1, '2025-01-13', 'PRESENT'),
(2, '2025-01-06', 'PRESENT'), (2, '2025-01-07', 'PRESENT'), (2, '2025-01-08', 'ABSENT'),
(2, '2025-01-09', 'PRESENT'), (2, '2025-01-10', 'PRESENT'), (2, '2025-01-13', 'PRESENT'),
(3, '2025-01-06', 'PRESENT'), (3, '2025-01-07', 'PRESENT'), (3, '2025-01-08', 'PRESENT'),
(3, '2025-01-09', 'ABSENT'),  (3, '2025-01-10', 'PRESENT'), (3, '2025-01-13', 'PRESENT');

-- 12. Fee structures & transactions
INSERT INTO fee_structure (student_id, total_amount, due_date) VALUES
(1, 48000.00, '2025-03-31'),
(2, 48000.00, '2025-03-31'),
(3, 36000.00, '2025-06-30'),
(4, 36000.00, '2025-06-30'),
(5, 24000.00, '2025-09-30'),
(6, 24000.00, '2025-09-30');

INSERT INTO fee_transactions (student_id, amount, txn_date, mode, remarks, receipt_no) VALUES
(1, 20000.00, '2024-04-05', 'CASH',   'First instalment',   'RCP-001'),
(1, 15000.00, '2024-07-10', 'ONLINE', 'Second instalment',  'RCP-002'),
(2, 48000.00, '2024-04-08', 'ONLINE', 'Full fee paid',       'RCP-003'),
(3, 18000.00, '2024-06-05', 'CASH',   'First instalment',   'RCP-004'),
(4, 36000.00, '2024-06-10', 'CHEQUE', 'Full fee paid',       'RCP-005'),
(5, 12000.00, '2024-07-20', 'CASH',   'First instalment',   'RCP-006');

-- 13. Salary transactions
INSERT INTO salary_transactions (faculty_id, pay_month, pay_year, basic_pay, arrears, deductions, net_pay, paid_date, remarks) VALUES
(1, 1, 2025, 45000.00, 0.00, 2000.00, 43000.00, '2025-01-31', 'PF deducted'),
(1, 2, 2025, 45000.00, 0.00, 2000.00, 43000.00, '2025-02-28', 'PF deducted'),
(1, 3, 2025, 45000.00, 1000.00, 2000.00, 44000.00, '2025-03-31', 'Arrears for festival'),
(2, 1, 2025, 38000.00, 0.00, 1500.00, 36500.00, '2025-01-31', 'PF deducted'),
(2, 2, 2025, 38000.00, 0.00, 1500.00, 36500.00, '2025-02-28', 'PF deducted'),
(3, 1, 2025, 40000.00, 0.00, 1800.00, 38200.00, '2025-01-31', 'PF deducted'),
(3, 2, 2025, 40000.00, 500.00, 1800.00, 38700.00, '2025-02-28', 'Arrears included');

-- 14. Notifications
INSERT INTO notifications (student_id, title, message) VALUES
(NULL, 'Welcome to Session 2025-26!',
       'Dear students, the new academic session begins on 1st April. Please check your timetable on the portal.'),
(NULL, 'Exam Schedule Released',
       'Half-yearly examination schedule has been published. Login to view your exam dates and syllabus.'),
(1, 'Fee Reminder – Rahul Kumar',
    'Dear Rahul, your fee balance of Rs 13,000 is due by 31st March 2025. Please clear it at the earliest.'),
(NULL, 'Holiday Notice – Republic Day',
       'The institute will remain closed on 26th January 2025 on account of Republic Day. Classes resume on 27th January.');

-- 15. Exams
INSERT INTO exams (exam_name, course_id, subject_id, faculty_id, exam_date, total_marks, duration_minutes, is_quiz_online) VALUES
('Physics Unit Test 1',   1, 1, 1, '2025-02-10', 50,  60, 0),
('Chemistry Mid-Term',    1, 2, 2, '2025-02-15', 100, 90, 0),
('Maths Online Quiz 1',   1, 3, 3, '2025-03-01', 20,  30, 1),
('NEET Physics Mock',     2, 4, 1, '2025-02-20', 180, 180, 0),
('Class 10 Maths Test',   3, 7, 3, '2025-02-25', 80,  120, 0);

-- 16. Questions (for the online quiz, exam_id=3)
INSERT INTO questions (exam_id, question_text, option_a, option_b, option_c, option_d, correct_option, marks) VALUES
(3, 'What is the value of sin 30°?',
    '1', '1/2', '√3/2', '0', 'B', 2),
(3, 'The sum of interior angles of a triangle is:',
    '90°', '270°', '180°', '360°', 'C', 2),
(3, 'Which of the following is a prime number?',
    '9', '15', '21', '17', 'D', 2),
(3, 'Solve: 2x + 5 = 13. Find x.',
    '4', '3', '9', '6', 'A', 2),
(3, 'The slope of a horizontal line is:',
    '1', 'Undefined', '-1', '0', 'D', 2),
(3, 'What is 7² – 3²?',
    '44', '40', '49', '16', 'B', 2),
(3, 'If a = 3 and b = 4, find √(a² + b²).',
    '5', '7', '6', '12', 'A', 2),
(3, 'The HCF of 12 and 18 is:',
    '3', '6', '9', '12', 'B', 2),
(3, 'A percentage means parts per:',
    '10', '1000', '100', '50', 'C', 2),
(3, 'What is the area of a circle with radius 7 cm? (π ≈ 22/7)',
    '154 cm²', '44 cm²', '49 cm²', '22 cm²', 'A', 2);

-- 17. Student marks
INSERT INTO student_marks (exam_id, student_id, marks_obtained, remarks, entered_by) VALUES
(1, 1, 38.0,  'Good performance',          1),
(1, 2, 44.0,  'Excellent',                 1),
(2, 1, 72.0,  'Needs improvement in Org.', 2),
(2, 2, 85.0,  'Very good',                 2),
(4, 3, 135.0, 'Good attempt',              1),
(4, 4, 112.0, 'Average',                   1),
(5, 5, 68.0,  'Good',                      3),
(5, 6, 55.0,  'Average, improve algebra',  3);

