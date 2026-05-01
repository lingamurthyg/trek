<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<%@ page import="com.acme.cargotrak.service.CargoFacade" %>
<%@ page import="com.acme.cargotrak.util.SpringContextHolder" %>
<%@ page import="com.acme.cargotrak.domain.Shipment" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html><html><head><title>Shipments</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>Shipments</h1>

<form method="get" action="<%=request.getContextPath()%>/shipments.do" class="search-bar">
    <input type="text" name="trackingFragment" value="<c:out value='${shipmentForm.trackingFragment}'/>" placeholder="tracking #"/>
    <select name="customerId">
        <option value="">any customer</option>
        <c:forEach var="c" items="${customers}">
            <option value="${c.customerId}" ${shipmentForm.customerId == c.customerId ? 'selected' : ''}><c:out value="${c.name}"/></option>
        </c:forEach>
    </select>
    <select name="status">
        <option value="">any status</option>
        <option ${shipmentForm.status == 'DRAFT' ? 'selected' : ''}>DRAFT</option>
        <option ${shipmentForm.status == 'BOOKED' ? 'selected' : ''}>BOOKED</option>
        <option ${shipmentForm.status == 'IN_TRANSIT' ? 'selected' : ''}>IN_TRANSIT</option>
        <option ${shipmentForm.status == 'DELIVERED' ? 'selected' : ''}>DELIVERED</option>
        <option ${shipmentForm.status == 'INVOICED' ? 'selected' : ''}>INVOICED</option>
        <option ${shipmentForm.status == 'CLOSED' ? 'selected' : ''}>CLOSED</option>
    </select>
    <input type="text" name="fromDate" value="<c:out value='${shipmentForm.fromDate}'/>" placeholder="yyyy-mm-dd"/>
    <input type="text" name="toDate" value="<c:out value='${shipmentForm.toDate}'/>" placeholder="yyyy-mm-dd"/>
    <button type="submit">Search</button>
    <a href="<%=request.getContextPath()%>/shipments/edit.do" class="btn">New shipment</a>
</form>

<p>Total: ${total}</p>

<%
    // N+1 query antipattern: loop over shipments and call .getLegs().size() inside.
    // (We mark legs lazy="true" in HBM but the eager-loading for shipments still
    // means we get one extra query per row. Yes, we know.)
    List _ships = (List) request.getAttribute("results");
%>
<table class="grid">
    <thead><tr><th>Tracking</th><th>Customer</th><th>Origin</th><th>Dest</th><th>Status</th><th>Booked</th><th>Legs</th><th>Total</th></tr></thead>
    <tbody>
    <%
        if (_ships != null) {
            CargoFacade _cf = (CargoFacade) SpringContextHolder.getBean("cargoFacade");
            Iterator _it = _ships.iterator();
            while (_it.hasNext()) {
                Shipment _s = (Shipment) _it.next();
                int legs = _s.getLegs() == null ? 0 : _s.getLegs().size();
    %>
        <tr>
            <td><a href="<%=request.getContextPath()%>/shipments/edit.do?id=<%= _s.getShipmentId() %>"><%= _s.getTrackingNo() %></a></td>
            <td>#<%= _s.getCustomerId() %></td>
            <td><%= _s.getOrigin() == null ? "" : _s.getOrigin() %></td>
            <td><%= _s.getDestination() == null ? "" : _s.getDestination() %></td>
            <td><%= _s.getStatus() %></td>
            <td><%= _s.getBookedDate() %></td>
            <td><%= legs %></td>
            <td><%= _cf.formatMoney(_s.getTotalCharge()) %></td>
        </tr>
    <%   }
        }
    %>
    </tbody>
</table>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
