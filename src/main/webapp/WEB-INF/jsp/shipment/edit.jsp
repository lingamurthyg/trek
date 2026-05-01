<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Shipment</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>${shipment == null ? "New shipment" : shipment.trackingNo}</h1>

<form method="post" action="<%=request.getContextPath()%>/shipments/save.do">
    <input type="hidden" name="shipmentId" value="<c:out value='${shipmentForm.shipmentId}'/>"/>
    <label>Tracking # <input type="text" name="trackingNo" value="<c:out value='${shipmentForm.trackingNo}'/>" placeholder="auto-gen if blank"/></label>
    <label>Customer
        <select name="customerId" required>
            <c:forEach var="c" items="${customers}">
                <option value="${c.customerId}" ${shipmentForm.customerId == c.customerId ? 'selected' : ''}><c:out value="${c.name}"/></option>
            </c:forEach>
        </select>
    </label>
    <label>Origin <input type="text" name="origin" value="<c:out value='${shipmentForm.origin}'/>"/></label>
    <label>Destination <input type="text" name="destination" value="<c:out value='${shipmentForm.destination}'/>"/></label>
    <label>Weight (kg) <input type="text" name="weightKg" value="<c:out value='${shipmentForm.weightKg}'/>"/></label>
    <label>Volume (m3) <input type="text" name="volumeM3" value="<c:out value='${shipmentForm.volumeM3}'/>"/></label>
    <label>Declared value <input type="text" name="declaredValue" value="<c:out value='${shipmentForm.declaredValue}'/>"/></label>
    <label>Driver
        <select name="driverId">
            <option value="">unassigned</option>
            <c:forEach var="d" items="${drivers}">
                <option value="${d.driverId}" ${shipmentForm.driverId == d.driverId ? 'selected' : ''}><c:out value="${d.fullName}"/></option>
            </c:forEach>
        </select>
    </label>
    <label>Vehicle
        <select name="vehicleId">
            <option value="">unassigned</option>
            <c:forEach var="v" items="${vehicles}">
                <option value="${v.vehicleId}" ${shipmentForm.vehicleId == v.vehicleId ? 'selected' : ''}><c:out value="${v.plateNumber}"/></option>
            </c:forEach>
        </select>
    </label>
    <label>Route ID <input type="text" name="routeId" value="<c:out value='${shipmentForm.routeId}'/>"/></label>
    <label>Rate card ID <input type="text" name="rateCardId" value="<c:out value='${shipmentForm.rateCardId}'/>"/></label>
    <label>Notes <textarea name="notes" rows="3"><c:out value='${shipmentForm.notes}'/></textarea></label>
    <button type="submit">Save</button>
</form>

<c:if test="${not empty shipment}">
    <h2>Status: <c:out value="${shipment.status}"/></h2>
    <form method="post" action="<%=request.getContextPath()%>/shipments/transition.do">
        <input type="hidden" name="shipmentId" value="${shipment.shipmentId}"/>
        <label>Transition to
            <select name="newStatus">
                <option>BOOKED</option>
                <option>IN_TRANSIT</option>
                <option>DELIVERED</option>
                <option>INVOICED</option>
                <option>CLOSED</option>
                <option>CANCELLED</option>
            </select>
        </label>
        <button type="submit">Transition</button>
    </form>

    <h2>Status history</h2>
    <table class="grid">
        <thead><tr><th>When</th><th>From</th><th>To</th><th>By</th><th>Notes</th></tr></thead>
        <tbody>
        <c:forEach var="h" items="${history}">
            <tr>
                <td><c:out value="${h.changedAt}"/></td>
                <td><c:out value="${h.oldStatus}"/></td>
                <td><c:out value="${h.newStatus}"/></td>
                <td><c:out value="${h.changedBy}"/></td>
                <td><c:out value="${h.notes}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h2>Legs</h2>
    <table class="grid">
        <thead><tr><th>Seq</th><th>From</th><th>To</th><th>Driver</th><th>Vehicle</th><th>Status</th></tr></thead>
        <tbody>
        <c:forEach var="l" items="${legs}">
            <tr>
                <td>${l.seqNo}</td>
                <td><c:out value="${l.fromLocation}"/></td>
                <td><c:out value="${l.toLocation}"/></td>
                <td>${l.driverId}</td>
                <td>${l.vehicleId}</td>
                <td><c:out value="${l.status}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
