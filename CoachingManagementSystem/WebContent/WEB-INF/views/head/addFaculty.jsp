<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Add Faculty" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<div class="container">
  <h2 class="page-title">Add Faculty Member</h2>
  <% if(request.getParameter("msg")!=null){ %><div class="alert alert-success">Faculty added successfully!</div><% } %>
  <div class="card">
    <form method="post" action="${pageContext.request.contextPath}/head/addFaculty" enctype="multipart/form-data">
      <div class="form-row">
        <div class="form-group"><label>Full Name *</label><input type="text" name="name" required/></div>
        <div class="form-group"><label>Date of Birth</label><input type="date" name="dob"/></div>
        <div class="form-group"><label>Gender</label>
          <select name="gender"><option>Male</option><option>Female</option><option>Other</option></select></div>
      </div>
      <div class="form-row">
        <div class="form-group"><label>Phone</label><input type="text" name="phone"/></div>
        <div class="form-group"><label>Email</label><input type="email" name="email"/></div>
        <div class="form-group"><label>Qualification</label><input type="text" name="qualification"/></div>
      </div>
      <div class="form-row">
        <div class="form-group"><label>Subject *</label>
          <select name="subjectId" required>
            <option value="">-- Select --</option>
            <c:forEach var="s" items="${subjects}"><option value="${s.subjectId}">${s.subjectName} (${s.courseName})</option></c:forEach>
          </select></div>
        <div class="form-group"><label>Basic Pay (Rs) *</label><input type="number" name="basicPay" step="0.01" required/></div>
      </div>
      <div class="form-group"><label>Address</label><textarea name="address" rows="2"></textarea></div>
      <div class="form-group"><label>Photo</label><input type="file" name="photo" accept="image/*"/></div>
      <hr/><h3>Login Credentials</h3>
      <div class="form-row">
        <div class="form-group"><label>Username</label><input type="text" name="username"/></div>
        <div class="form-group"><label>Password</label><input type="password" name="password"/></div>
      </div>
      <button type="submit" class="btn">Add Faculty</button>
    </form>
  </div>
  <div class="card">
    <h3>All Faculty</h3>
    <table class="data-table">
      <thead><tr><th>Name</th><th>Subject</th><th>Phone</th><th>Basic Pay</th><th>Status</th></tr></thead>
      <tbody>
        <c:forEach var="f" items="${faculties}">
          <tr><td>${f.name}</td><td>${f.subjectName}</td><td>${f.phone}</td>
              <td>&#8377;${f.basicPay}</td>
              <td><span class="badge ${f.status=='ACTIVE'?'badge-green':'badge-red'}">${f.status}</span></td></tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>
<jsp:include page="../common/footer.jsp"/>
