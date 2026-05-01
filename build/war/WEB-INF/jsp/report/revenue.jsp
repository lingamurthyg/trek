<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Revenue by Customer</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>Revenue by Customer</h1>
<form method="get" action="<%=request.getContextPath()%>/reports/revenue.do" class="search-bar">
    <input type="text" name="fromDate" value="<c:out value='${reportForm.fromDate}'/>" placeholder="from yyyy-mm-dd"/>
    <input type="text" name="toDate" value="<c:out value='${reportForm.toDate}'/>" placeholder="to yyyy-mm-dd"/>
    <button type="submit">Run</button>
    <a class="btn" href="<%=request.getContextPath()%>/reports/revenue.do?format=xls&fromDate=<c:out value='${reportForm.fromDate}'/>&toDate=<c:out value='${reportForm.toDate}'/>">Excel</a>
</form>
<p>From <c:out value="${from}"/> to <c:out value="${to}"/></p>
<table class="grid">
    <thead><tr><th>Code</th><th>Name</th><th># invoices</th><th>Revenue</th><th>Paid</th><th>Outstanding</th></tr></thead>
    <tbody>
    <c:forEach var="r" items="${rows}">
        <tr>
            <td><c:out value="${r.customerCode}"/></td>
            <td><c:out value="${r.customerName}"/></td>
            <td>${r.invoiceCount}</td>
            <td>${r.grossRevenue}</td>
            <td>${r.amountPaid}</td>
            <td>${r.outstanding}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
