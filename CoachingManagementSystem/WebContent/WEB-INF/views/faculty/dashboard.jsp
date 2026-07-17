<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Faculty Dashboard" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<div class="container">
  <h2 class="page-title">Welcome, ${faculty.name}</h2>

  <div class="stats-grid">
    <div class="stat-card">
      <div class="stat-label">My Attendance</div>
      <div class="stat-value">${attendancePct}%</div>
    </div>
    <div class="stat-card">
      <div class="stat-label">Upcoming Classes</div>
      <div class="stat-value">${upcomingClasses.size()}</div>
    </div>
    <div class="stat-card">
      <div class="stat-label">Classes Taken</div>
      <div class="stat-value">${takenClasses.size()}</div>
    </div>
    <div class="stat-card">
      <div class="stat-label">Exams Created</div>
      <div class="stat-value">${exams.size()}</div>
    </div>
  </div>

  <div class="row">
    <div class="card half">
      <h3>Upcoming Classes</h3>
      <c:forEach var="s" items="${upcomingClasses}" end="4">
        <div class="list-item">
          <strong>${s.subjectName}</strong> &mdash; ${s.sessionDate} (${s.courseName})<br/>
          <small>${s.startTime} - ${s.endTime} | Room: ${s.room}</small><br/>
          <a class="btn-sm" style="margin-top:6px;display:inline-block"
             href="${pageContext.request.contextPath}/faculty/takeAttendance?sessionId=${s.sessionId}">Enter Class</a>
        </div>
      </c:forEach>
      <c:if test="${empty upcomingClasses}">
        <p class="muted">No upcoming classes scheduled.</p>
      </c:if>
      <a href="${pageContext.request.contextPath}/faculty/classes" class="btn-sm" style="margin-top:10px;display:inline-block">View All Classes</a>
    </div>

    <div class="card half">
      <h3>Salary History</h3>
      <table class="data-table">
        <thead><tr><th>Month/Year</th><th>Basic Pay</th><th>Net Pay</th></tr></thead>
        <tbody>
          <c:forEach var="s" items="${salaryHistory}" end="4">
            <tr>
              <td>${s.payMonth}/${s.payYear}</td>
              <td>&#8377;<fmt:formatNumber value="${s.basicPay}" pattern="#,##0.00"/></td>
              <td><strong>&#8377;<fmt:formatNumber value="${s.netPay}" pattern="#,##0.00"/></strong></td>
            </tr>
          </c:forEach>
          <c:if test="${empty salaryHistory}">
            <tr><td colspan="3" class="muted">No salary records yet.</td></tr>
          </c:if>
        </tbody>
      </table>
    </div>
  </div>

  <div class="card">
    <h3>My Exams</h3>
    <table class="data-table">
      <thead><tr><th>Exam Name</th><th>Course</th><th>Date</th><th>Total Marks</th><th>Online Quiz</th><th>Actions</th></tr></thead>
      <tbody>
        <c:forEach var="e" items="${exams}">
          <tr>
            <td>${e.examName}</td>
            <td>${e.courseName}</td>
            <td>${e.examDate}</td>
            <td>${e.totalMarks}</td>
            <td>${e.quizOnline ? 'Yes' : 'No'}</td>
            <td>
              <a class="btn-sm" href="${pageContext.request.contextPath}/faculty/addQuestion?examId=${e.examId}">Questions</a>
              <a class="btn-sm" href="${pageContext.request.contextPath}/faculty/feedMarks?examId=${e.examId}">Marks</a>
              <a class="btn-sm" href="${pageContext.request.contextPath}/pdf/examReport?examId=${e.examId}" style="background:#e74c3c">PDF</a>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty exams}">
          <tr><td colspan="6" class="muted">No exams created yet. <a href="${pageContext.request.contextPath}/faculty/createExam">Create one</a></td></tr>
        </c:if>
      </tbody>
    </table>
  </div>
</div>
<jsp:include page="../common/footer.jsp"/>
