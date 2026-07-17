<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Student Dashboard" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<div class="container">
  <h2 class="page-title">Welcome, ${student.name}</h2>
  <% if(request.getParameter("msg") != null){ %><div class="alert alert-success">Operation successful.</div><% } %>

  <div class="stats-grid">
    <div class="stat-card">
      <div class="stat-label">Attendance</div>
      <div class="stat-value">${attendancePct}%</div>
    </div>
    <div class="stat-card">
      <div class="stat-label">Avg Score</div>
      <div class="stat-value">${avgMarksPct}%</div>
    </div>
    <div class="stat-card">
      <div class="stat-label">Course</div>
      <div class="stat-value" style="font-size:1.1rem">${student.courseName}</div>
    </div>
    <c:if test="${not empty feeStructure}">
    <div class="stat-card">
      <div class="stat-label">Fee Balance</div>
      <div class="stat-value">&#8377;<fmt:formatNumber value="${feeStructure.balanceAmount}" pattern="#,##0"/></div>
    </div>
    </c:if>
  </div>

  <div class="row">
    <div class="card half">
      <h3>Exam Performance</h3>
      <canvas id="marksChart" height="220"></canvas>
    </div>
    <div class="card half">
      <h3>Recent Notifications</h3>
      <c:forEach var="n" items="${notifications}" end="4">
        <div class="notif-item">
          <strong>${n.title}</strong>
          <p>${n.message}</p>
          <small class="muted"><fmt:formatDate value="${n.createdAt}" pattern="dd MMM yyyy"/></small>
        </div>
      </c:forEach>
      <c:if test="${empty notifications}"><p class="muted">No notifications yet.</p></c:if>
      <a href="${pageContext.request.contextPath}/student/notifications" class="btn-sm" style="margin-top:10px;display:inline-block">View All</a>
    </div>
  </div>

  <div class="card">
    <h3>My Results</h3>
    <table class="data-table">
      <thead><tr><th>Exam</th><th>Marks Obtained</th><th>Total</th><th>%</th><th>Remarks</th></tr></thead>
      <tbody>
        <c:forEach var="m" items="${marks}">
          <tr>
            <td>${m.examName}</td>
            <td>${m.marksObtained}</td>
            <td>${m.totalMarks}</td>
            <td><fmt:formatNumber value="${m.marksObtained * 100 / m.totalMarks}" maxFractionDigits="1"/>%</td>
            <td>${m.remarks}</td>
          </tr>
        </c:forEach>
        <c:if test="${empty marks}"><tr><td colspan="5" class="muted">No results recorded yet.</td></tr></c:if>
      </tbody>
    </table>
  </div>

  <div class="card">
    <h3>Available Online Quizzes</h3>
    <a href="${pageContext.request.contextPath}/student/quizzes" class="btn">Go to Quizzes</a>
  </div>
</div>

<script>
const labels = [<c:forEach var="m" items="${marks}">'${m.examName}',</c:forEach>];
const scores = [<c:forEach var="m" items="${marks}">${m.totalMarks > 0 ? m.marksObtained * 100 / m.totalMarks : 0},</c:forEach>];
new Chart(document.getElementById('marksChart'), {
  type: 'bar',
  data: { labels, datasets: [{ label: 'Score %', data: scores, backgroundColor: '#3498db' }] },
  options: { scales: { y: { beginAtZero: true, max: 100 } }, plugins: { legend: { display: false } } }
});
</script>
<jsp:include page="../common/footer.jsp"/>
