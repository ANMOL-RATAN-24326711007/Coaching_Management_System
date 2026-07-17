<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Feed Marks" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<div class="container">
  <h2 class="page-title">Feed Marks</h2>
  <% if(request.getParameter("msg")!=null){ %><div class="alert alert-success">Marks saved successfully.</div><% } %>
  <div class="card">
    <h3>Select Exam</h3>
    <form method="get" action="${pageContext.request.contextPath}/faculty/feedMarks">
      <div class="form-row">
        <div class="form-group">
          <select name="examId" onchange="this.form.submit()">
            <option value="">-- Select Exam --</option>
            <c:forEach var="e" items="${exams}">
              <option value="${e.examId}" ${selectedExam!=null && selectedExam.examId==e.examId?'selected':''}>${e.examName}</option>
            </c:forEach>
          </select>
        </div>
      </div>
    </form>
  </div>
  <c:if test="${not empty selectedExam}">
  <div class="card">
    <h3>Enter Marks for ${selectedExam.examName} (Total: ${selectedExam.totalMarks})</h3>
    <form method="post" action="${pageContext.request.contextPath}/faculty/feedMarks">
      <input type="hidden" name="examId" value="${selectedExam.examId}"/>
      <table class="data-table">
        <thead><tr><th>Student</th><th>Marks Obtained</th><th>Remarks</th></tr></thead>
        <tbody>
          <c:forEach var="s" items="${students}">
            <c:set var="existingMark" value=""/>
            <c:forEach var="m" items="${existingMarks}">
              <c:if test="${m.studentId == s.studentId}"><c:set var="existingMark" value="${m}"/></c:if>
            </c:forEach>
            <input type="hidden" name="studentId" value="${s.studentId}"/>
            <tr>
              <td>${s.name}</td>
              <td><input type="number" name="marks_${s.studentId}" value="${not empty existingMark ? existingMark.marksObtained : ''}" min="0" max="${selectedExam.totalMarks}" step="0.5"/></td>
              <td><input type="text" name="remarks_${s.studentId}" value="${not empty existingMark ? existingMark.remarks : ''}"/></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
      <button type="submit" class="btn">Save Marks</button>
    </form>
  </div>
  </c:if>
</div>
<jsp:include page="../common/footer.jsp"/>
