<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<%@ page import="com.acme.cargotrak.util.Constants" %>
<%@ page import="com.acme.cargotrak.util.FileUtil" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.FileReader" %>
<!DOCTYPE html><html><head><title>Admin: Log viewer</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/></head><body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>Log viewer</h1>
<ul>
    <c:forEach var="f" items="${logFiles}">
        <li><a href="<%=request.getContextPath()%>/admin/logViewer?name=<c:out value='${f}'/>"><c:out value="${f}"/></a></li>
    </c:forEach>
</ul>

<%
    String _file = request.getParameter("inline");
    if (_file != null && _file.indexOf("..") < 0 && _file.indexOf('/') < 0) {
        File _f = new File(Constants.LOGS_DIR, _file);
        if (_f.exists()) {
            out.print("<h2>" + com.acme.cargotrak.util.Util.escapeHtml(_file) + "</h2><pre style='max-height:400px;overflow:auto'>");
            BufferedReader _br = null;
            try {
                _br = new BufferedReader(new FileReader(_f));
                String _ln;
                int _i = 0;
                while ((_ln = _br.readLine()) != null && _i < 500) {
                    out.println(com.acme.cargotrak.util.Util.escapeHtml(_ln));
                    _i++;
                }
            } finally {
                if (_br != null) _br.close();
            }
            out.print("</pre>");
        }
    }
%>
</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/></body></html>
