<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Upload result</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>Ingestion result</h1>
<c:if test="${not empty error}"><p class="error">${error}</p></c:if>
<c:if test="${not empty result}">
    <p>Source file: <c:out value="${result.sourceFile}"/></p>
    <p>Accepted: <strong>${result.accepted}</strong> &nbsp; Rejected: <strong>${result.rejected}</strong></p>
    <p>Rejection report: <code><c:out value="${result.rejectionReportPath}"/></code></p>
    <c:if test="${not empty result.rejections}">
        <h2>Rejections</h2>
        <ul>
        <c:forEach var="r" items="${result.rejections}"><li><c:out value="${r}"/></li></c:forEach>
        </ul>
    </c:if>
</c:if>
<p><a href="<%=request.getContextPath()%>/upload/form.do">Upload another</a></p>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
