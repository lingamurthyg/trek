<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<%@ page import="com.acme.cargotrak.service.CargoFacade" %>
<%@ page import="com.acme.cargotrak.util.SpringContextHolder" %>
<%@ page import="com.acme.cargotrak.util.Util" %>
<!DOCTYPE html><html><head><title>Customer</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<%
    // Scriptlet calling the facade directly to compute outstanding for the side-panel.
    // yes, in the JSP. legacy. ops asked for it after the load tester showed the action
    // was getting too long.
    CargoFacade _cf = (CargoFacade) SpringContextHolder.getBean("cargoFacade");
    Object _custIdAttr = request.getAttribute("customer");
    String _outstanding = "0";
    if (_custIdAttr != null) {
        com.acme.cargotrak.domain.Customer _c = (com.acme.cargotrak.domain.Customer) _custIdAttr;
        _outstanding = Util.formatMoney(_cf.totalOutstandingForCustomer(_c.getCustomerId()));
    }
%>

<h1>${customer == null ? "Add customer" : customer.name}</h1>

<form method="post" action="<%=request.getContextPath()%>/customers/save.do">
    <input type="hidden" name="customerId" value="<c:out value='${customerForm.customerId}'/>"/>
    <label>Code <input type="text" name="customerCode" value="<c:out value='${customerForm.customerCode}'/>"/></label>
    <label>Name <input type="text" name="name" value="<c:out value='${customerForm.name}'/>"/></label>
    <label>Industry <input type="text" name="industry" value="<c:out value='${customerForm.industry}'/>"/></label>
    <label>Credit limit <input type="text" name="creditLimit" value="<c:out value='${customerForm.creditLimit}'/>"/></label>
    <label>Payment terms <input type="text" name="paymentTerms" value="<c:out value='${customerForm.paymentTerms}'/>"/></label>
    <label>Active
        <select name="activeFlag">
            <option value="Y" ${customerForm.activeFlag == 'Y' ? 'selected' : ''}>Y</option>
            <option value="N" ${customerForm.activeFlag == 'N' ? 'selected' : ''}>N</option>
        </select>
    </label>
    <button type="submit">Save</button>
</form>

<c:if test="${not empty customer}">
    <div class="side-info">Outstanding balance: $<%= _outstanding %></div>

    <h2>Contacts</h2>
    <table class="grid">
        <thead><tr><th>Name</th><th>Title</th><th>Email</th><th>Phone</th><th>Primary</th></tr></thead>
        <tbody>
        <c:forEach var="cc" items="${contacts}">
            <tr>
                <td><c:out value="${cc.contactName}"/></td>
                <td><c:out value="${cc.title}"/></td>
                <td><c:out value="${cc.email}"/></td>
                <td><c:out value="${cc.phone}"/></td>
                <td><c:out value="${cc.primaryFlag}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h3>Add contact</h3>
    <form method="post" action="<%=request.getContextPath()%>/customers/contact/save.do">
        <input type="hidden" name="customerId" value="${customer.customerId}"/>
        <input type="text" name="contactName" placeholder="name"/>
        <input type="text" name="title" placeholder="title"/>
        <input type="text" name="email" placeholder="email"/>
        <input type="text" name="phone" placeholder="phone"/>
        <select name="primaryFlag"><option value="N">N</option><option value="Y">Y</option></select>
        <button type="submit">Add contact</button>
    </form>

    <h2>Addresses</h2>
    <table class="grid">
        <thead><tr><th>Type</th><th>Line 1</th><th>City</th><th>State</th><th>ZIP</th><th>Country</th></tr></thead>
        <tbody>
        <c:forEach var="a" items="${addresses}">
            <tr>
                <td><c:out value="${a.addressType}"/></td>
                <td><c:out value="${a.line1}"/></td>
                <td><c:out value="${a.city}"/></td>
                <td><c:out value="${a.state}"/></td>
                <td><c:out value="${a.postalCode}"/></td>
                <td><c:out value="${a.country}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h3>Add address</h3>
    <form method="post" action="<%=request.getContextPath()%>/customers/address/save.do">
        <input type="hidden" name="customerId" value="${customer.customerId}"/>
        <select name="addressType"><option>BILLING</option><option>SHIPPING</option></select>
        <input type="text" name="line1" placeholder="line 1"/>
        <input type="text" name="city" placeholder="city"/>
        <input type="text" name="state" placeholder="state"/>
        <input type="text" name="postalCode" placeholder="zip"/>
        <input type="text" name="country" placeholder="country"/>
        <button type="submit">Add address</button>
    </form>
</c:if>

</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/>
</body></html>
