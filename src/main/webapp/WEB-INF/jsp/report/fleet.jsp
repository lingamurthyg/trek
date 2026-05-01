<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Fleet utilization</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>Fleet utilization</h1>
<form method="get" action="<%=request.getContextPath()%>/reports/fleet.do" class="search-bar">
    <input type="text" name="fromDate" value="<c:out value='${reportForm.fromDate}'/>" placeholder="from"/>
    <input type="text" name="toDate" value="<c:out value='${reportForm.toDate}'/>" placeholder="to"/>
    <button type="submit">Run</button>
    <a class="btn" href="<%=request.getContextPath()%>/reports/fleet.do?format=xls&fromDate=<c:out value='${reportForm.fromDate}'/>&toDate=<c:out value='${reportForm.toDate}'/>">Excel</a>
</form>
<table class="grid">
    <thead><tr><th>Plate</th><th>Type</th><th>Status</th><th># shipments</th><th>Total weight</th><th>Revenue</th></tr></thead>
    <tbody>
    <c:forEach var="r" items="${rows}">
        <tr>
            <td><c:out value="${r.plateNumber}"/></td>
            <td><c:out value="${r.vehicleType}"/></td>
            <td><c:out value="${r.status}"/></td>
            <td>${r.shipmentCount}</td>
            <td>${r.totalWeight}</td>
            <td>${r.totalRevenue}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
