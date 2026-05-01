<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Users</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>Users</h1>
<p><a href="<%=request.getContextPath()%>/users/edit.do">Add user</a></p>
<table class="grid">
    <thead><tr><th>ID</th><th>Username</th><th>Full name</th><th>Email</th><th>Active</th><th>Last login</th><th>Actions</th></tr></thead>
    <tbody>
    <c:forEach var="u" items="${users}">
        <tr>
            <td>${u.userId}</td>
            <td><c:out value="${u.username}"/></td>
            <td><c:out value="${u.fullName}"/></td>
            <td><c:out value="${u.email}"/></td>
            <td><c:out value="${u.activeFlag}"/></td>
            <td><c:out value="${u.lastLogin}"/></td>
            <td>
                <a href="<%=request.getContextPath()%>/users/edit.do?id=${u.userId}">edit</a> |
                <a href="<%=request.getContextPath()%>/users/deactivate.do?id=${u.userId}">deactivate</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/>
</body></html>
