<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>On-time delivery %</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>On-time delivery %</h1>
<form method="get" action="<%=request.getContextPath()%>/reports/onTime.do" class="search-bar">
    <input type="text" name="fromDate" value="<c:out value='${reportForm.fromDate}'/>" placeholder="from"/>
    <input type="text" name="toDate" value="<c:out value='${reportForm.toDate}'/>" placeholder="to"/>
    <button type="submit">Run</button>
</form>
<table class="grid">
    <thead><tr><th>Customer</th><th>Total</th><th>On-time</th><th>%</th></tr></thead>
    <tbody>
    <c:forEach var="r" items="${rows}">
        <tr>
            <td><c:out value="${r.customerCode}"/> <c:out value="${r.customerName}"/></td>
            <td>${r.total}</td>
            <td>${r.onTime}</td>
            <td>${r.pct}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
