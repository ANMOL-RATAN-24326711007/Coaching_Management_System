<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Salary Management" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<div class="container">
  <h2 class="page-title">Salary Management</h2>
  <% if("paid".equals(request.getParameter("msg"))){ %>
    <div class="alert alert-success">Salary payment recorded successfully!</div>
  <% } %>

  <div class="row">
    <div class="card half">
      <h3>Pay Salary</h3>
      <form method="post" action="${pageContext.request.contextPath}/head/salary" id="salaryForm">
        <div class="form-group">
          <label>Faculty *</label>
          <select name="facultyId" id="facultySelect" required onchange="fillBasicPay(this)">
            <option value="">-- Select Faculty --</option>
            <c:forEach var="f" items="${faculties}">
              <option value="${f.facultyId}" data-pay="${f.basicPay}">${f.name} (Basic: &#8377;${f.basicPay})</option>
            </c:forEach>
          </select>
        </div>
        <div class="form-row">
          <div class="form-group">
            <label>Month *</label>
            <input type="number" name="payMonth" id="payMonth" min="1" max="12" value="${selectedMonth}" required/>
          </div>
          <div class="form-group">
            <label>Year *</label>
            <input type="number" name="payYear" id="payYear" value="${selectedYear}" required/>
          </div>
        </div>
        <div class="form-row">
          <div class="form-group">
            <label>Basic Pay *</label>
            <input type="number" name="basicPay" id="basicPayInput" step="0.01" required min="0"/>
          </div>
          <div class="form-group">
            <label>Arrears</label>
            <input type="number" name="arrears" value="0" step="0.01" min="0"/>
          </div>
          <div class="form-group">
            <label>Deductions</label>
            <input type="number" name="deductions" value="0" step="0.01" min="0"/>
          </div>
        </div>
        <div class="form-group">
          <label>Remarks</label>
          <input type="text" name="remarks" placeholder="Optional remarks"/>
        </div>
        <button type="submit" class="btn">&#128176; Record Payment</button>
      </form>
    </div>

    <div class="card half">
      <h3>Filter by Month / Year</h3>
      <form method="get" action="${pageContext.request.contextPath}/head/salary" id="filterForm">
        <div class="form-row">
          <div class="form-group">
            <label>Month</label>
            <input type="number" name="month" value="${selectedMonth}" min="1" max="12" id="filterMonth"/>
          </div>
          <div class="form-group">
            <label>Year</label>
            <input type="number" name="year" value="${selectedYear}" id="filterYear"/>
          </div>
        </div>
        <button type="submit" class="btn">Filter</button>
        <a class="btn" id="pdfLink"
           href="${pageContext.request.contextPath}/pdf/salaryReport?month=${selectedMonth}&year=${selectedYear}"
           style="background:#e74c3c">&#128196; Download PDF</a>
      </form>
      <p style="margin-top:14px"><strong>Showing:</strong> ${selectedMonth}/${selectedYear}</p>
    </div>
  </div>

  <div class="card">
    <h3>Salary Transactions &mdash; ${selectedMonth}/${selectedYear}</h3>
    <c:if test="${empty transactions}">
      <div class="alert alert-info">No salary transactions for ${selectedMonth}/${selectedYear}. Use the filter above to check another month.</div>
    </c:if>
    <c:if test="${not empty transactions}">
    <table class="data-table">
      <thead>
        <tr><th>Faculty</th><th>Basic Pay</th><th>Arrears</th><th>Deductions</th><th>Net Pay</th><th>Date Paid</th><th>Remarks</th></tr>
      </thead>
      <tbody>
        <c:forEach var="t" items="${transactions}">
          <tr>
            <td>${t.facultyName}</td>
            <td>&#8377;<fmt:formatNumber value="${t.basicPay}" pattern="#,##0.00"/></td>
            <td>&#8377;<fmt:formatNumber value="${t.arrears}" pattern="#,##0.00"/></td>
            <td>&#8377;<fmt:formatNumber value="${t.deductions}" pattern="#,##0.00"/></td>
            <td><strong>&#8377;<fmt:formatNumber value="${t.netPay}" pattern="#,##0.00"/></strong></td>
            <td>${t.paidDate}</td>
            <td>${t.remarks}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    </c:if>
  </div>
</div>

<script>
// Auto-fill basic pay when faculty is selected
function fillBasicPay(sel) {
  const opt = sel.options[sel.selectedIndex];
  const pay = opt.getAttribute('data-pay');
  if (pay) document.getElementById('basicPayInput').value = pay;
}

// Sync PDF link with filter inputs
document.getElementById('filterForm').addEventListener('input', function() {
  const m = document.getElementById('filterMonth').value;
  const y = document.getElementById('filterYear').value;
  const ctx = '${pageContext.request.contextPath}';
  document.getElementById('pdfLink').href = ctx + '/pdf/salaryReport?month=' + m + '&year=' + y;
});
</script>
<jsp:include page="../common/footer.jsp"/>
