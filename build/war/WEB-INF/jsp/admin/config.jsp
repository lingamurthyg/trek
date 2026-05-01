<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Admin: System Config</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>System Config</h1>
<form method="post" action="<%=request.getContextPath()%>/admin/config.do?op=save" class="inline-edit">
    <input type="text" name="configKey" placeholder="key"/>
    <input type="text" name="configValue" placeholder="value"/>
    <input type="text" name="description" placeholder="description"/>
    <button type="submit">Save</button>
</form>
<table class="grid">
    <thead><tr><th>Key</th><th>Value</th><th>Description</th><th>Updated</th></tr></thead>
    <tbody>
    <c:forEach var="cfg" items="${configs}">
        <tr>
            <td><c:out value="${cfg.configKey}"/></td>
            <td>
                <form method="post" action="<%=request.getContextPath()%>/admin/config.do?op=save" style="display:inline-flex; gap:4px">
                    <input type="hidden" name="configKey" value="<c:out value='${cfg.configKey}'/>"/>
                    <input type="text" name="configValue" value="<c:out value='${cfg.configValue}'/>"/>
                    <button type="submit">Save</button>
                </form>
            </td>
            <td><c:out value="${cfg.description}"/></td>
            <td><c:out value="${cfg.updatedAt}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
