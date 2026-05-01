<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Invoice generated</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>Invoice generated</h1>
<c:if test="${not empty invoiceId}">
    <p>Invoice #${invoiceId} created.</p>
    <p><a href="<%=request.getContextPath()%>/invoices/detail.do?id=${invoiceId}">View invoice</a></p>
</c:if>
<c:if test="${empty invoiceId}">
    <p>Could not generate invoice.</p>
</c:if>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
