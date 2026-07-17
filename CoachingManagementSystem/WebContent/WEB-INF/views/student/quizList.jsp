<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Online Quizzes" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<div class="container">
  <h2 class="page-title">My Quizzes</h2>
  <% if(request.getParameter("msg")!=null){ %><div class="alert alert-info">You have already submitted this quiz.</div><% } %>
  <div class="card">
    <table class="data-table">
      <thead><tr><th>Quiz Name</th><th>Subject</th><th>Total Marks</th><th>Duration</th><th>Action</th></tr></thead>
      <tbody>
        <c:forEach var="q" items="${quizzes}">
          <c:set var="submitted" value="false"/>
          <c:forEach var="a" items="${attempts}">
            <c:if test="${a.examId == q.examId && a.status == 'SUBMITTED'}"><c:set var="submitted" value="true"/></c:if>
          </c:forEach>
          <tr>
            <td>${q.examName}</td>
            <td>${q.subjectName}</td>
            <td>${q.totalMarks}</td>
            <td>${q.durationMinutes} min</td>
            <td>
              <c:choose>
                <c:when test="${submitted}">
                  <span class="badge badge-green">Submitted</span>
                  <a class="btn-sm" href="${pageContext.request.contextPath}/student/quiz/result?attemptId=<c:forEach var='a' items='${attempts}'><c:if test='${a.examId == q.examId}'>${a.attemptId}</c:if></c:forEach>">View Result</a>
                </c:when>
                <c:otherwise>
                  <a class="btn" href="${pageContext.request.contextPath}/student/quiz/attempt?examId=${q.examId}">Start Quiz</a>
                </c:otherwise>
              </c:choose>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty quizzes}"><tr><td colspan="5" class="muted">No quizzes available for your course.</td></tr></c:if>
      </tbody>
    </table>
  </div>
</div>
<jsp:include page="../common/footer.jsp"/>
