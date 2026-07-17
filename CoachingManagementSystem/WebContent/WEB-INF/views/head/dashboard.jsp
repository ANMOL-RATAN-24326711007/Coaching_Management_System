<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Head Dashboard" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<div class="container">
  <h2 class="page-title">Institute Dashboard</h2>
  <div class="stats-grid">
    <div class="stat-card">
      <div class="stat-label">Total Students</div>
      <div class="stat-value">${totalStudents}</div>
    </div>
    <div class="stat-card">
      <div class="stat-label">Total Faculty</div>
      <div class="stat-value">${totalFaculty}</div>
    </div>
  </div>

  <div class="row">
    <div class="card half">
      <h3>Enrolment by Course</h3>
      <canvas id="enrolChart" height="250"></canvas>
    </div>
    <div class="card half">
      <h3>Monthly Fee Collection (Last 6 months)</h3>
      <canvas id="feeChart" height="250"></canvas>
    </div>
  </div>

  <div class="row">
    <div class="card half">
      <h3>Quick Actions</h3>
      <div style="display:grid;grid-template-columns:1fr 1fr;gap:10px;margin-top:8px">
        <a class="btn" href="${pageContext.request.contextPath}/head/enrollStudent">&#43; Enroll Student</a>
        <a class="btn" href="${pageContext.request.contextPath}/head/addFaculty">&#43; Add Faculty</a>
        <a class="btn" href="${pageContext.request.contextPath}/head/assignClass">&#128197; Assign Class</a>
        <a class="btn" href="${pageContext.request.contextPath}/head/notifications">&#128276; Send Notice</a>
        <a class="btn" href="${pageContext.request.contextPath}/head/fees">&#8377; Collect Fees</a>
        <a class="btn" href="${pageContext.request.contextPath}/head/salary">&#128176; Pay Salary</a>
      </div>
    </div>
    <div class="card half">
      <h3>PDF Reports</h3>
      <div style="display:flex;flex-direction:column;gap:10px;margin-top:8px">
        <a class="btn" href="${pageContext.request.contextPath}/pdf/feeReport" style="background:#e74c3c">&#128196; Fee Collection Report</a>
        <a class="btn" href="${pageContext.request.contextPath}/pdf/salaryReport" style="background:#e74c3c">&#128196; Salary Report (Current Month)</a>
        <a class="btn" href="${pageContext.request.contextPath}/controlPanel" style="background:#8e44ad">&#9881; Control Panel</a>
      </div>
    </div>
  </div>

  <div class="card">
    <h3>Recent Students</h3>
    <table class="data-table">
      <thead><tr><th>Name</th><th>Course</th><th>Phone</th><th>Admission Date</th><th>Status</th></tr></thead>
      <tbody>
        <c:forEach var="s" items="${recentStudents}" end="9">
          <tr>
            <td>${s.name}</td>
            <td>${s.courseName}</td>
            <td>${s.phone}</td>
            <td>${s.admissionDate}</td>
            <td><span class="badge ${s.status == 'ACTIVE' ? 'badge-green' : 'badge-red'}">${s.status}</span></td>
          </tr>
        </c:forEach>
        <c:if test="${empty recentStudents}">
          <tr><td colspan="5" class="muted">No students enrolled yet.</td></tr>
        </c:if>
      </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/head/enrollStudent" class="btn-sm" style="margin-top:10px;display:inline-block">View All Students</a>
  </div>
</div>

<script>
new Chart(document.getElementById('enrolChart'), {
  type: 'doughnut',
  data: {
    labels: ${enrolmentLabels},
    datasets: [{
      data: ${enrolmentData},
      backgroundColor: ['#3498db','#2ecc71','#e67e22','#9b59b6','#e74c3c']
    }]
  },
  options: { plugins: { legend: { position: 'bottom' } } }
});
new Chart(document.getElementById('feeChart'), {
  type: 'line',
  data: {
    labels: ${feeLabels},
    datasets: [{
      label: 'Fee Collected (Rs)',
      data: ${feeData},
      borderColor: '#27ae60',
      fill: true,
      backgroundColor: 'rgba(39,174,96,0.1)'
    }]
  },
  options: { scales: { y: { beginAtZero: true } }, plugins: { legend: { display: false } } }
});
</script>
<jsp:include page="../common/footer.jsp"/>
