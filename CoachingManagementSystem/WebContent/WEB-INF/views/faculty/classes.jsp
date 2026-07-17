<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="My Classes" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<div class="container">
  <h2 class="page-title">My Classes</h2>
  <% if("attendance_saved".equals(request.getParameter("msg"))){ %>
    <div class="alert alert-success">Attendance &amp; topic saved successfully!</div>
  <% } %>

  <div class="tabs">
    <button class="tab-btn active" onclick="showTab('upcoming', this)">Upcoming (${upcomingClasses.size()})</button>
    <button class="tab-btn" onclick="showTab('taken', this)">Taken (${takenClasses.size()})</button>
    <button class="tab-btn" onclick="showTab('all', this)">All</button>
  </div>

  <div id="upcoming" class="tab-content active card">
    <table class="data-table">
      <thead><tr><th>Date</th><th>Subject</th><th>Course</th><th>Time</th><th>Room</th><th>Action</th></tr></thead>
      <tbody>
        <c:forEach var="s" items="${upcomingClasses}">
          <tr>
            <td>${s.sessionDate}</td>
            <td>${s.subjectName}</td>
            <td>${s.courseName}</td>
            <td>${s.startTime} &ndash; ${s.endTime}</td>
            <td>${s.room}</td>
            <td>
              <a class="btn-sm" href="${pageContext.request.contextPath}/faculty/takeAttendance?sessionId=${s.sessionId}">
                &#9654; Enter Class
              </a>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty upcomingClasses}">
          <tr><td colspan="6" class="muted">No upcoming classes. Your routines will appear here automatically.</td></tr>
        </c:if>
      </tbody>
    </table>
  </div>

  <div id="taken" class="tab-content card" style="display:none">
    <table class="data-table">
      <thead><tr><th>Date</th><th>Subject</th><th>Course</th><th>Topic Covered</th><th>Time</th></tr></thead>
      <tbody>
        <c:forEach var="s" items="${takenClasses}">
          <tr>
            <td>${s.sessionDate}</td>
            <td>${s.subjectName}</td>
            <td>${s.courseName}</td>
            <td>${not empty s.topic ? s.topic : '<span class="muted">—</span>'}</td>
            <td>${s.startTime} &ndash; ${s.endTime}</td>
          </tr>
        </c:forEach>
        <c:if test="${empty takenClasses}">
          <tr><td colspan="5" class="muted">No classes taken yet.</td></tr>
        </c:if>
      </tbody>
    </table>
  </div>

  <div id="all" class="tab-content card" style="display:none">
    <table class="data-table">
      <thead><tr><th>Date</th><th>Subject</th><th>Course</th><th>Status</th><th>Topic</th></tr></thead>
      <tbody>
        <c:forEach var="s" items="${allClasses}">
          <tr>
            <td>${s.sessionDate}</td>
            <td>${s.subjectName}</td>
            <td>${s.courseName}</td>
            <td>
              <span class="badge ${s.status == 'TAKEN' ? 'badge-green' : s.status == 'CANCELLED' ? 'badge-red' : 'badge-blue'}">
                ${s.status}
              </span>
            </td>
            <td>${s.topic}</td>
          </tr>
        </c:forEach>
        <c:if test="${empty allClasses}">
          <tr><td colspan="5" class="muted">No class sessions found.</td></tr>
        </c:if>
      </tbody>
    </table>
  </div>
</div>

<script>
function showTab(id, btn) {
  document.querySelectorAll('.tab-content').forEach(el => el.style.display = 'none');
  document.querySelectorAll('.tab-btn').forEach(el => el.classList.remove('active'));
  document.getElementById(id).style.display = '';
  btn.classList.add('active');
}
</script>
<jsp:include page="../common/footer.jsp"/>
