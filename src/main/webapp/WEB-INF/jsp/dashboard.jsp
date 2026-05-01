<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html>
<html>
<head>
    <title>CargoTrak - Dashboard</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
    <h1>Dashboard</h1>

    <div class="cards">
        <div class="card"><div class="num">${counts.users}</div><div class="lbl">Users</div></div>
        <div class="card"><div class="num">${counts.customers}</div><div class="lbl">Customers</div></div>
        <div class="card"><div class="num">${counts.shipments}</div><div class="lbl">Shipments</div></div>
        <div class="card"><div class="num">${counts.invoices}</div><div class="lbl">Invoices</div></div>
        <div class="card"><div class="num">${counts.drivers}</div><div class="lbl">Drivers</div></div>
        <div class="card"><div class="num">${counts.vehicles}</div><div class="lbl">Vehicles</div></div>
        <div class="card"><div class="num">${counts.warehouses}</div><div class="lbl">Warehouses</div></div>
    </div>

    <h2>Recent activity</h2>
    <table class="grid">
        <thead><tr><th>Time</th><th>User</th><th>Action</th><th>Entity</th><th>Details</th></tr></thead>
        <tbody>
        <c:forEach var="a" items="${recentAudits}">
            <tr>
                <td><c:out value="${a.auditTime}"/></td>
                <td><c:out value="${a.username}"/></td>
                <td><c:out value="${a.action}"/></td>
                <td><c:out value="${a.entityType}"/>:<c:out value="${a.entityId}"/></td>
                <td><c:out value="${a.details}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/>
</body>
</html>
