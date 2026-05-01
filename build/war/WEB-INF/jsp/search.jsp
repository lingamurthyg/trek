<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html>
<html>
<head><title>CargoTrak - Search</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head>
<body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>Search</h1>
<%-- DELIBERATE LEGACY BUG: ${q} is the raw request param, written without escaping. XSS. --%>
<%-- Modernization tools should flag this. --%>
<p>You searched for: <%= request.getAttribute("q") %></p>

<form method="get" action="<%=request.getContextPath()%>/search.do">
    <input type="text" name="q" value="<%= request.getAttribute("q") == null ? "" : request.getAttribute("q") %>"/>
    <button type="submit">Search</button>
</form>

<c:if test="${not empty shipment}">
    <h2>Shipment match</h2>
    <p>Tracking: <c:out value="${shipment.trackingNo}"/> Status: <c:out value="${shipment.status}"/></p>
</c:if>
<c:if test="${not empty customer}">
    <h2>Customer match</h2>
    <p><c:out value="${customer.customerCode}"/>: <c:out value="${customer.name}"/></p>
</c:if>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/>
</body>
</html>
