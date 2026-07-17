<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Assign Class" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<div class="container">
  <h2 class="page-title">Assign Class / Routine</h2>

  <% if("assigned".equals(request.getParameter("msg"))){ %>
    <div class="alert alert-success">Class routine assigned successfully!</div>
  <% } else if("deleted".equals(request.getParameter("msg"))){ %>
    <div class="alert alert-success">Class routine removed.</div>
  <% } %>

  <c:if test="${not empty error}">
    <div class="alert alert-error">${error}</div>
  </c:if>

  <div class="card">
    <h3>Add New Class Routine</h3>
    <form method="post" action="${pageContext.request.contextPath}/head/assignClass">
      <div class="form-row">
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
        <div class="form-group">
          <label>Faculty *</label>
          <select name="facultyId" required>
            <option value="">-- Select Faculty --</option>
            <c:forEach var="f" items="${faculties}">
              <option value="${f.facultyId}">${f.name}</option>
            </c:forEach>
          </select>
        </div>
      </div>
      <div class="form-row">
        <div class="form-group">
          <label>Day of Week *</label>
          <select name="dayOfWeek" required>
            <option value="MONDAY">Monday</option>
            <option value="TUESDAY">Tuesday</option>
            <option value="WEDNESDAY">Wednesday</option>
            <option value="THURSDAY">Thursday</option>
            <option value="FRIDAY">Friday</option>
            <option value="SATURDAY">Saturday</option>
            <option value="SUNDAY">Sunday</option>
          </select>
        </div>
        <div class="form-group">
          <label>Start Time *</label>
          <input type="time" name="startTime" required/>
        </div>
        <div class="form-group">
          <label>End Time *</label>
          <input type="time" name="endTime" required/>
        </div>
        <div class="form-group">
          <label>Room / Location</label>
          <input type="text" name="room" placeholder="e.g. Room A"/>
        </div>
      </div>
      <button type="submit" class="btn">&#43; Assign Class</button>
    </form>
  </div>

  <div class="card">
    <h3>Existing Class Routines</h3>
    <table class="data-table">
      <thead>
        <tr>
          <th>Course</th><th>Subject</th><th>Faculty</th>
          <th>Day</th><th>Time</th><th>Room</th><th>Action</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="r" items="${routines}">
          <tr>
            <td>${r.courseName}</td>
            <td>${r.subjectName}</td>
            <td>${r.facultyName}</td>
            <td>${r.dayOfWeek}</td>
            <td>${r.startTime} - ${r.endTime}</td>
            <td>${r.room}</td>
            <td>
              <form method="post" action="${pageContext.request.contextPath}/head/assignClass"
                    style="display:inline"
                    onsubmit="return confirm('Remove this class routine?')">
                <input type="hidden" name="action" value="delete"/>
                <input type="hidden" name="routineId" value="${r.routineId}"/>
                <button type="submit" class="btn-sm" style="background:#e74c3c">Remove</button>
              </form>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty routines}">
          <tr><td colspan="7" class="muted">No class routines assigned yet.</td></tr>
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
        sel.innerHTML = '<option value="">No subjects for this course</option>';
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
