<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Fee Management" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<div class="container">
  <h2 class="page-title">Fee Management</h2>
  <% if(request.getParameter("msg")!=null){ %><div class="alert alert-success">Payment recorded!</div><% } %>
  <div class="row">
    <div class="card half">
      <h3>Record Payment</h3>
      <form method="post" action="${pageContext.request.contextPath}/head/fees">
        <div class="form-group"><label>Student *</label>
          <select name="studentId" required>
            <option value="">-- Select --</option>
            <c:forEach var="s" items="${students}"><option value="${s.studentId}">${s.name}</option></c:forEach>
          </select></div>
        <div class="form-row">
          <div class="form-group"><label>Total Fee *</label><input type="number" name="totalFee" step="0.01" required/></div>
          <div class="form-group"><label>Amount Paid *</label><input type="number" name="amount" step="0.01" required/></div>
        </div>
        <div class="form-row">
          <div class="form-group"><label>Mode</label>
            <select name="mode"><option>CASH</option><option>ONLINE</option><option>CHEQUE</option></select></div>
          <div class="form-group"><label>Receipt No</label><input type="text" name="receiptNo"/></div>
        </div>
        <div class="form-group"><label>Remarks</label><input type="text" name="remarks"/></div>
        <button type="submit" class="btn">Record Payment</button>
      </form>
    </div>
    <div class="card half">
      <h3>Filter Transactions</h3>
      <form method="get" action="${pageContext.request.contextPath}/head/fees">
        <div class="form-row">
          <div class="form-group"><label>From</label><input type="date" name="from"/></div>
          <div class="form-group"><label>To</label><input type="date" name="to"/></div>
        </div>
        <button type="submit" class="btn">Filter</button>
        <a class="btn" href="${pageContext.request.contextPath}/pdf/feeReport?from=${param.from}&to=${param.to}" style="background:#e74c3c">Download PDF</a>
      </form>
      <p><strong>Total Collected: &#8377;<fmt:formatNumber value="${totalCollected}" pattern="#,##0.00"/></strong></p>
    </div>
  </div>
  <div class="card">
    <h3>Transactions</h3>
    <table class="data-table">
      <thead><tr><th>Receipt</th><th>Student</th><th>Amount</th><th>Date</th><th>Mode</th></tr></thead>
      <tbody>
        <c:forEach var="t" items="${transactions}">
          <tr><td>${t.receiptNo}</td><td>${t.studentName}</td>
              <td>&#8377;<fmt:formatNumber value="${t.amount}" pattern="#,##0.00"/></td>
              <td>${t.txnDate}</td><td>${t.mode}</td></tr>
        </c:forEach>
        <c:if test="${empty transactions}"><tr><td colspan="5" class="muted">No transactions found.</td></tr></c:if>
      </tbody>
    </table>
  </div>
</div>
<jsp:include page="../common/footer.jsp"/>
