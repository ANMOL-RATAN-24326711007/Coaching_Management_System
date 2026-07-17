<%@ page contentType="text/html;charset=UTF-8" isErrorPage="false" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Error — CMS</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<div class="container" style="margin-top:80px;text-align:center">
  <div class="card" style="max-width:560px;margin:auto">
    <h2 style="color:#e74c3c">&#9888; Something went wrong</h2>
    <p style="margin:16px 0">
      ${not empty error ? error : "An unexpected error occurred. Please try again."}
    </p>
    <div style="display:flex;gap:10px;justify-content:center;margin-top:20px">
      <a class="btn" href="javascript:history.back()">&#8592; Go Back</a>
      <a class="btn" href="${pageContext.request.contextPath}/login" style="background:#95a5a6">Login Page</a>
    </div>
  </div>
</div>
</body>
</html>
