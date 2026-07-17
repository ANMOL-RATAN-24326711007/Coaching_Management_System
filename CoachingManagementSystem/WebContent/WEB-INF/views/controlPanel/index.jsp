<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Control Panel" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<div class="container">
  <h2 class="page-title">&#9881; Control Panel</h2>
  <% if("updated".equals(request.getParameter("msg"))){ %>
    <div class="alert alert-success">Institute settings updated!</div>
  <% } else if("course_added".equals(request.getParameter("msg"))){ %>
    <div class="alert alert-success">Course added successfully!</div>
  <% } else if("subject_added".equals(request.getParameter("msg"))){ %>
    <div class="alert alert-success">Subject added successfully!</div>
  <% } %>

  <!-- Quick Stats -->
  <div class="stats-grid">
    <div class="stat-card"><div class="stat-label">Total Students</div><div class="stat-value">${totalStudents}</div></div>
    <div class="stat-card"><div class="stat-label">Total Faculty</div><div class="stat-value">${totalFaculty}</div></div>
    <div class="stat-card"><div class="stat-label">Courses</div><div class="stat-value">${courses.size()}</div></div>
    <div class="stat-card"><div class="stat-label">Subjects</div><div class="stat-value">${subjects.size()}</div></div>
  </div>

  <!-- Institute Settings -->
  <div class="card">
    <h3>&#127970; Institute Settings</h3>
    <form method="post" action="${pageContext.request.contextPath}/controlPanel" enctype="multipart/form-data">
      <input type="hidden" name="action" value="updateConfig"/>
      <div class="form-row">
        <div class="form-group">
          <label>Institute Name *</label>
          <input type="text" name="instituteName" value="${config.instituteName}" required/>
        </div>
        <div class="form-group">
          <label>Tagline</label>
          <input type="text" name="tagline" value="${config.tagline}"/>
        </div>
      </div>
      <div class="form-row">
        <div class="form-group">
          <label>Contact Phone</label>
          <input type="text" name="contactPhone" value="${config.contactPhone}"/>
        </div>
        <div class="form-group">
          <label>Contact Email</label>
          <input type="email" name="contactEmail" value="${config.contactEmail}"/>
        </div>
      </div>
      <div class="form-group">
        <label>Address</label>
        <textarea name="address" rows="2">${config.address}</textarea>
      </div>
      <div class="form-group">
        <label>Institute Logo</label>
        <input type="file" name="logo" accept="image/*"/>
        <c:if test="${not empty config.logoPath}">
          <br/><img src="${pageContext.request.contextPath}/${config.logoPath}" height="50" style="margin-top:8px;border-radius:4px"/>
        </c:if>
      </div>
      <button type="submit" class="btn">Save Settings</button>
    </form>
  </div>

  <!-- Course Management -->
  <div class="card">
    <h3>&#128218; Add Course</h3>
    <form method="post" action="${pageContext.request.contextPath}/controlPanel">
      <input type="hidden" name="action" value="addCourse"/>
      <div class="form-row">
        <div class="form-group">
          <label>Course Name *</label>
          <input type="text" name="courseName" required placeholder="e.g. JEE Foundation"/>
        </div>
        <div class="form-group">
          <label>Duration (months)</label>
          <input type="number" name="durationMonths" value="12" min="1"/>
        </div>
        <div class="form-group">
          <label>Fee Amount (&#8377;)</label>
          <input type="number" name="feeAmount" step="0.01" value="0" min="0"/>
        </div>
      </div>
      <div class="form-group">
        <label>Description</label>
        <input type="text" name="description" placeholder="Brief description of the course"/>
      </div>
      <button type="submit" class="btn">&#43; Add Course</button>
    </form>
    <table class="data-table" style="margin-top:16px">
      <thead><tr><th>Course</th><th>Duration</th><th>Fee (&#8377;)</th><th>Description</th></tr></thead>
      <tbody>
        <c:forEach var="c" items="${courses}">
          <tr>
            <td><strong>${c.courseName}</strong></td>
            <td>${c.durationMonths} months</td>
            <td>&#8377;${c.feeAmount}</td>
            <td>${c.description}</td>
          </tr>
        </c:forEach>
        <c:if test="${empty courses}">
          <tr><td colspan="4" class="muted">No courses yet.</td></tr>
        </c:if>
      </tbody>
    </table>
  </div>

  <!-- Subject Management -->
  <div class="card">
    <h3>&#128196; Add Subject</h3>
    <form method="post" action="${pageContext.request.contextPath}/controlPanel">
      <input type="hidden" name="action" value="addSubject"/>
      <div class="form-row">
        <div class="form-group">
          <label>Subject Name *</label>
          <input type="text" name="subjectName" required placeholder="e.g. Physics"/>
        </div>
        <div class="form-group">
          <label>Course *</label>
          <select name="courseId" required>
            <option value="">-- Select Course --</option>
            <c:forEach var="c" items="${courses}">
              <option value="${c.courseId}">${c.courseName}</option>
            </c:forEach>
          </select>
        </div>
      </div>
      <button type="submit" class="btn">&#43; Add Subject</button>
    </form>
    <table class="data-table" style="margin-top:16px">
      <thead><tr><th>Subject</th><th>Course</th></tr></thead>
      <tbody>
        <c:forEach var="s" items="${subjects}">
          <tr><td>${s.subjectName}</td><td>${s.courseName}</td></tr>
        </c:forEach>
        <c:if test="${empty subjects}">
          <tr><td colspan="2" class="muted">No subjects yet.</td></tr>
        </c:if>
      </tbody>
    </table>
  </div>
</div>
<jsp:include page="../common/footer.jsp"/>
