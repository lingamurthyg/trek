<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html><html><head><title>Admin: SQL runner</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>SQL runner</h1>
<p class="warn">Yes, this runs arbitrary SQL. Don't.</p>
<form method="post" action="<%=request.getContextPath()%>/admin/sql.do">
    <textarea name="sql" rows="6" style="width:80%"><c:out value="${sqlRunnerForm.sql}"/></textarea>
    <br/>
    <label><input type="radio" name="mode" value="QUERY" ${sqlRunnerForm.mode == 'UPDATE' ? '' : 'checked'}/> SELECT</label>
    <label><input type="radio" name="mode" value="UPDATE" ${sqlRunnerForm.mode == 'UPDATE' ? 'checked' : ''}/> UPDATE/INSERT/DELETE</label>
    <button type="submit">Run</button>
</form>

<c:if test="${not empty affected}">
    <p class="success">Rows affected: ${affected}</p>
</c:if>

<%
    List<List<String>> _rows = (List<List<String>>) request.getAttribute("rows");
    if (_rows != null && !_rows.isEmpty()) {
        out.print("<table class='grid'><thead><tr>");
        for (String h : _rows.get(0)) {
            out.print("<th>" + com.acme.cargotrak.util.Util.escapeHtml(h) + "</th>");
        }
        out.print("</tr></thead><tbody>");
        for (int i = 1; i < _rows.size(); i++) {
            out.print("<tr>");
            for (String v : _rows.get(i)) {
                out.print("<td>" + com.acme.cargotrak.util.Util.escapeHtml(v) + "</td>");
            }
            out.print("</tr>");
        }
        out.print("</tbody></table>");
    }
%>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
