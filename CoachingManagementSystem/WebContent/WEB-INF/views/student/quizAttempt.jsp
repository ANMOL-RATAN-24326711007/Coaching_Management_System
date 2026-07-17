<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Quiz Attempt" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<div class="container">
  <h2 class="page-title">${exam.examName}</h2>
  <p><strong>Duration:</strong> ${exam.durationMinutes} minutes &nbsp;|&nbsp; <strong>Total Marks:</strong> ${exam.totalMarks}</p>
  <form method="post" action="${pageContext.request.contextPath}/student/quiz/attempt">
    <input type="hidden" name="attemptId" value="${attempt.attemptId}"/>
    <input type="hidden" name="examId" value="${exam.examId}"/>
    <c:forEach var="q" items="${questions}" varStatus="s">
      <div class="card question-card">
        <p><strong>Q${s.index+1}.</strong> ${q.questionText} <span class="muted">(${q.marks} mark${q.marks>1?'s':''})</span></p>
        <c:if test="${not empty q.imagePath}">
          <img src="${pageContext.request.contextPath}/${q.imagePath}" alt="Question image" class="question-img"/>
        </c:if>
        <input type="hidden" name="questionId" value="${q.questionId}"/>
        <div class="options">
          <label><input type="radio" name="answer_${q.questionId}" value="A"/> A. ${q.optionA}</label>
          <label><input type="radio" name="answer_${q.questionId}" value="B"/> B. ${q.optionB}</label>
          <label><input type="radio" name="answer_${q.questionId}" value="C"/> C. ${q.optionC}</label>
          <label><input type="radio" name="answer_${q.questionId}" value="D"/> D. ${q.optionD}</label>
        </div>
      </div>
    </c:forEach>
    <button type="submit" class="btn" onclick="return confirm('Submit quiz? You cannot change answers after submission.')">Submit Quiz</button>
  </form>
</div>
<jsp:include page="../common/footer.jsp"/>
