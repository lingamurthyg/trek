<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Warehouses</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>Warehouses</h1>
<p><a href="<%=request.getContextPath()%>/warehouses/edit.do" class="btn">Add warehouse</a></p>
<table class="grid">
    <thead><tr><th>Code</th><th>Name</th><th>City</th><th>Capacity (m3)</th><th></th></tr></thead>
    <tbody>
    <c:forEach var="w" items="${warehouses}">
        <tr>
            <td><c:out value="${w.code}"/></td>
            <td><c:out value="${w.name}"/></td>
            <td><c:out value="${w.city}"/></td>
            <td>${w.capacityM3}</td>
            <td><a href="<%=request.getContextPath()%>/warehouses/edit.do?id=${w.warehouseId}">edit</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
