-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: coaching_management
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `attendance_faculty`
--

DROP TABLE IF EXISTS `attendance_faculty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendance_faculty` (
  `att_id` int NOT NULL AUTO_INCREMENT,
  `faculty_id` int NOT NULL,
  `att_date` date NOT NULL,
  `status` varchar(10) NOT NULL DEFAULT 'PRESENT',
  `marked_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`att_id`),
  UNIQUE KEY `uniq_faculty_date` (`faculty_id`,`att_date`),
  CONSTRAINT `attendance_faculty_ibfk_1` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`faculty_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance_faculty`
--

LOCK TABLES `attendance_faculty` WRITE;
/*!40000 ALTER TABLE `attendance_faculty` DISABLE KEYS */;
INSERT INTO `attendance_faculty` VALUES (1,1,'2025-01-06','PRESENT','2026-06-23 04:58:49'),(2,1,'2025-01-07','PRESENT','2026-06-23 04:58:49'),(3,1,'2025-01-08','PRESENT','2026-06-23 04:58:49'),(4,1,'2025-01-09','PRESENT','2026-06-23 04:58:49'),(5,1,'2025-01-10','ABSENT','2026-06-23 04:58:49'),(6,1,'2025-01-13','PRESENT','2026-06-23 04:58:49'),(7,2,'2025-01-06','PRESENT','2026-06-23 04:58:49'),(8,2,'2025-01-07','PRESENT','2026-06-23 04:58:49'),(9,2,'2025-01-08','ABSENT','2026-06-23 04:58:49'),(10,2,'2025-01-09','PRESENT','2026-06-23 04:58:49'),(11,2,'2025-01-10','PRESENT','2026-06-23 04:58:49'),(12,2,'2025-01-13','PRESENT','2026-06-23 04:58:49'),(13,3,'2025-01-06','PRESENT','2026-06-23 04:58:49'),(14,3,'2025-01-07','PRESENT','2026-06-23 04:58:49'),(15,3,'2025-01-08','PRESENT','2026-06-23 04:58:49'),(16,3,'2025-01-09','ABSENT','2026-06-23 04:58:49'),(17,3,'2025-01-10','PRESENT','2026-06-23 04:58:49'),(18,3,'2025-01-13','PRESENT','2026-06-23 04:58:49');
/*!40000 ALTER TABLE `attendance_faculty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendance_student`
--

DROP TABLE IF EXISTS `attendance_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendance_student` (
  `att_id` int NOT NULL AUTO_INCREMENT,
  `session_id` int NOT NULL,
  `student_id` int NOT NULL,
  `status` varchar(10) NOT NULL DEFAULT 'ABSENT',
  `marked_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`att_id`),
  UNIQUE KEY `uniq_session_student` (`session_id`,`student_id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `attendance_student_ibfk_1` FOREIGN KEY (`session_id`) REFERENCES `class_sessions` (`session_id`) ON DELETE CASCADE,
  CONSTRAINT `attendance_student_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance_student`
--

LOCK TABLES `attendance_student` WRITE;
/*!40000 ALTER TABLE `attendance_student` DISABLE KEYS */;
INSERT INTO `attendance_student` VALUES (1,1,1,'PRESENT','2026-06-23 04:58:49'),(2,1,2,'PRESENT','2026-06-23 04:58:49'),(3,2,1,'PRESENT','2026-06-23 04:58:49'),(4,2,2,'ABSENT','2026-06-23 04:58:49'),(5,3,1,'PRESENT','2026-06-23 04:58:49'),(6,3,2,'PRESENT','2026-06-23 04:58:49'),(7,4,1,'PRESENT','2026-06-23 04:58:49'),(8,4,2,'PRESENT','2026-06-23 04:58:49'),(9,5,1,'ABSENT','2026-06-23 04:58:49'),(10,5,2,'PRESENT','2026-06-23 04:58:49'),(11,6,1,'PRESENT','2026-06-23 04:58:49'),(12,6,2,'PRESENT','2026-06-23 04:58:49'),(13,7,1,'PRESENT','2026-06-23 04:58:49'),(14,7,2,'ABSENT','2026-06-23 04:58:49'),(15,9,3,'PRESENT','2026-06-23 04:58:49'),(16,9,4,'PRESENT','2026-06-23 04:58:49'),(17,10,3,'PRESENT','2026-06-23 04:58:49'),(18,10,4,'ABSENT','2026-06-23 04:58:49'),(19,11,5,'PRESENT','2026-06-23 04:58:49'),(20,11,6,'PRESENT','2026-06-23 04:58:49');
/*!40000 ALTER TABLE `attendance_student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_routine`
--

DROP TABLE IF EXISTS `class_routine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class_routine` (
  `routine_id` int NOT NULL AUTO_INCREMENT,
  `course_id` int DEFAULT NULL,
  `subject_id` int DEFAULT NULL,
  `faculty_id` int DEFAULT NULL,
  `day_of_week` varchar(15) NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `room` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`routine_id`),
  KEY `course_id` (`course_id`),
  KEY `subject_id` (`subject_id`),
  KEY `faculty_id` (`faculty_id`),
  CONSTRAINT `class_routine_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE,
  CONSTRAINT `class_routine_ibfk_2` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`subject_id`) ON DELETE CASCADE,
  CONSTRAINT `class_routine_ibfk_3` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`faculty_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_routine`
