<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Driver</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>${driver == null ? "Add driver" : driver.fullName}</h1>
<form method="post" action="<%=request.getContextPath()%>/drivers/save.do">
    <input type="hidden" name="driverId" value="<c:out value='${driverForm.driverId}'/>"/>
    <label>Employee code <input type="text" name="employeeCode" value="<c:out value='${driverForm.employeeCode}'/>"/></label>
    <label>Full name <input type="text" name="fullName" value="<c:out value='${driverForm.fullName}'/>"/></label>
    <label>License # <input type="text" name="licenseNumber" value="<c:out value='${driverForm.licenseNumber}'/>"/></label>
    <label>License expiry <input type="text" name="licenseExpiry" value="<c:out value='${driverForm.licenseExpiry}'/>" placeholder="yyyy-mm-dd"/></label>
    <label>Phone <input type="text" name="phone" value="<c:out value='${driverForm.phone}'/>"/></label>
    <label>Active
        <select name="activeFlag">
            <option value="Y" ${driverForm.activeFlag == 'Y' ? 'selected' : ''}>Y</option>
            <option value="N" ${driverForm.activeFlag == 'N' ? 'selected' : ''}>N</option>
        </select>
    </label>
    <button type="submit">Save</button>
</form>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
