<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Customers</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>Customers</h1>

<form method="get" action="<%=request.getContextPath()%>/customers.do" class="search-bar">
    <input type="text" name="namePart" value="<c:out value='${customerForm.namePart}'/>" placeholder="name fragment"/>
    <input type="text" name="industry" value="<c:out value='${customerForm.industry}'/>" placeholder="industry"/>
    <select name="activeFlag">
        <option value="">any</option>
        <option value="Y" ${customerForm.activeFlag == 'Y' ? 'selected' : ''}>Y</option>
        <option value="N" ${customerForm.activeFlag == 'N' ? 'selected' : ''}>N</option>
    </select>
    <button type="submit">Search</button>
    <a href="<%=request.getContextPath()%>/customers/edit.do" class="btn">Add customer</a>
</form>

<p>Total: ${total}, page ${page} (${pageSize} per page)</p>

<table class="grid">
    <thead><tr><th>Code</th><th>Name</th><th>Industry</th><th>Credit limit</th><th>Terms</th><th>Active</th></tr></thead>
    <tbody>
    <c:forEach var="c" items="${results}">
        <tr>
            <td><a href="<%=request.getContextPath()%>/customers/edit.do?id=${c.customerId}"><c:out value="${c.customerCode}"/></a></td>
            <td><c:out value="${c.name}"/></td>
            <td><c:out value="${c.industry}"/></td>
            <td><c:out value="${c.creditLimit}"/></td>
            <td><c:out value="${c.paymentTerms}"/></td>
            <td><c:out value="${c.activeFlag}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div class="pager">
    <c:if test="${page > 0}">
        <a href="?page=${page-1}&pageSize=${pageSize}&namePart=<c:out value='${customerForm.namePart}'/>&industry=<c:out value='${customerForm.industry}'/>&activeFlag=<c:out value='${customerForm.activeFlag}'/>">prev</a>
    </c:if>
    <c:if test="${(page+1)*pageSize < total}">
        <a href="?page=${page+1}&pageSize=${pageSize}&namePart=<c:out value='${customerForm.namePart}'/>&industry=<c:out value='${customerForm.industry}'/>&activeFlag=<c:out value='${customerForm.activeFlag}'/>">next</a>
    </c:if>
</div>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/>
</body></html>
