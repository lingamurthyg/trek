<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Warehouse</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>${warehouse == null ? "Add warehouse" : warehouse.name}</h1>
<form method="post" action="<%=request.getContextPath()%>/warehouses/save.do">
    <input type="hidden" name="warehouseId" value="<c:out value='${warehouseForm.warehouseId}'/>"/>
    <label>Code <input type="text" name="code" value="<c:out value='${warehouseForm.code}'/>"/></label>
    <label>Name <input type="text" name="name" value="<c:out value='${warehouseForm.name}'/>"/></label>
    <label>City <input type="text" name="city" value="<c:out value='${warehouseForm.city}'/>"/></label>
    <label>Capacity (m3) <input type="text" name="capacityM3" value="<c:out value='${warehouseForm.capacityM3}'/>"/></label>
    <button type="submit">Save</button>
</form>

<c:if test="${not empty warehouse}">
    <h2>Zones</h2>
    <table class="grid">
        <thead><tr><th>Code</th><th>Description</th></tr></thead>
        <tbody>
            <c:forEach var="z" items="${zones}">
                <tr><td><c:out value="${z.code}"/></td><td><c:out value="${z.description}"/></td></tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
