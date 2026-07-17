# Coaching Management System

A full-featured institute management portal built with **Java EE (JSP / Servlet / JDBC)**, MySQL, and Tomcat 10.1. No Spring, no Hibernate — pure Jakarta EE.

---

## Default Login Credentials

| Role           | Username | Password    |
|----------------|----------|-------------|
| Institute Head | `admin`  | `admin123`  |
| Student        | `rahul`  | `student123`|
| Student        | `priya`  | `student123`|
| Student        | `anjali` | `student123`|
| Faculty        | `anil`   | `faculty123`|
| Faculty        | `neha`   | `faculty123`|

> Change all passwords after first login.

---

## Required JAR Files

Download these JARs and place them in `WebContent/WEB-INF/lib/`

| JAR | Version | Download |
|-----|---------|----------|
| `mysql-connector-j-8.0.33.jar` | 8.0.33 | https://mvnrepository.com/artifact/com.mysql/mysql-connector-j/8.0.33 |
| `jakarta.servlet.jsp.jstl-api-3.0.0.jar` | 3.0.0 | https://mvnrepository.com/artifact/jakarta.servlet.jsp.jstl/jakarta.servlet.jsp.jstl-api/3.0.0 |
| `jakarta.servlet.jsp.jstl-3.0.1.jar` | 3.0.1 | https://mvnrepository.com/artifact/org.glassfish.web/jakarta.servlet.jsp.jstl/3.0.1 |
| `openpdf-1.3.30.jar` | 1.3.30 | https://mvnrepository.com/artifact/com.github.librepdf/openpdf/1.3.30 |

> **Note:** Do NOT add `jakarta.servlet-api.jar` — it is provided by Tomcat 10.1 at runtime.

---

## Setup Steps (Eclipse IDE for Java EE)

### Step 1 — Prerequisites

- Java JDK 17 or 21 installed
- Eclipse IDE for Enterprise Java (Eclipse 2023-12 or newer)
- Apache Tomcat 10.1.x downloaded and extracted
- MySQL 8.x running on localhost:3306
- MySQL Workbench or any MySQL client

---

### Step 2 — Create the Database

1. Open MySQL Workbench (or MySQL CLI).
2. Run the file `database/schema.sql` against your MySQL server:

```sql
SOURCE /path/to/CoachingManagementSystem/database/schema.sql;
```

Or in MySQL CLI:
```bash
mysql -u root -p < database/schema.sql
```

This will:
- Create the `coaching_management` database
- Create all tables
- Insert sample data + the default admin account

---

### Step 3 — Configure Database Connection

Open `src/db.properties` and update with your MySQL credentials:

```properties
db.url=jdbc:mysql://localhost:3306/coaching_management?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
db.driver=com.mysql.cj.jdbc.Driver
db.username=root
db.password=YOUR_MYSQL_PASSWORD
```

---

### Step 4 — Import into Eclipse

1. Open Eclipse → **File → Import → General → Existing Projects into Workspace**
2. Browse to the `CoachingManagementSystem` root folder
3. Tick the project and click **Finish**

If Eclipse shows errors about the server runtime:
- Go to **Window → Preferences → Server → Runtime Environments → Add**
- Select **Apache Tomcat v10.1**, browse to your Tomcat folder, click **Finish**
- Right-click the project → **Properties → Targeted Runtimes** → tick **Apache Tomcat v10.1**

---

### Step 5 — Add Required JARs

1. Download all 4 JARs listed above
2. Copy them into `WebContent/WEB-INF/lib/`
3. Eclipse auto-detects JARs in `WEB-INF/lib` — no `.classpath` edits needed

---

### Step 6 — Configure Upload Directory

The application saves uploaded photos and question images under `uploads/` inside the deployed web application. Tomcat creates this at runtime. No extra configuration needed.

---

### Step 7 — Run the Application

1. Right-click the project in Eclipse → **Run As → Run on Server**
2. Select **Apache Tomcat v10.1** → click **Finish**
3. Open your browser at:

```
http://localhost:8080/CoachingManagementSystem/
```

4. Login with `admin` / `admin123`

---

## Project Structure

```
CoachingManagementSystem/
├── database/
│   └── schema.sql              ← Run this first in MySQL
├── src/
│   ├── db.properties           ← Edit with your DB credentials
│   └── com/coaching/
│       ├── util/               ← DBConnection, PasswordUtil, FileUploadUtil, etc.
│       ├── model/              ← Plain Java POJOs
│       ├── dao/                ← All JDBC database operations
│       ├── service/            ← Business logic layer
│       └── controller/         ← Jakarta Servlets (mapped with @WebServlet)
│           ├── student/
│           ├── faculty/
│           ├── head/
│           └── pdf/
└── WebContent/
    ├── index.jsp               ← Redirects to /login
    ├── css/style.css
    ├── uploads/                ← Auto-created at runtime
    └── WEB-INF/
        ├── web.xml
        ├── lib/                ← Place your JARs here
        └── views/
            ├── common/         ← header.jsp, footer.jsp
            ├── login.jsp
            ├── student/
            ├── faculty/
            ├── head/
            └── controlPanel/
```

---

## Module Features

### Institute Head (`/head/`)
- Dashboard with enrolment & fee collection charts
- Enroll students with photo upload & parent details
- Add faculty with subject assignment & pay structure
- Assign weekly class routine (day / time / room)
- Record fee payments & download PDF reports
- Pay faculty salary (basic + arrears – deductions)
- Send notifications to all or individual students
- **Control Panel** (`/controlPanel`) — change institute name, logo, add courses & subjects

### Faculty (`/faculty/`)
- Dashboard with class schedule & salary summary
- View upcoming / taken / all classes
- Take attendance with topic recording
- Feed student marks per exam
- Create exams / online quizzes with MCQ questions (image upload supported)

### Student (`/student/`)
- Dashboard with performance graphs
- Attendance records with percentage
- Fee ledger & transaction history
- Notifications inbox
- Attempt online MCQ quizzes with instant result & answer review

### PDF Reports
- `/pdf/feeReport?from=YYYY-MM-DD&to=YYYY-MM-DD` — fee collection
- `/pdf/salaryReport?month=M&year=YYYY` — salary sheet
- `/pdf/examReport?examId=N` — exam result sheet

---

## Technology Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| Web framework | Jakarta Servlet 5.0 + JSP + JSTL 3.0 |
| Database | MySQL 8.x via JDBC |
| PDF generation | OpenPDF 1.3.x |
| Charts | Chart.js 4.x (CDN) |
| Server | Apache Tomcat 10.1 |
| IDE | Eclipse IDE for Enterprise Java |

---

## Troubleshooting

| Problem | Fix |
|---------|-----|
| `ClassNotFoundException: com.mysql.cj.jdbc.Driver` | Add `mysql-connector-j.jar` to `WEB-INF/lib` |
| `NoClassDefFoundError: com/lowagie/...` | Add `openpdf.jar` to `WEB-INF/lib` |
| JSTL tag `${...}` prints as-is | Add both JSTL api + impl jars |
| 404 on any URL | Ensure Tomcat 10.1 is selected as runtime; restart server |
| Login fails for admin | Verify `schema.sql` ran fully; check `db.properties` password |
| Upload fails | Ensure Tomcat has write permission on the `uploads/` folder |
