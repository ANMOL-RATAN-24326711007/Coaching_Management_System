<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="My Fees" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<div class="container">
  <h2 class="page-title">Fee Details</h2>
  <c:if test="${not empty feeStructure}">
  <div class="stats-grid">
    <div class="stat-card"><div class="stat-label">Total Fee</div><div class="stat-value">&#8377;<fmt:formatNumber value="${feeStructure.totalAmount}" pattern="#,##0.00"/></div></div>
    <div class="stat-card"><div class="stat-label">Paid</div><div class="stat-value" style="color:#27ae60">&#8377;<fmt:formatNumber value="${feeStructure.paidAmount}" pattern="#,##0.00"/></div></div>
    <div class="stat-card"><div class="stat-label">Balance</div><div class="stat-value" style="color:#e74c3c">&#8377;<fmt:formatNumber value="${feeStructure.balanceAmount}" pattern="#,##0.00"/></div></div>
    <c:if test="${not empty feeStructure.dueDate}"><div class="stat-card"><div class="stat-label">Due Date</div><div class="stat-value">${feeStructure.dueDate}</div></div></c:if>
  </div>
  </c:if>
  <div class="card">
    <h3>Transaction History</h3>
    <table class="data-table">
      <thead><tr><th>Receipt No</th><th>Date</th><th>Amount</th><th>Mode</th><th>Remarks</th></tr></thead>
      <tbody>
        <c:forEach var="t" items="${transactions}">
          <tr>
            <td>${t.receiptNo}</td>
            <td>${t.txnDate}</td>
            <td>&#8377;<fmt:formatNumber value="${t.amount}" pattern="#,##0.00"/></td>
            <td>${t.mode}</td>
            <td>${t.remarks}</td>
          </tr>
        </c:forEach>
        <c:if test="${empty transactions}"><tr><td colspan="5" class="muted">No transactions found.</td></tr></c:if>
      </tbody>
    </table>
  </div>
</div>
<jsp:include page="../common/footer.jsp"/>
