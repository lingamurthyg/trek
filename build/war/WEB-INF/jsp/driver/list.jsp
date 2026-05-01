<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Drivers</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>Drivers</h1>
<p><a href="<%=request.getContextPath()%>/drivers/edit.do" class="btn">Add driver</a></p>

<c:if test="${not empty expiring}">
    <div class="warn">
        <strong>Expiring licenses (next 60 days):</strong>
        <c:forEach var="d" items="${expiring}">
            <c:out value="${d.fullName}"/> (<c:out value="${d.licenseExpiry}"/>);
        </c:forEach>
    </div>
</c:if>

<table class="grid">
    <thead><tr><th>Code</th><th>Name</th><th>License</th><th>Expiry</th><th>Phone</th><th>Active</th><th></th></tr></thead>
    <tbody>
    <c:forEach var="d" items="${drivers}">
        <tr>
            <td><c:out value="${d.employeeCode}"/></td>
            <td><c:out value="${d.fullName}"/></td>
            <td><c:out value="${d.licenseNumber}"/></td>
            <td><c:out value="${d.licenseExpiry}"/></td>
            <td><c:out value="${d.phone}"/></td>
            <td><c:out value="${d.activeFlag}"/></td>
            <td><a href="<%=request.getContextPath()%>/drivers/edit.do?id=${d.driverId}">edit</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
