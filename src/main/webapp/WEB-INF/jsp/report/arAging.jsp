<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>AR aging</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>AR aging</h1>
<form method="get" action="<%=request.getContextPath()%>/reports/aragon.do" class="search-bar">
    <input type="text" name="asOfDate" value="<c:out value='${reportForm.asOfDate}'/>" placeholder="as of"/>
    <button type="submit">Run</button>
    <a class="btn" href="<%=request.getContextPath()%>/reports/aragon.do?format=pdf&asOfDate=<c:out value='${reportForm.asOfDate}'/>">PDF</a>
</form>
<p>As of <c:out value="${asOf}"/></p>
<table class="grid">
    <thead><tr><th>Code</th><th>Name</th><th>0-30</th><th>31-60</th><th>61-90</th><th>90+</th><th>Total</th></tr></thead>
    <tbody>
    <c:forEach var="r" items="${rows}">
        <tr>
            <td><c:out value="${r.customerCode}"/></td>
            <td><c:out value="${r.customerName}"/></td>
            <td>${r.bucket0_30}</td>
            <td>${r.bucket31_60}</td>
            <td>${r.bucket61_90}</td>
            <td>${r.bucket90Plus}</td>
            <td>${r.totalOutstanding}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
