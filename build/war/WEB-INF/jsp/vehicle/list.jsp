<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Vehicles</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>Vehicles</h1>
<p><a href="<%=request.getContextPath()%>/vehicles/edit.do" class="btn">Add vehicle</a></p>
<table class="grid">
    <thead><tr><th>Plate</th><th>Make</th><th>Model</th><th>Year</th><th>Capacity (kg)</th><th>Type</th><th>Status</th><th></th></tr></thead>
    <tbody>
    <c:forEach var="v" items="${vehicles}">
        <tr>
            <td><c:out value="${v.plateNumber}"/></td>
            <td><c:out value="${v.make}"/></td>
            <td><c:out value="${v.model}"/></td>
            <td>${v.year}</td>
            <td>${v.capacityKg}</td>
            <td><c:out value="${v.vehicleType}"/></td>
            <td><c:out value="${v.status}"/></td>
            <td><a href="<%=request.getContextPath()%>/vehicles/edit.do?id=${v.vehicleId}">edit</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
