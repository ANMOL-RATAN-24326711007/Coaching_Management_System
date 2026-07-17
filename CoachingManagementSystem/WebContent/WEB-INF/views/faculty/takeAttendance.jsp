<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Take Attendance" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<div class="container">
  <h2 class="page-title">Take Attendance &mdash; ${classSession.subjectName} (${classSession.sessionDate})</h2>
  <c:if test="${empty classSession}">
    <div class="alert alert-error">Session not found. <a href="${pageContext.request.contextPath}/faculty/classes">Back to Classes</a></div>
  </c:if>

  <c:if test="${not empty classSession}">
  <form method="post" action="${pageContext.request.contextPath}/faculty/takeAttendance">
    <input type="hidden" name="sessionId" value="${classSession.sessionId}"/>
    <div class="card">
      <div class="form-group">
        <label>Topic Covered</label>
        <input type="text" name="topic" placeholder="Enter topic covered in this class session"
               value="${not empty classSession.topic ? classSession.topic : ''}"/>
      </div>
    </div>
    <div class="card">
      <h3>Student Attendance</h3>
      <table class="data-table">
        <thead>
          <tr><th>#</th><th>Student Name</th><th>Status</th></tr>
        </thead>
        <tbody>
          <c:forEach var="row" items="${students}" varStatus="s">
            <c:set var="sid"   value="${row[0]}"/>
            <c:set var="sname" value="${row[1]}"/>
            <c:set var="sstat" value="${row[2]}"/>
            <input type="hidden" name="studentId" value="${sid}"/>
            <tr>
              <td>${s.index + 1}</td>
              <td>${sname}</td>
              <td>
                <label style="margin-right:16px">
                  <input type="radio" name="status_${sid}" value="PRESENT"
                    <c:if test="${sstat != 'ABSENT'}">checked</c:if>/> Present
                </label>
                <label>
                  <input type="radio" name="status_${sid}" value="ABSENT"
                    <c:if test="${sstat == 'ABSENT'}">checked</c:if>/> Absent
                </label>
              </td>
            </tr>
          </c:forEach>
          <c:if test="${empty students}">
            <tr><td colspan="3" class="muted">No students enrolled in this course.</td></tr>
          </c:if>
        </tbody>
      </table>
    </div>
    <c:if test="${not empty students}">
      <button type="submit" class="btn">Save Attendance &amp; Topic</button>
    </c:if>
    <a href="${pageContext.request.contextPath}/faculty/classes" class="btn" style="background:#95a5a6;margin-left:8px">Cancel</a>
  </form>
  </c:if>
</div>
<jsp:include page="../common/footer.jsp"/>