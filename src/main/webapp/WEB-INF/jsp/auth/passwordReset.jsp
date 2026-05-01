<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Password reset</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>Password reset</h1>
<c:if test="${not empty tempPassword}">
    <div class="success">Temporary password issued: <code><c:out value="${tempPassword}"/></code></div>
</c:if>
<c:if test="${not empty ok}">
    <div class="${ok ? 'success' : 'error'}">
        Password change ${ok ? "succeeded" : "failed (check old password)"}.
    </div>
</c:if>

<h2>Self-service change</h2>
<form method="post" action="<%=request.getContextPath()%>/passwordReset.do?op=self">
    <label>Current password <input type="password" name="oldPassword"/></label>
    <label>New password <input type="password" name="newPassword"/></label>
    <button type="submit">Change</button>
</form>

<h2>Admin reset (admin only)</h2>
<form method="post" action="<%=request.getContextPath()%>/passwordReset.do">
    <label>Username <input type="text" name="username"/></label>
    <button type="submit">Reset and email temp password</button>
</form>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/>
</body></html>
