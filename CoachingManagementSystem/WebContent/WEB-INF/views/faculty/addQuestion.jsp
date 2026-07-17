<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Add Questions" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<div class="container">
  <h2 class="page-title">Add Questions — ${exam.examName}</h2>
  <% if(request.getParameter("msg")!=null){ %><div class="alert alert-success">Question added!</div><% } %>
  <div class="card">
    <form method="post" action="${pageContext.request.contextPath}/faculty/addQuestion" enctype="multipart/form-data">
      <input type="hidden" name="examId" value="${exam.examId}"/>
      <div class="form-group"><label>Question Text *</label><textarea name="questionText" rows="3" required></textarea></div>
      <div class="form-group"><label>Question Image (optional)</label><input type="file" name="questionImage" accept="image/*"/></div>
      <div class="form-row">
        <div class="form-group"><label>Option A *</label><input type="text" name="optionA" required/></div>
        <div class="form-group"><label>Option B *</label><input type="text" name="optionB" required/></div>
        <div class="form-group"><label>Option C *</label><input type="text" name="optionC" required/></div>
        <div class="form-group"><label>Option D *</label><input type="text" name="optionD" required/></div>
      </div>
      <div class="form-row">
        <div class="form-group">
          <label>Correct Answer *</label>
          <select name="correctOption" required>
            <option value="A">A</option><option value="B">B</option><option value="C">C</option><option value="D">D</option>
          </select>
        </div>
        <div class="form-group"><label>Marks *</label><input type="number" name="marks" value="1" min="1" required/></div>
      </div>
      <button type="submit" class="btn">Add Question</button>
      <a class="btn" style="background:#27ae60" href="${pageContext.request.contextPath}/faculty/createExam">Back to Exams</a>
    </form>
  </div>
  <div class="card">
    <h3>Questions (${questions.size()})</h3>
    <c:forEach var="q" items="${questions}" varStatus="s">
      <div class="question-card">
        <p><strong>Q${s.index+1}.</strong> ${q.questionText} <span class="muted">(${q.marks} marks)</span></p>
        <c:if test="${not empty q.imagePath}"><img src="${pageContext.request.contextPath}/${q.imagePath}" class="question-img"/></c:if>
        <div class="options muted">
          <span>A: ${q.optionA}</span> <span>B: ${q.optionB}</span> <span>C: ${q.optionC}</span> <span>D: ${q.optionD}</span>
          <span>&nbsp;|&nbsp; Answer: <strong>${q.correctOption}</strong></span>
        </div>
      </div>
    </c:forEach>
    <c:if test="${empty questions}"><p class="muted">No questions added yet.</p></c:if>
  </div>
</div>
<jsp:include page="../common/footer.jsp"/>
