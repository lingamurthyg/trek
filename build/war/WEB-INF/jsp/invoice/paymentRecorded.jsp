<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Payment recorded</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>Payment recorded</h1>
<p>Payment #${paymentId}.</p>
<p><a href="<%=request.getContextPath()%>/invoices.do">Back to invoices</a></p>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
