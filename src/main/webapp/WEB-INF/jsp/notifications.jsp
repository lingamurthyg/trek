<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Notifications</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>Notifications</h1>
<table class="grid">
    <thead><tr><th>When</th><th>Subject</th><th>Body</th><th>Delivered</th></tr></thead>
    <tbody>
    <c:forEach var="n" items="${recent}">
        <tr>
            <td><c:out value="${n.sentAt}"/></td>
            <td><c:out value="${n.subject}"/></td>
            <td><pre><c:out value="${n.body}"/></pre></td>
            <td><c:out value="${n.deliveredFlag}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
