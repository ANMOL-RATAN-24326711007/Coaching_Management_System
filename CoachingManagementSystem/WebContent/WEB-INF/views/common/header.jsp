<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>${not empty config ? config.instituteName : 'CMS'} - ${pageTitle}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
  <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js"></script>
</head>
<body>
<header class="topbar">
  <div class="topbar-brand">
    <c:if test="${not empty config.logoPath}">
      <img src="${pageContext.request.contextPath}/${config.logoPath}" alt="Logo" class="brand-logo"/>
    </c:if>
    <span>${not empty config ? config.instituteName : 'Coaching Management System'}</span>
  </div>
  <nav class="topbar-nav">
    <c:choose>
      <c:when test="${sessionScope.ROLE == 'STUDENT'}">
        <a href="${pageContext.request.contextPath}/student/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/student/attendance">Attendance</a>
        <a href="${pageContext.request.contextPath}/student/fees">Fees</a>
        <a href="${pageContext.request.contextPath}/student/notifications">Notifications</a>
        <a href="${pageContext.request.contextPath}/student/quizzes">Quizzes</a>
      </c:when>
      <c:when test="${sessionScope.ROLE == 'FACULTY'}">
        <a href="${pageContext.request.contextPath}/faculty/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/faculty/classes">Classes</a>
        <a href="${pageContext.request.contextPath}/faculty/feedMarks">Feed Marks</a>
        <a href="${pageContext.request.contextPath}/faculty/createExam">Create Exam</a>
      </c:when>
      <c:when test="${sessionScope.ROLE == 'HEAD'}">
        <a href="${pageContext.request.contextPath}/head/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/head/enrollStudent">Students</a>
        <a href="${pageContext.request.contextPath}/head/addFaculty">Faculty</a>
        <a href="${pageContext.request.contextPath}/head/assignClass">Classes</a>
        <a href="${pageContext.request.contextPath}/head/fees">Fees</a>
        <a href="${pageContext.request.contextPath}/head/salary">Salary</a>
        <a href="${pageContext.request.contextPath}/head/notifications">Notifications</a>
        <a href="${pageContext.request.contextPath}/controlPanel">&#9881; Control Panel</a>
      </c:when>
    </c:choose>
    <a href="${pageContext.request.contextPath}/logout" class="btn-logout">Logout</a>
  </nav>
</header>
<div class="page-content">
