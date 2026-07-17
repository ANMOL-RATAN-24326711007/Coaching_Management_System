<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="My Attendance" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<div class="container">
  <h2 class="page-title">My Attendance</h2>
  <div class="stats-grid">
    <div class="stat-card">
      <div class="stat-label">Overall Attendance</div>
      <div class="stat-value">${attendancePct}%</div>
    </div>
  </div>
  <div class="card">
    <table class="data-table">
      <thead><tr><th>Date</th><th>Subject</th><th>Status</th></tr></thead>
      <tbody>
        <c:forEach var="r" items="${records}">
          <tr>
            <td>${r.date}</td>
            <td>${r.extra}</td>
            <td><span class="badge ${r.status == 'PRESENT' ? 'badge-green' : 'badge-red'}">${r.status}</span></td>
          </tr>
        </c:forEach>
        <c:if test="${empty records}"><tr><td colspan="3" class="muted">No attendance records found.</td></tr></c:if>
      </tbody>
    </table>
  </div>
</div>
<jsp:include page="../common/footer.jsp"/>
