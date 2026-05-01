<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Address saved</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>Address saved</h1>
<p><a href="<%=request.getContextPath()%>/customers/edit.do?id=${customerId}">Back to customer</a></p>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
