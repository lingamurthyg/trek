<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Routes</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>Routes</h1>
<table class="grid">
    <thead><tr><th>Code</th><th>Name</th><th>Origin</th><th>Destination</th></tr></thead>
    <tbody>
    <c:forEach var="r" items="${routes}">
        <tr>
            <td><c:out value="${r.code}"/></td>
            <td><c:out value="${r.name}"/></td>
            <td><c:out value="${r.origin}"/></td>
            <td><c:out value="${r.destination}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
