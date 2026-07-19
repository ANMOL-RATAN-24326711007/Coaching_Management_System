# End Semester Project Documentation 📄

## Project Declaration

This document certifies that the following work is submitted as an **End Semester Project** for academic evaluation.

**Project Developed By:**
**Anmol Ratan**<br/>
**Enrollment No.: 24326711007**<br/>
**Guided By : Dr. Amit Kumar Shukla Sir**<br/>
**Project Development Time : 31st March 2026 To 30th June 2026 (4 months)**

**Institution:**
**CIMAGE Center Of Digital Technology & Entrepreneurship, Patna**

**University Affiliation:**
**Aryabhatta Knowledge University, Patna**

**Program:**
**Master of Computer Applications (MCA)**

---

## Project Description

This project has been developed as part of the academic requirements for the attainment of the **Master of Computer Applications (MCA)** degree.

The project demonstrates the conceptual implementation of a **Coaching Management System using Java EE (JSP / Servlet / JDBC)** for educational purposes.

---

## Technology Stack & Deployment

The project has been developed using the following technologies:

* **Programming Language:** Java 17
* **Web Framework:** Jakarta Servlet 5.0 + JSP + JSTL 3.0
* **Database:** MySQL 8.x via JDBC
* **PDF Generation:** OpenPDF 1.3.x
* **Charts & Visualization:** Chart.js 4.x (CDN)
* **Application Server:** Apache Tomcat 10.1
* **IDE:** Eclipse IDE for Enterprise Java

The system follows a modern web application architecture utilizing Java EE technologies for application development, MySQL for data management, JDBC for database connectivity, and Apache Tomcat 10.1 for deployment and testing purposes.

---

## Disclaimer

This project is **strictly intended for academic demonstration purposes only**.

* Not developed for **retail use**
* Not intended for **distribution**
* Not permitted for **commercial usage**

After submission, this project shall be considered the **property of Aryabhatta Knowledge University, Patna**.

---

**Author:** Anmol Ratan
**Enrollment No.: 24326711007** 🎓<br/>
**MCA - 4th semester AKU (2024-2026)**<br/>
**CIMAGE Center of Digital Technology and Entrepreneurship, Patna**

---

# Coaching Management System

A full-featured institute management portal built with **Java EE (JSP / Servlet / JDBC)**, MySQL, and Tomcat 10.1. No Spring, no Hibernate — pure Jakarta EE.

---

## Default Login Credentials

| Role           | Username | Password     |
| -------------- | -------- | ------------ |
| Institute Head | `admin`  | `admin123`   |
| Student        | `rahul`  | `student123` |
| Student        | `priya`  | `student123` |
| Student        | `anjali` | `student123` |
| Faculty        | `anil`   | `faculty123` |
| Faculty        | `neha`   | `faculty123` |

> Change all passwords after first login.

---

## Required JAR Files

Download these JARs and place them in `WebContent/WEB-INF/lib/`

| JAR                                      | Version |
| ---------------------------------------- | ------- |
| `mysql-connector-j-8.0.33.jar`           | 8.0.33  |
| `jakarta.servlet.jsp.jstl-api-3.0.0.jar` | 3.0.0   |
| `jakarta.servlet.jsp.jstl-3.0.1.jar`     | 3.0.1   |
| `openpdf-1.3.30.jar`                     | 1.3.30  |

> **Note:** Do NOT add `jakarta.servlet-api.jar` — it is provided by Tomcat 10.1 at runtime.

---

## Project Structure

```text
CoachingManagementSystem/
├── database/
│   └── schema.sql
├── src/
│   ├── db.properties
│   └── com/coaching/
│       ├── util/
│       ├── model/
│       ├── dao/
│       ├── service/
│       └── controller/
│           ├── student/
│           ├── faculty/
│           ├── head/
│           └── pdf/
└── WebContent/
    ├── index.jsp
    ├── css/style.css
    ├── uploads/
    └── WEB-INF/
        ├── web.xml
        ├── lib/
        └── views/
```

---

## Module Features

### Institute Head Module (`/head/`)

* Dashboard with enrolment and fee collection charts
* Enroll students with photo upload and parent details
* Add faculty with subject assignment and pay structure
* Assign weekly class routine
* Record fee payments and generate PDF reports
* Faculty salary management
* Send notifications to students
* Institute Control Panel management

### Faculty Module (`/faculty/`)

* Dashboard with class schedule and salary summary
* View upcoming and completed classes
* Take attendance with topic recording
* Feed student marks per examination
* Create exams and online quizzes
* Upload question images

### Student Module (`/student/`)

* Dashboard with performance graphs
* Attendance records and percentage tracking
* Fee ledger and transaction history
* Notifications inbox
* Online MCQ quiz participation
* Instant result and answer review

### PDF Reports

* Fee Collection Reports
* Salary Reports
* Examination Reports
* Downloadable PDF Statements

---

## Technology Stack

| Layer          | Technology                           |
| -------------- | ------------------------------------ |
| Language       | Java 17                              |
| Web Framework  | Jakarta Servlet 5.0 + JSP + JSTL 3.0 |
| Database       | MySQL 8.x via JDBC                   |
| PDF Generation | OpenPDF 1.3.x                        |
| Charts         | Chart.js 4.x                         |
| Server         | Apache Tomcat 10.1                   |
| IDE            | Eclipse IDE for Enterprise Java      |

---

## Troubleshooting

| Problem                                            | Fix                                  |
| -------------------------------------------------- | ------------------------------------ |
| `ClassNotFoundException: com.mysql.cj.jdbc.Driver` | Add MySQL Connector JAR              |
| `NoClassDefFoundError: com/lowagie/...`            | Add OpenPDF JAR                      |
| JSTL tag `${...}` prints as-is                     | Add JSTL API and Implementation JARs |
| 404 on URL                                         | Verify Tomcat Runtime Configuration  |
| Login fails for admin                              | Verify schema.sql execution          |
| Upload fails                                       | Check uploads folder permissions     |

---

## Academic Submission Details

**Project Title:** Coaching Management System
**Submitted By:** Anmol Ratan
**Enrollment Number:** 24326711007
**Program:** Master of Computer Applications (MCA)
**Session:** 2024-2026
**Semester:** 4th Semester
**Institution:** CIMAGE Center Of Digital Technology & Entrepreneurship, Patna
**University:** Aryabhatta Knowledge University (AKU), Patna
**Project Guide:** Dr. Amit Kumar Shukla Sir
**Development Duration:** 31st March 2026 to 30th June 2026

This project is submitted as a partial fulfillment of the requirements for the award of the degree of **Master of Computer Applications (MCA)** under **Aryabhatta Knowledge University, Patna**.
