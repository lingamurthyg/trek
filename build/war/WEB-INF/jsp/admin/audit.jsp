<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Admin: Audit log</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>Audit log (recent)</h1>
<table class="grid">
    <thead><tr><th>When</th><th>User</th><th>Action</th><th>Entity</th><th>Details</th></tr></thead>
    <tbody>
    <c:forEach var="a" items="${audits}">
        <tr>
            <td><c:out value="${a.auditTime}"/></td>
            <td><c:out value="${a.username}"/></td>
            <td><c:out value="${a.action}"/></td>
            <td><c:out value="${a.entityType}"/>:<c:out value="${a.entityId}"/></td>
            <td><c:out value="${a.details}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
