<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Invoice</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<c:if test="${empty invoice}">
    <h1>Invoice not found</h1>
</c:if>
<c:if test="${not empty invoice}">
    <h1>Invoice <c:out value="${invoice.invoiceNo}"/></h1>
    <p>Customer #${invoice.customerId} | Status: <c:out value="${invoice.status}"/></p>
    <p>Date: <c:out value="${invoice.invoiceDate}"/> | Due: <c:out value="${invoice.dueDate}"/></p>
    <p>Subtotal: ${invoice.subtotal} | Tax: ${invoice.tax} | <strong>Total: ${invoice.total}</strong></p>
    <p>Paid: ${invoice.amountPaid} | Outstanding: ${invoice.outstanding}</p>

    <h2>Line items</h2>
    <table class="grid">
        <thead><tr><th>Description</th><th>Qty</th><th>Unit price</th><th>Line total</th></tr></thead>
        <tbody>
        <c:forEach var="li" items="${invoice.lineItems}">
            <tr>
                <td><c:out value="${li.description}"/></td>
                <td>${li.quantity}</td>
                <td>${li.unitPrice}</td>
                <td>${li.lineTotal}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <p>
        <a href="<%=request.getContextPath()%>/invoices/pdf.do?id=${invoice.invoiceId}" class="btn">Download PDF</a>
    </p>

    <h2>Record payment</h2>
    <form method="post" action="<%=request.getContextPath()%>/payments/record.do">
        <input type="hidden" name="customerId" value="${invoice.customerId}"/>
        <input type="hidden" name="invoiceId" value="${invoice.invoiceId}"/>
        <input type="text" name="amount" placeholder="amount"/>
        <select name="method"><option>WIRE</option><option>CHECK</option><option>ACH</option><option>CASH</option></select>
        <input type="text" name="reference" placeholder="reference"/>
        <button type="submit">Record</button>
    </form>
</c:if>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
