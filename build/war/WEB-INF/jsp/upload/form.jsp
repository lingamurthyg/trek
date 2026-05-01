<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html><html><head><title>Upload</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>File ingestion</h1>
<form method="post" action="<%=request.getContextPath()%>/upload.do" enctype="multipart/form-data">
    <label>Type
        <select name="fileType">
            <option value="CSV">Customer manifest CSV</option>
            <option value="EDI204">EDI 204 (load tender)</option>
            <option value="EDI214">EDI 214 (status update)</option>
        </select>
    </label>
    <label>File <input type="file" name="file" required/></label>
    <button type="submit">Upload &amp; ingest</button>
</form>

<h2>CSV format</h2>
<pre>customer_code,origin,destination,weight_kg,volume_m3,declared_value,pickup_date,route_code,notes</pre>

<h2>EDI 204 / 214 format (fixed-width)</h2>
<p>See README. Try a small file (a few rows) before bulk-loading.</p>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
