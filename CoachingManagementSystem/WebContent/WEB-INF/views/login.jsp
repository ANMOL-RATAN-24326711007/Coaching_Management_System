<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>${not empty config ? config.instituteName : 'CMS'} - Login</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body class="login-body">
<div class="login-wrapper">
  <div class="login-card">
    <div class="login-header">
      <h1>${not empty config ? config.instituteName : 'Coaching Management System'}</h1>
      <p>${not empty config ? config.tagline : 'Excellence in Education'}</p>
    </div>
    <% if (request.getAttribute("error") != null) { %>
      <div class="alert alert-error">${error}</div>
    <% } %>
    <form method="post" action="${pageContext.request.contextPath}/login">
      <div class="form-group">
        <label>Username</label>
        <input type="text" name="username" required placeholder="Enter your username"/>
      </div>
      <div class="form-group">
        <label>Password</label>
        <input type="password" name="password" required placeholder="Enter your password"/>
      </div>
      <button type="submit" class="btn btn-full">Login</button>
    </form>
  </div>
</div>
</body>
</html>
