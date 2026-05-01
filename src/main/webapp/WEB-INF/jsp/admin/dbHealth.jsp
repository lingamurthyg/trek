<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html><html><head><title>Admin: DB health</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>DB health (SHOW TABLE STATUS)</h1>
<%
    List<Map<String,String>> _ts = (List<Map<String,String>>) request.getAttribute("tableStatus");
    if (_ts == null) _ts = new ArrayList<Map<String,String>>();
%>
<%
    if (_ts.isEmpty()) {
        out.print("<p>No table status returned.</p>");
    } else {
        Map<String,String> first = _ts.get(0);
        out.print("<table class='grid'><thead><tr>");
        for (String col : first.keySet()) {
            out.print("<th>" + col + "</th>");
        }
        out.print("</tr></thead><tbody>");
        for (Map<String,String> row : _ts) {
            out.print("<tr>");
            for (String col : first.keySet()) {
                String v = row.get(col);
                out.print("<td>" + (v == null ? "" : com.acme.cargotrak.util.Util.escapeHtml(v)) + "</td>");
            }
            out.print("</tr>");
        }
        out.print("</tbody></table>");
    }
%>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
