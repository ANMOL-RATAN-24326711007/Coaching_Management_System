<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Notifications" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<div class="container">
  <h2 class="page-title">Notifications</h2>
  <div class="card">
    <c:forEach var="n" items="${notifications}">
      <div class="notif-item">
        <strong>${n.title}</strong>
        <p>${n.message}</p>
        <small class="muted"><fmt:formatDate value="${n.createdAt}" pattern="dd MMM yyyy HH:mm"/></small>
      </div>
    </c:forEach>
    <c:if test="${empty notifications}"><p class="muted">No notifications.</p></c:if>
  </div>
</div>
<jsp:include page="../common/footer.jsp"/>
