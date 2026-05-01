<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Vehicle</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>${vehicle == null ? "Add vehicle" : vehicle.plateNumber}</h1>
<form method="post" action="<%=request.getContextPath()%>/vehicles/save.do">
    <input type="hidden" name="vehicleId" value="<c:out value='${vehicleForm.vehicleId}'/>"/>
    <label>Plate # <input type="text" name="plateNumber" value="<c:out value='${vehicleForm.plateNumber}'/>"/></label>
    <label>Make <input type="text" name="make" value="<c:out value='${vehicleForm.make}'/>"/></label>
    <label>Model <input type="text" name="model" value="<c:out value='${vehicleForm.model}'/>"/></label>
    <label>Year <input type="text" name="year" value="<c:out value='${vehicleForm.year}'/>"/></label>
    <label>Capacity (kg) <input type="text" name="capacityKg" value="<c:out value='${vehicleForm.capacityKg}'/>"/></label>
    <label>Type
        <select name="vehicleType">
            <option ${vehicleForm.vehicleType == 'TRACTOR' ? 'selected' : ''}>TRACTOR</option>
            <option ${vehicleForm.vehicleType == 'BOX' ? 'selected' : ''}>BOX</option>
            <option ${vehicleForm.vehicleType == 'VAN' ? 'selected' : ''}>VAN</option>
            <option ${vehicleForm.vehicleType == 'TRAILER' ? 'selected' : ''}>TRAILER</option>
            <option ${vehicleForm.vehicleType == 'REEFER' ? 'selected' : ''}>REEFER</option>
        </select>
    </label>
    <label>Status
        <select name="status">
            <option ${vehicleForm.status == 'AVAILABLE' ? 'selected' : ''}>AVAILABLE</option>
            <option ${vehicleForm.status == 'IN_USE' ? 'selected' : ''}>IN_USE</option>
            <option ${vehicleForm.status == 'MAINTENANCE' ? 'selected' : ''}>MAINTENANCE</option>
        </select>
    </label>
    <button type="submit">Save</button>
</form>

<c:if test="${not empty vehicle}">
    <h2>Maintenance log</h2>
    <table class="grid">
        <thead><tr><th>Date</th><th>Description</th><th>Cost</th><th>Odometer</th><th>Technician</th></tr></thead>
        <tbody>
        <c:forEach var="m" items="${maintenance}">
            <tr>
                <td><c:out value="${m.maintDate}"/></td>
                <td><c:out value="${m.description}"/></td>
                <td>${m.cost}</td>
                <td>${m.odometer}</td>
                <td><c:out value="${m.technician}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <h3>Add maintenance record</h3>
    <form method="post" action="<%=request.getContextPath()%>/vehicles/maintenance/save.do">
        <input type="hidden" name="vehicleId" value="${vehicle.vehicleId}"/>
        <input type="text" name="maintDate" placeholder="yyyy-mm-dd"/>
        <input type="text" name="description" placeholder="description"/>
        <input type="text" name="cost" placeholder="cost"/>
        <input type="text" name="odometer" placeholder="odometer"/>
        <input type="text" name="technician" placeholder="technician"/>
        <button type="submit">Add</button>
    </form>
</c:if>

</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
