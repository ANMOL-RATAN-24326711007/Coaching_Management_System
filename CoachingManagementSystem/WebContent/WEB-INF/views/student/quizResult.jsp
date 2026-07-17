<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Quiz Result" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<div class="container">
  <h2 class="page-title">Quiz Result</h2>
  <div class="stats-grid">
    <div class="stat-card"><div class="stat-label">Score</div><div class="stat-value">${attempt.score} / ${attempt.totalMarks}</div></div>
    <div class="stat-card"><div class="stat-label">Status</div><div class="stat-value">${attempt.status}</div></div>
  </div>
  <div class="card">
    <c:forEach var="r" items="${responses}" varStatus="s">
      <div class="question-card ${r.correct ? 'correct' : 'incorrect'}">
        <p><strong>Q${s.index+1}.</strong> ${r.questionText}</p>
        <c:if test="${not empty r.imagePath}"><img src="${pageContext.request.contextPath}/${r.imagePath}" class="question-img"/></c:if>
        <p>Your answer: <strong>${not empty r.selectedOption ? r.selectedOption : 'Skipped'}</strong>
           &nbsp;&mdash;&nbsp; Correct: <strong>${r.correctOption}</strong>
           &nbsp;<span class="${r.correct ? 'badge badge-green' : 'badge badge-red'}">${r.correct ? 'Correct' : 'Wrong'}</span>
        </p>
        <div class="options muted">
          <div>A: ${r.optionA}</div><div>B: ${r.optionB}</div><div>C: ${r.optionC}</div><div>D: ${r.optionD}</div>
        </div>
      </div>
    </c:forEach>
  </div>
  <a class="btn" href="${pageContext.request.contextPath}/student/quizzes">Back to Quizzes</a>
</div>
<jsp:include page="../common/footer.jsp"/>
