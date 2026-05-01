<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Shipments by route</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>Shipments by route</h1>
<form method="get" action="<%=request.getContextPath()%>/reports/byRoute.do" class="search-bar">
    <input type="text" name="fromDate" value="<c:out value='${reportForm.fromDate}'/>" placeholder="from"/>
    <input type="text" name="toDate" value="<c:out value='${reportForm.toDate}'/>" placeholder="to"/>
    <button type="submit">Run</button>
</form>
<table class="grid">
    <thead><tr><th>Route</th><th>Name</th><th>Count</th><th>Total weight</th><th>Revenue</th></tr></thead>
    <tbody>
    <c:forEach var="r" items="${rows}">
        <tr>
            <td><c:out value="${r.routeCode}"/></td>
            <td><c:out value="${r.routeName}"/></td>
            <td>${r.count}</td>
            <td>${r.totalWeight}</td>
            <td>${r.revenue}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
