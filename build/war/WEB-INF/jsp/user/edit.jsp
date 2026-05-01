<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Edit user</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>${user == null ? "Add user" : "Edit user"}</h1>
<form method="post" action="<%=request.getContextPath()%>/users/save.do">
    <input type="hidden" name="userId" value="<c:out value='${userForm.userId}'/>"/>
    <label>Username <input type="text" name="username" value="<c:out value='${userForm.username}'/>" ${user == null ? "" : "readonly"}/></label>
    <label>Password <input type="password" name="password" placeholder="${user == null ? 'set password' : 'leave blank to keep'}"/></label>
    <label>Full name <input type="text" name="fullName" value="<c:out value='${userForm.fullName}'/>"/></label>
    <label>Email <input type="text" name="email" value="<c:out value='${userForm.email}'/>"/></label>
    <label>Active
        <select name="activeFlag">
            <option value="Y" ${userForm.activeFlag == 'Y' ? 'selected' : ''}>Y</option>
            <option value="N" ${userForm.activeFlag == 'N' ? 'selected' : ''}>N</option>
        </select>
    </label>
    <fieldset>
        <legend>Roles</legend>
        <c:forEach var="r" items="${roles}">
            <label><input type="checkbox" name="roleNames" value="${r.roleName}"/> ${r.roleName} - <c:out value="${r.description}"/></label>
        </c:forEach>
    </fieldset>
    <button type="submit">Save</button>
    <a href="<%=request.getContextPath()%>/users.do">Cancel</a>
</form>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/>
</body></html>
