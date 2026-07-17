<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Enroll Student" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<div class="container">
  <h2 class="page-title">Enroll New Student</h2>
  <% if(request.getParameter("msg")!=null){ %><div class="alert alert-success">Student enrolled successfully!</div><% } %>
  <div class="card">
    <form method="post" action="${pageContext.request.contextPath}/head/enrollStudent" enctype="multipart/form-data">
      <h3>Student Information</h3>
      <div class="form-row">
        <div class="form-group"><label>Full Name *</label><input type="text" name="name" required/></div>
        <div class="form-group"><label>Date of Birth</label><input type="date" name="dob"/></div>
        <div class="form-group"><label>Gender</label>
          <select name="gender"><option>Male</option><option>Female</option><option>Other</option></select></div>
      </div>
      <div class="form-row">
        <div class="form-group"><label>Phone</label><input type="text" name="phone"/></div>
        <div class="form-group"><label>Email</label><input type="email" name="email"/></div>
        <div class="form-group"><label>Course *</label>
          <select name="courseId" required>
            <option value="">-- Select --</option>
            <c:forEach var="c" items="${courses}"><option value="${c.courseId}">${c.courseName}</option></c:forEach>
          </select></div>
      </div>
      <div class="form-group"><label>Address</label><textarea name="address" rows="2"></textarea></div>
      <div class="form-group"><label>Photo</label><input type="file" name="photo" accept="image/*"/></div>
      <hr/><h3>Parent / Guardian Information</h3>
      <div class="form-row">
        <div class="form-group"><label>Parent Name *</label><input type="text" name="parentName" required/></div>
        <div class="form-group"><label>Parent Phone</label><input type="text" name="parentPhone"/></div>
        <div class="form-group"><label>Parent Email</label><input type="email" name="parentEmail"/></div>
      </div>
      <div class="form-row">
        <div class="form-group"><label>Occupation</label><input type="text" name="parentOccupation"/></div>
        <div class="form-group"><label>Parent Address</label><input type="text" name="parentAddress"/></div>
      </div>
      <hr/><h3>Login Credentials</h3>
      <div class="form-row">
        <div class="form-group"><label>Username</label><input type="text" name="username"/></div>
        <div class="form-group"><label>Password</label><input type="password" name="password"/></div>
      </div>
      <button type="submit" class="btn">Enroll Student</button>
    </form>
  </div>
  <div class="card">
    <h3>All Students</h3>
    <table class="data-table">
      <thead><tr><th>Name</th><th>Course</th><th>Phone</th><th>Status</th></tr></thead>
      <tbody>
        <c:forEach var="s" items="${students}">
          <tr><td>${s.name}</td><td>${s.courseName}</td><td>${s.phone}</td>
              <td><span class="badge ${s.status=='ACTIVE'?'badge-green':'badge-red'}">${s.status}</span></td></tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>
<jsp:include page="../common/footer.jsp"/>
