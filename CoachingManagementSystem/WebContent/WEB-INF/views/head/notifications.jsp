<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Notifications" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<div class="container">
  <h2 class="page-title">&#128276; Send Notifications</h2>
  <% if("sent".equals(request.getParameter("msg"))){ %>
    <div class="alert alert-success">Notification sent successfully!</div>
  <% } %>

  <div class="row">
    <div class="card half">
      <h3>Send New Notification</h3>
      <form method="post" action="${pageContext.request.contextPath}/head/notifications">
        <div class="form-group">
          <label>Send To</label>
          <select name="studentId">
            <option value="0">&#127758; All Students (Broadcast)</option>
            <c:forEach var="s" items="${students}">
              <option value="${s.studentId}">${s.name} (${s.courseName})</option>
            </c:forEach>
          </select>
        </div>
        <div class="form-group">
          <label>Title *</label>
          <input type="text" name="title" required placeholder="e.g. Holiday Notice"/>
        </div>
        <div class="form-group">
          <label>Message *</label>
          <textarea name="message" rows="5" required placeholder="Type your notification message here..."></textarea>
        </div>
        <button type="submit" class="btn">&#128276; Send Notification</button>
      </form>
    </div>
    <div class="card half">
      <h3>Sent Notifications</h3>
      <c:choose>
        <c:when test="${empty notifications}">
          <p class="muted">No notifications sent yet.</p>
        </c:when>
        <c:otherwise>
          <c:forEach var="n" items="${notifications}">
            <div class="notif-item">
              <strong>${n.title}</strong>
              <span class="badge ${n.studentId != null ? 'badge-blue' : 'badge-green'}" style="margin-left:8px">
                ${n.studentId != null ? 'Personal' : 'Broadcast'}
              </span>
              <p>${n.message}</p>
              <small class="muted">
                <fmt:formatDate value="${n.createdAt}" pattern="dd MMM yyyy HH:mm"/>
              </small>
            </div>
          </c:forEach>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>
<jsp:include page="../common/footer.jsp"/>
