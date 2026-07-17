<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Create Exam" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<div class="container">
  <h2 class="page-title">Create Exam / Quiz</h2>

  <% if("exam_created".equals(request.getParameter("msg"))){ %>
    <div class="alert alert-success">Exam created! Now add questions below.</div>
  <% } else if("select_exam".equals(request.getParameter("error"))){ %>
    <div class="alert alert-error">Please select an exam to generate the PDF report.</div>
  <% } else if("invalid_exam".equals(request.getParameter("error"))){ %>
    <div class="alert alert-error">Invalid exam selected.</div>
  <% } %>

  <div class="card">
    <h3>New Exam / Quiz</h3>
    <form method="post" action="${pageContext.request.contextPath}/faculty/createExam">
      <div class="form-row">
        <div class="form-group">
          <label>Exam / Quiz Name *</label>
          <input type="text" name="examName" required placeholder="e.g. Physics Unit Test 1"/>
        </div>
        <div class="form-group">
          <label>Course *</label>
          <select name="courseId" id="courseSelect" required onchange="loadSubjects(this.value)">
            <option value="">-- Select Course --</option>
            <c:forEach var="c" items="${courses}">
              <option value="${c.courseId}">${c.courseName}</option>
            </c:forEach>
          </select>
        </div>
        <div class="form-group">
          <label>Subject *</label>
          <select name="subjectId" id="subjectSelect" required>
            <option value="">-- Select Course First --</option>
          </select>
        </div>
      </div>
      <div class="form-row">
        <div class="form-group">
          <label>Exam Date</label>
          <input type="date" name="examDate"/>
        </div>
        <div class="form-group">
          <label>Total Marks *</label>
          <input type="number" name="totalMarks" value="100" required min="1"/>
        </div>
        <div class="form-group">
          <label>Duration (minutes)</label>
          <input type="number" name="durationMinutes" value="60" min="5"/>
        </div>
      </div>
      <div class="form-group">
        <label>
          <input type="checkbox" name="isQuizOnline" value="1"/>
          Make this an Online Quiz (students can attempt on portal)
        </label>
      </div>
      <button type="submit" class="btn">&#43; Create &amp; Add Questions</button>
    </form>
  </div>

  <div class="card">
    <h3>My Exams</h3>
    <table class="data-table">
      <thead>
        <tr><th>Name</th><th>Course</th><th>Subject</th><th>Date</th><th>Marks</th><th>Online Quiz</th><th>Actions</th></tr>
      </thead>
      <tbody>
        <c:forEach var="e" items="${myExams}">
          <tr>
            <td>${e.examName}</td>
            <td>${e.courseName}</td>
            <td>${e.subjectName}</td>
            <td>${e.examDate}</td>
            <td>${e.totalMarks}</td>
            <td>
              <span class="badge ${e.quizOnline ? 'badge-green' : 'badge-blue'}">
                ${e.quizOnline ? 'Yes' : 'No'}
              </span>
            </td>
            <td>
              <a class="btn-sm" href="${pageContext.request.contextPath}/faculty/addQuestion?examId=${e.examId}">Questions</a>
              <a class="btn-sm" href="${pageContext.request.contextPath}/faculty/feedMarks?examId=${e.examId}">Marks</a>
              <a class="btn-sm" href="${pageContext.request.contextPath}/pdf/examReport?examId=${e.examId}" style="background:#e74c3c">PDF</a>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty myExams}">
          <tr><td colspan="7" class="muted">No exams created yet. Create one above.</td></tr>
        </c:if>
      </tbody>
    </table>
  </div>
</div>

<script>
function loadSubjects(courseId) {
  const sel = document.getElementById('subjectSelect');
  sel.innerHTML = '<option value="">-- Loading... --</option>';
  if (!courseId) {
    sel.innerHTML = '<option value="">-- Select Course First --</option>';
    return;
  }
  fetch('${pageContext.request.contextPath}/ajax/subjects?courseId=' + courseId)
    .then(r => r.json())
    .then(data => {
      sel.innerHTML = '<option value="">-- Select Subject --</option>';
      if (data.length === 0) {
        sel.innerHTML = '<option value="">No subjects — add them in Control Panel</option>';
      } else {
        data.forEach(s => {
          sel.innerHTML += '<option value="' + s.subjectId + '">' + s.subjectName + '</option>';
        });
      }
    })
    .catch(() => {
      sel.innerHTML = '<option value="">Error loading subjects</option>';
    });
}
</script>
<jsp:include page="../common/footer.jsp"/>
