<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Invoices</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>Invoices</h1>

<form method="get" action="<%=request.getContextPath()%>/invoices.do" class="search-bar">
    <input type="text" name="fragment" value="<c:out value='${invoiceForm.fragment}'/>" placeholder="invoice #"/>
    <select name="customerId">
        <option value="">any</option>
        <c:forEach var="c" items="${customers}">
            <option value="${c.customerId}" ${invoiceForm.customerId == c.customerId ? 'selected' : ''}><c:out value="${c.name}"/></option>
        </c:forEach>
    </select>
    <select name="status">
        <option value="">any</option>
        <option ${invoiceForm.status == 'OPEN' ? 'selected' : ''}>OPEN</option>
        <option ${invoiceForm.status == 'PAID' ? 'selected' : ''}>PAID</option>
    </select>
    <button type="submit">Search</button>
</form>

<p>Total: ${total}</p>

<table class="grid">
    <thead><tr><th>Invoice #</th><th>Customer</th><th>Date</th><th>Due</th><th>Status</th><th>Total</th><th>Paid</th><th></th></tr></thead>
    <tbody>
    <c:forEach var="i" items="${results}">
        <tr>
            <td><a href="<%=request.getContextPath()%>/invoices/detail.do?id=${i.invoiceId}"><c:out value="${i.invoiceNo}"/></a></td>
            <td>#${i.customerId}</td>
            <td><c:out value="${i.invoiceDate}"/></td>
            <td><c:out value="${i.dueDate}"/></td>
            <td><c:out value="${i.status}"/></td>
            <td>${i.total}</td>
            <td>${i.amountPaid}</td>
            <td><a href="<%=request.getContextPath()%>/invoices/pdf.do?id=${i.invoiceId}">PDF</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<h2>Generate invoice from shipment</h2>
<form method="post" action="<%=request.getContextPath()%>/invoices/generate.do">
    <input type="text" name="shipmentId" placeholder="shipment id"/>
    <button type="submit">Generate</button>
</form>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
