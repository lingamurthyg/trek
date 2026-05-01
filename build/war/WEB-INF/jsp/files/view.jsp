<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html>
<html>
<head>
    <title>CargoTrak - View file</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/>
    <style>
        .file-view {
            background: #fff;
            border: 1px solid #c8c8c0;
            padding: 12px;
            white-space: pre;
            font-family: "Courier New", monospace;
            font-size: 12px;
            overflow: auto;
            max-height: 70vh;
        }
        .meta { color: #666; margin: 8px 0; font-size: 12px; }
        .truncated { color: #850; background: #fff7c2; border: 1px solid #cc9; padding: 6px 10px; margin: 8px 0; }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">

<p>
    <a href="<%=request.getContextPath()%>/files.do?dir=${dirKey}<c:if test='${not empty sub}'>&sub=${sub}</c:if>">&larr; back to <c:out value='${dirKey}'/>/<c:out value='${sub}'/></a>
</p>

<c:if test="${not empty error}">
    <div class="error"><c:out value="${error}"/></div>
</c:if>

<c:if test="${empty error}">
    <h1><c:out value="${fileName}"/></h1>
    <div class="meta">
        Path: <code><c:out value="${fileAbs}"/></code>
        &nbsp;|&nbsp; Size: ${fileSize} bytes
        &nbsp;|&nbsp;
        <a href="<%=request.getContextPath()%>/download?dir=${dirKey}<c:if test='${not empty sub}'>&sub=${sub}</c:if>&name=<c:out value='${fileName}'/>">download</a>
    </div>

    <c:if test="${truncated}">
        <div class="truncated">
            File is larger than 1 MB; only the first 1 MB is shown above.
            Use <em>download</em> to get the full file.
        </div>
    </c:if>

    <pre class="file-view"><c:out value="${content}"/></pre>
</c:if>

</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/>
</body>
</html>
