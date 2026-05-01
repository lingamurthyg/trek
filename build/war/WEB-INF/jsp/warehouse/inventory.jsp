<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Inventory</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>Inventory</h1>
<form method="get" action="<%=request.getContextPath()%>/inventory.do" class="search-bar">
    <input type="text" name="fragment" placeholder="SKU fragment" value="<c:out value='${inventoryForm.fragment}'/>"/>
    <input type="text" name="zoneId" placeholder="zone id" value="<c:out value='${inventoryForm.zoneId}'/>"/>
    <button type="submit">Search</button>
</form>

<table class="grid">
    <thead><tr><th>SKU</th><th>Description</th><th>Zone</th><th>Qty</th><th>Weight (kg)</th><th>Last updated</th><th>Transfer</th></tr></thead>
    <tbody>
    <c:forEach var="i" items="${items}">
        <tr>
            <td><c:out value="${i.sku}"/></td>
            <td><c:out value="${i.description}"/></td>
            <td>${i.zoneId}</td>
            <td>${i.quantity}</td>
            <td>${i.weightKg}</td>
            <td><c:out value="${i.lastUpdated}"/></td>
            <td>
                <form method="post" action="<%=request.getContextPath()%>/inventory/transfer.do" style="display:inline-flex; gap:4px">
                    <input type="hidden" name="itemId" value="${i.itemId}"/>
                    <input type="text" name="destZoneId" placeholder="dest zone" size="6"/>
                    <input type="text" name="transferQty" placeholder="qty" size="4"/>
                    <button type="submit">Move</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