--

LOCK TABLES `class_routine` WRITE;
/*!40000 ALTER TABLE `class_routine` DISABLE KEYS */;
INSERT INTO `class_routine` VALUES (1,1,1,1,'MONDAY','09:00:00','10:30:00','Room A'),(2,1,2,2,'TUESDAY','09:00:00','10:30:00','Room B'),(3,1,3,3,'WEDNESDAY','09:00:00','10:30:00','Room A'),(4,2,4,1,'MONDAY','11:00:00','12:30:00','Room C'),(5,2,5,2,'THURSDAY','09:00:00','10:30:00','Room B'),(6,3,7,3,'FRIDAY','11:00:00','12:30:00','Room A'),(7,3,8,4,'MONDAY','10:00:00','12:00:00','1A');
/*!40000 ALTER TABLE `class_routine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_sessions`
--

DROP TABLE IF EXISTS `class_sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class_sessions` (
  `session_id` int NOT NULL AUTO_INCREMENT,
  `routine_id` int NOT NULL,
  `session_date` date NOT NULL,
  `topic` varchar(255) DEFAULT NULL,
  `status` varchar(20) DEFAULT 'UPCOMING',
  PRIMARY KEY (`session_id`),
  KEY `routine_id` (`routine_id`),
  CONSTRAINT `class_sessions_ibfk_1` FOREIGN KEY (`routine_id`) REFERENCES `class_routine` (`routine_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_sessions`
--

LOCK TABLES `class_sessions` WRITE;
/*!40000 ALTER TABLE `class_sessions` DISABLE KEYS */;
INSERT INTO `class_sessions` VALUES (1,1,'2025-01-06','Laws of Motion – Newton\'s Three Laws','TAKEN'),(2,1,'2025-01-13','Friction and Circular Motion','TAKEN'),(3,1,'2025-01-20','Gravitation – Kepler\'s Laws','TAKEN'),(4,2,'2025-01-07','Atomic Structure – Bohr Model','TAKEN'),(5,2,'2025-01-14','Chemical Bonding – VSEPR Theory','TAKEN'),(6,3,'2025-01-08','Quadratic Equations – Roots & Nature','TAKEN'),(7,3,'2025-01-15','Sequences and Series – AP & GP','TAKEN'),(8,1,'2025-06-30',NULL,'UPCOMING'),(9,4,'2025-01-06','Thermodynamics – Laws & Processes','TAKEN'),(10,5,'2025-01-07','Organic Chemistry Introduction','TAKEN'),(11,6,'2025-01-10','Algebra – Linear Equations','TAKEN');
/*!40000 ALTER TABLE `class_sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `course_id` int NOT NULL AUTO_INCREMENT,
  `course_name` varchar(120) NOT NULL,
  `duration_months` int NOT NULL DEFAULT '12',
  `fee_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `description` varchar(500) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (1,'JEE Foundation',24,48000.00,'Comprehensive preparation for IIT-JEE Main & Advanced','2026-06-23 04:58:49'),(2,'NEET Preparation',12,36000.00,'Medical entrance exam coaching for class 11-12','2026-06-23 04:58:49'),(3,'Class 10 Board',12,24000.00,'CBSE Class 10 full subject preparation','2026-06-23 04:58:49');
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exams`
--

DROP TABLE IF EXISTS `exams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exams` (
  `exam_id` int NOT NULL AUTO_INCREMENT,
  `exam_name` varchar(150) NOT NULL,
  `course_id` int DEFAULT NULL,
  `subject_id` int DEFAULT NULL,
  `faculty_id` int DEFAULT NULL,
  `exam_date` date DEFAULT NULL,
  `total_marks` int NOT NULL DEFAULT '100',
  `duration_minutes` int DEFAULT '30',
  `is_quiz_online` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`exam_id`),
  KEY `course_id` (`course_id`),
  KEY `subject_id` (`subject_id`),
  KEY `faculty_id` (`faculty_id`),
  CONSTRAINT `exams_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE,
  CONSTRAINT `exams_ibfk_2` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`subject_id`) ON DELETE CASCADE,
  CONSTRAINT `exams_ibfk_3` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`faculty_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exams`
--

LOCK TABLES `exams` WRITE;
/*!40000 ALTER TABLE `exams` DISABLE KEYS */;
INSERT INTO `exams` VALUES (1,'Physics Unit Test 1',1,1,1,'2025-02-10',50,60,0),(2,'Chemistry Mid-Term',1,2,2,'2025-02-15',100,90,0),(3,'Maths Online Quiz 1',1,3,3,'2025-03-01',20,30,1),(4,'NEET Physics Mock',2,4,1,'2025-02-20',180,180,0),(5,'Class 10 Maths Test',3,7,3,'2025-02-25',80,120,0);
/*!40000 ALTER TABLE `exams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculty`
--

DROP TABLE IF EXISTS `faculty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faculty` (
  `faculty_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(120) NOT NULL,
  `dob` date DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `photo_path` varchar(255) DEFAULT NULL,
  `qualification` varchar(150) DEFAULT NULL,
  `joining_date` date DEFAULT (curdate()),
  `subject_id` int DEFAULT NULL,
  `basic_pay` decimal(10,2) NOT NULL DEFAULT '0.00',
  `status` varchar(20) DEFAULT 'ACTIVE',
  PRIMARY KEY (`faculty_id`),
  KEY `subject_id` (`subject_id`),
  CONSTRAINT `faculty_ibfk_1` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`subject_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty`
--

LOCK TABLES `faculty` WRITE;
/*!40000 ALTER TABLE `faculty` DISABLE KEYS */;
INSERT INTO `faculty` VALUES (1,'Dr. Anil Mishra','1982-04-10','Male','Boring Road, Patna','9800001111','anil.m@cms.edu',NULL,'M.Sc Physics, PhD','2026-06-23',1,45000.00,'ACTIVE'),(2,'Mrs. Neha Sinha','1988-09-14','Female','Rajendra Nagar, Patna','9800002222','neha.s@cms.edu',NULL,'M.Sc Chemistry','2026-06-23',2,38000.00,'ACTIVE'),(3,'Mr. Ravi Tiwari','1985-06-20','Male','Kankarbagh, Patna','9800003333','ravi.t@cms.edu',NULL,'M.Sc Mathematics, B.Ed','2026-06-23',3,40000.00,'ACTIVE'),(4,'Amrit Kumar Singh','2004-03-31','Male','Patna','1234512345','amrit@gmail.com','uploads/faculty/b1a669fe71b740869e4707958379d77a.jpg','MCA','2026-06-23',8,5500.00,'ACTIVE');
/*!40000 ALTER TABLE `faculty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fee_structure`
--

DROP TABLE IF EXISTS `fee_structure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fee_structure` (
  `fee_id` int NOT NULL AUTO_INCREMENT,
  `student_id` int NOT NULL,
  `total_amount` decimal(10,2) NOT NULL,
  `due_date` date DEFAULT NULL,
  PRIMARY KEY (`fee_id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `fee_structure_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fee_structure`
--

LOCK TABLES `fee_structure` WRITE;
/*!40000 ALTER TABLE `fee_structure` DISABLE KEYS */;
INSERT INTO `fee_structure` VALUES (1,1,48000.00,'2025-03-31'),(2,2,48000.00,'2025-03-31'),(3,3,36000.00,'2025-06-30'),(4,4,36000.00,'2025-06-30'),(5,5,24000.00,'2025-09-30'),(6,6,24000.00,'2025-09-30'),(7,7,4000.00,NULL);
/*!40000 ALTER TABLE `fee_structure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fee_transactions`
--

DROP TABLE IF EXISTS `fee_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fee_transactions` (
  `txn_id` int NOT NULL AUTO_INCREMENT,
  `student_id` int NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `txn_date` date NOT NULL DEFAULT (curdate()),
  `mode` varchar(20) DEFAULT 'CASH',
  `remarks` varchar(255) DEFAULT NULL,
  `receipt_no` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`txn_id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `fee_transactions_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fee_transactions`
--

LOCK TABLES `fee_transactions` WRITE;
/*!40000 ALTER TABLE `fee_transactions` DISABLE KEYS */;
INSERT INTO `fee_transactions` VALUES (1,1,20000.00,'2024-04-05','CASH','First instalment','RCP-001'),(2,1,15000.00,'2024-07-10','ONLINE','Second instalment','RCP-002'),(3,2,48000.00,'2024-04-08','ONLINE','Full fee paid','RCP-003'),(4,3,18000.00,'2024-06-05','CASH','First instalment','RCP-004'),(5,4,36000.00,'2024-06-10','CHEQUE','Full fee paid','RCP-005'),(6,5,12000.00,'2024-07-20','CASH','First instalment','RCP-006'),(7,7,4000.00,'2026-06-24','CASH','Done','ABC1');
/*!40000 ALTER TABLE `fee_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `institute_config`
--

DROP TABLE IF EXISTS `institute_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `institute_config` (
  `id` int NOT NULL AUTO_INCREMENT,
  `institute_name` varchar(150) NOT NULL DEFAULT 'My Coaching Institute',
  `tagline` varchar(255) DEFAULT 'Excellence in Education',
  `address` varchar(255) DEFAULT NULL,
  `contact_phone` varchar(20) DEFAULT NULL,
  `contact_email` varchar(100) DEFAULT NULL,
  `logo_path` varchar(255) DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `institute_config`
--

LOCK TABLES `institute_config` WRITE;
/*!40000 ALTER TABLE `institute_config` DISABLE KEYS */;
INSERT INTO `institute_config` VALUES (1,'My Coaching Institute','Excellence in Education',NULL,NULL,NULL,NULL,'2026-06-23 04:58:48'),(2,'Brilliant Minds Coaching Centre','Excellence in Education Since 2010','42 Knowledge Park, Near City Mall, Patna – 800001, Bihar','9876543210','info@brilliantminds.edu.in',NULL,'2026-06-23 04:58:49');
/*!40000 ALTER TABLE `institute_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `notif_id` int NOT NULL AUTO_INCREMENT,
  `student_id` int DEFAULT NULL,
  `title` varchar(150) NOT NULL,
  `message` varchar(1000) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`notif_id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (1,NULL,'Welcome to Session 2025-26!','Dear students, the new academic session begins on 1st April. Please check your timetable on the portal.','2026-06-23 04:58:49'),(2,NULL,'Exam Schedule Released','Half-yearly examination schedule has been published. Login to view your exam dates and syllabus.','2026-06-23 04:58:49'),(3,1,'Fee Reminder – Rahul Kumar','Dear Rahul, your fee balance of Rs 13,000 is due by 31st March 2025. Please clear it at the earliest.','2026-06-23 04:58:49'),(4,NULL,'Holiday Notice – Republic Day','The institute will remain closed on 26th January 2025 on account of Republic Day. Classes resume on 27th January.','2026-06-23 04:58:49');
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parents`
--

DROP TABLE IF EXISTS `parents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parents` (
  `parent_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(120) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `occupation` varchar(100) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parents`
--

LOCK TABLES `parents` WRITE;
/*!40000 ALTER TABLE `parents` DISABLE KEYS */;
INSERT INTO `parents` VALUES (1,'Ramesh Kumar','9811001001','ramesh.k@gmail.com','Businessman','Boring Road, Patna'),(2,'Suresh Prasad','9811002002','suresh.p@gmail.com','Teacher','Rajendra Nagar, Patna'),(3,'Anita Sharma','9811003003','anita.s@gmail.com','Homemaker','Kankarbagh, Patna'),(4,'Vijay Singh','9811004004','vijay.s@gmail.com','Engineer','Patliputra Colony'),(5,'Meena Devi','9811005005','meena.d@gmail.com','Nurse','Gardanibagh, Patna'),(6,'Ajay Gupta','9811006006','ajay.g@gmail.com','Shopkeeper','Mithapur, Patna'),(7,'Mr. Anish Sahay','9999999999','anish@gmail.com','Legal Advisor','Patna');
/*!40000 ALTER TABLE `parents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `question_id` int NOT NULL AUTO_INCREMENT,
  `exam_id` int NOT NULL,
  `question_text` varchar(1000) NOT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `option_a` varchar(255) NOT NULL,
  `option_b` varchar(255) NOT NULL,
  `option_c` varchar(255) NOT NULL,
  `option_d` varchar(255) NOT NULL,
  `correct_option` char(1) NOT NULL,
  `marks` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`question_id`),
  KEY `exam_id` (`exam_id`),
  CONSTRAINT `questions_ibfk_1` FOREIGN KEY (`exam_id`) REFERENCES `exams` (`exam_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,3,'What is the value of sin 30°?',NULL,'1','1/2','√3/2','0','B',2),(2,3,'The sum of interior angles of a triangle is:',NULL,'90°','270°','180°','360°','C',2),(3,3,'Which of the following is a prime number?',NULL,'9','15','21','17','D',2),(4,3,'Solve: 2x + 5 = 13. Find x.',NULL,'4','3','9','6','A',2),(5,3,'The slope of a horizontal line is:',NULL,'1','Undefined','-1','0','D',2),(6,3,'What is 7² – 3²?',NULL,'44','40','49','16','B',2),(7,3,'If a = 3 and b = 4, find √(a² + b²).',NULL,'5','7','6','12','A',2),(8,3,'The HCF of 12 and 18 is:',NULL,'3','6','9','12','B',2),(9,3,'A percentage means parts per:',NULL,'10','1000','100','50','C',2),(10,3,'What is the area of a circle with radius 7 cm? (π ≈ 22/7)',NULL,'154 cm²','44 cm²','49 cm²','22 cm²','A',2);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_attempts`
--

DROP TABLE IF EXISTS `quiz_attempts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_attempts` (
  `attempt_id` int NOT NULL AUTO_INCREMENT,
  `exam_id` int NOT NULL,
  `student_id` int NOT NULL,
  `start_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `end_time` timestamp NULL DEFAULT NULL,
  `score` decimal(6,2) DEFAULT '0.00',
  `status` varchar(20) DEFAULT 'IN_PROGRESS',
  PRIMARY KEY (`attempt_id`),
  KEY `exam_id` (`exam_id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `quiz_attempts_ibfk_1` FOREIGN KEY (`exam_id`) REFERENCES `exams` (`exam_id`) ON DELETE CASCADE,
  CONSTRAINT `quiz_attempts_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_attempts`
--

LOCK TABLES `quiz_attempts` WRITE;
/*!40000 ALTER TABLE `quiz_attempts` DISABLE KEYS */;
INSERT INTO `quiz_attempts` VALUES (1,3,7,'2026-06-26 11:09:43','2026-06-26 11:11:22',18.00,'SUBMITTED');
/*!40000 ALTER TABLE `quiz_attempts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_responses`
--

DROP TABLE IF EXISTS `quiz_responses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_responses` (
  `response_id` int NOT NULL AUTO_INCREMENT,
  `attempt_id` int NOT NULL,
  `question_id` int NOT NULL,
  `selected_option` char(1) DEFAULT NULL,
  `is_correct` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`response_id`),
  UNIQUE KEY `uniq_attempt_question` (`attempt_id`,`question_id`),
  KEY `question_id` (`question_id`),
  CONSTRAINT `quiz_responses_ibfk_1` FOREIGN KEY (`attempt_id`) REFERENCES `quiz_attempts` (`attempt_id`) ON DELETE CASCADE,
  CONSTRAINT `quiz_responses_ibfk_2` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_responses`
--

LOCK TABLES `quiz_responses` WRITE;
/*!40000 ALTER TABLE `quiz_responses` DISABLE KEYS */;
INSERT INTO `quiz_responses` VALUES (1,1,1,'B',1),(2,1,2,'C',1),(3,1,3,'D',1),(4,1,4,'A',1),(5,1,5,'D',1),(6,1,6,'B',1),(7,1,7,'A',1),(8,1,8,'A',0),(9,1,9,'C',1),(10,1,10,'A',1);
/*!40000 ALTER TABLE `quiz_responses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salary_transactions`
--

DROP TABLE IF EXISTS `salary_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salary_transactions` (
  `txn_id` int NOT NULL AUTO_INCREMENT,
  `faculty_id` int NOT NULL,
  `pay_month` int NOT NULL,
  `pay_year` int NOT NULL,
  `basic_pay` decimal(10,2) NOT NULL DEFAULT '0.00',
  `arrears` decimal(10,2) NOT NULL DEFAULT '0.00',
  `deductions` decimal(10,2) NOT NULL DEFAULT '0.00',
  `net_pay` decimal(10,2) NOT NULL DEFAULT '0.00',
  `paid_date` date DEFAULT (curdate()),
  `remarks` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`txn_id`),
  KEY `faculty_id` (`faculty_id`),
  CONSTRAINT `salary_transactions_ibfk_1` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`faculty_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salary_transactions`
--

LOCK TABLES `salary_transactions` WRITE;
/*!40000 ALTER TABLE `salary_transactions` DISABLE KEYS */;
INSERT INTO `salary_transactions` VALUES (1,1,1,2025,45000.00,0.00,2000.00,43000.00,'2025-01-31','PF deducted'),(2,1,2,2025,45000.00,0.00,2000.00,43000.00,'2025-02-28','PF deducted'),(3,1,3,2025,45000.00,1000.00,2000.00,44000.00,'2025-03-31','Arrears for festival'),(4,2,1,2025,38000.00,0.00,1500.00,36500.00,'2025-01-31','PF deducted'),(5,2,2,2025,38000.00,0.00,1500.00,36500.00,'2025-02-28','PF deducted'),(6,3,1,2025,40000.00,0.00,1800.00,38200.00,'2025-01-31','PF deducted'),(7,3,2,2025,40000.00,500.00,1800.00,38700.00,'2025-02-28','Arrears included'),(8,4,1,2026,5500.00,1200.00,0.00,6700.00,'2026-06-24','Month: June'),(9,4,1,2026,5500.00,1.00,100.00,5401.00,'2026-06-26','good work');
/*!40000 ALTER TABLE `salary_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_marks`
--

DROP TABLE IF EXISTS `student_marks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_marks` (
  `mark_id` int NOT NULL AUTO_INCREMENT,
  `exam_id` int NOT NULL,
  `student_id` int NOT NULL,
  `marks_obtained` decimal(6,2) NOT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `entered_by` int DEFAULT NULL,
  `entered_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`mark_id`),
  UNIQUE KEY `uniq_exam_student` (`exam_id`,`student_id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `student_marks_ibfk_1` FOREIGN KEY (`exam_id`) REFERENCES `exams` (`exam_id`) ON DELETE CASCADE,
  CONSTRAINT `student_marks_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_marks`
--

LOCK TABLES `student_marks` WRITE;
/*!40000 ALTER TABLE `student_marks` DISABLE KEYS */;
INSERT INTO `student_marks` VALUES (1,1,1,38.00,'Good performance',1,'2026-06-23 04:58:49'),(2,1,2,44.00,'Excellent',1,'2026-06-23 04:58:49'),(3,2,1,72.00,'Needs improvement in Org.',2,'2026-06-23 04:58:49'),(4,2,2,85.00,'Very good',2,'2026-06-23 04:58:49'),(5,4,3,135.00,'Good attempt',1,'2026-06-23 04:58:49'),(6,4,4,112.00,'Average',1,'2026-06-23 04:58:49'),(7,5,5,68.00,'Good',3,'2026-06-23 04:58:49'),(8,5,6,55.00,'Average, improve algebra',3,'2026-06-23 04:58:49'),(9,3,7,18.00,'Auto-graded quiz',0,'2026-06-26 11:11:22');
/*!40000 ALTER TABLE `student_marks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `student_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(120) NOT NULL,
  `dob` date DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `photo_path` varchar(255) DEFAULT NULL,
  `parent_id` int DEFAULT NULL,
  `course_id` int DEFAULT NULL,
  `admission_date` date DEFAULT (curdate()),
  `status` varchar(20) DEFAULT 'ACTIVE',
  PRIMARY KEY (`student_id`),
  KEY `parent_id` (`parent_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `students_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `parents` (`parent_id`) ON DELETE SET NULL,
  CONSTRAINT `students_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (1,'Rahul Kumar','2006-03-12','Male','Boring Road, Patna','9900001111','rahul.k@gmail.com',NULL,1,1,'2024-04-01','ACTIVE'),(2,'Priya Sharma','2006-07-25','Female','Rajendra Nagar, Patna','9900002222','priya.s@gmail.com',NULL,2,1,'2024-04-01','ACTIVE'),(3,'Anjali Singh','2007-01-15','Female','Kankarbagh, Patna','9900003333','anjali.s@gmail.com',NULL,3,2,'2024-06-01','ACTIVE'),(4,'Rohit Verma','2007-05-20','Male','Patliputra Colony','9900004444','rohit.v@gmail.com',NULL,4,2,'2024-06-01','ACTIVE'),(5,'Sneha Gupta','2008-09-10','Female','Gardanibagh, Patna','9900005555','sneha.g@gmail.com',NULL,5,3,'2024-07-15','ACTIVE'),(6,'Arjun Prasad','2008-11-30','Male','Mithapur, Patna','9900006666','arjun.p@gmail.com',NULL,6,3,'2024-07-15','ACTIVE'),(7,'Aayush','2004-01-29','Male','patna','7878787878','aayush@gmail.com',NULL,7,1,'2026-06-24','ACTIVE');
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subjects`
--

DROP TABLE IF EXISTS `subjects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subjects` (
  `subject_id` int NOT NULL AUTO_INCREMENT,
  `subject_name` varchar(120) NOT NULL,
  `course_id` int DEFAULT NULL,
  PRIMARY KEY (`subject_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `subjects_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjects`
--

LOCK TABLES `subjects` WRITE;
/*!40000 ALTER TABLE `subjects` DISABLE KEYS */;
INSERT INTO `subjects` VALUES (1,'Physics',1),(2,'Chemistry',1),(3,'Mathematics',1),(4,'Physics',2),(5,'Chemistry',2),(6,'Biology',2),(7,'Mathematics',3),(8,'Science',3),(9,'English',3),(10,'Social Studies',3);
/*!40000 ALTER TABLE `subjects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(60) NOT NULL,
  `password_hash` varchar(256) NOT NULL,
  `salt` varchar(64) NOT NULL,
  `role` varchar(20) NOT NULL,
  `linked_id` int DEFAULT NULL,
  `status` varchar(20) DEFAULT 'ACTIVE',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','vN9mq4pNw2E9DvUKNnnklRm/ef0fWk/pwWeN0c4dllM=','MTIzNDU2Nzg5MGFiY2RlZg==','HEAD',NULL,'ACTIVE','2026-06-23 04:58:49'),(2,'rahul','YMjE6TNlYTFpynLqyAmZ8gqgJTYu7Zw6ezfsybzHSTg=','c3R1ZGVudDFzYWx0eA==','STUDENT',1,'ACTIVE','2026-06-23 04:58:49'),(3,'priya','St/PC3aZ8R5pihRxc0+sa7nGGYVtAnC9hQLJSIEST2w=','c3R1ZGVudDJzYWx0eA==','STUDENT',2,'ACTIVE','2026-06-23 04:58:49'),(4,'anjali','VsNXEliqzhlS23vTOM4Dhp/oXKnp5csKQuEtMUF8IyY=','c3R1ZGVudDNzYWx0eA==','STUDENT',3,'ACTIVE','2026-06-23 04:58:49'),(5,'anil','FF7YjVMAiZ7LHbyDz9VRkGB39GvLUGbLeSuGFvDzBws=','ZmFjdWx0eTFzYWx0eA==','FACULTY',1,'ACTIVE','2026-06-23 04:58:49'),(6,'neha','8rzDAummUSZRK2sCsR+11VOg/NsgE6C5gbxLBN3TnlU=','ZmFjdWx0eTJzYWx0eA==','FACULTY',2,'ACTIVE','2026-06-23 04:58:49'),(7,'Amrit','HK8drKSZ1bazY1HMxy/hYd60Xd7tMdx5l6RxPGXIeEg=','cgM0jT23ZVZ1Lsjhx8ChGA==','FACULTY',4,'ACTIVE','2026-06-23 13:34:33'),(8,'Aayush','a10lJiLkI2bJGa88gg0gSTm7D59bE7TkcuZrcqrOyZ0=','F+5D0Tsn4Q5PvGwfmi2rlg==','STUDENT',7,'ACTIVE','2026-06-24 11:14:59');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'coaching_management'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-26 19:32:07
