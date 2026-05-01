<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/layout/page.jspf" %>
<!DOCTYPE html>
<html>
<head>
    <title>CargoTrak - Files</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/>
    <style>
        .dir-tabs { display:flex; gap:4px; margin: 8px 0 16px; border-bottom: 2px solid #1f4f8b; }
        .dir-tabs a { padding: 6px 14px; background: #ddd; color: #1f4f8b; border-radius: 4px 4px 0 0; }
        .dir-tabs a.active { background: #1f4f8b; color: #fff; font-weight: bold; }
        .path-bar { background: #fff7c2; border: 1px solid #cc9; padding: 6px 10px; font-family: monospace; margin-bottom: 8px; }
        .crumbs a { color: #1f4f8b; }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/layout/nav.jsp"/>
<main class="cargo-main">
<h1>Filesystem browser</h1>

<p style="color:#666; max-width:780px;">
    The application reads and writes to the local filesystem under
    <code>/var/app/cargo/</code>. Generated reports, uploaded files, inbound
    carrier feeds, and the daily archive all live here. This page lists what's
    in each directory so you can verify what was produced and download things
    without ssh-ing in.
</p>

<div class="dir-tabs">
    <c:forEach var="d" items="${dirTabs}">
        <a class="${d == dirKey ? 'active' : ''}"
           href="<%=request.getContextPath()%>/files.do?dir=${d}">/var/app/cargo/${d}/</a>
    </c:forEach>
</div>

<div class="path-bar">
    <strong>Path:</strong>
    <span class="crumbs">
        <a href="<%=request.getContextPath()%>/files.do?dir=${dirKey}">${dirPath}</a><c:if test="${not empty sub}">/<c:out value="${sub}"/></c:if>
    </span>
    &nbsp;|&nbsp;
    <strong>Files:</strong> ${fn:length(rows)}
    &nbsp;|&nbsp;
    <strong>Total size:</strong> <c:out value="${totalBytesHuman}"/>
    &nbsp;|&nbsp;
    <a href="<%=request.getContextPath()%>/files.do?dir=${dirKey}<c:if test='${not empty sub}'>&sub=${sub}</c:if>">refresh</a>
</div>

<c:if test="${not empty dirs}">
    <h3>Subdirectories</h3>
    <table class="grid" style="max-width:680px">
        <thead><tr><th>Name</th><th>Modified</th></tr></thead>
        <tbody>
        <c:forEach var="d" items="${dirs}">
            <tr>
                <td>
                    <a href="<%=request.getContextPath()%>/files.do?dir=${dirKey}&sub=<c:if test='${not empty sub}'>${sub}/</c:if><c:out value='${d.name}'/>">
                        &#128193; <c:out value="${d.name}"/>/
                    </a>
                </td>
                <td><c:out value="${d.modified}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<h3>Files</h3>
<c:if test="${empty rows}">
    <p><em>No files in this directory.</em></p>
</c:if>
<c:if test="${not empty rows}">
<table class="grid">
    <thead>
        <tr><th>Name</th><th>Size</th><th>Modified</th><th>Actions</th></tr>
    </thead>
    <tbody>
    <c:forEach var="r" items="${rows}">
        <tr>
            <td><c:out value="${r.name}"/></td>
            <td>${r.sizeHuman}</td>
            <td><c:out value="${r.modified}"/></td>
            <td>
                <a href="<%=request.getContextPath()%>/download?dir=${dirKey}<c:if test='${not empty sub}'>&sub=${sub}</c:if>&name=<c:out value='${r.name}'/>">
                    download
                </a>
                <c:if test="${r.isViewable}">
                    &nbsp;|&nbsp;
                    <a href="<%=request.getContextPath()%>/files/view.do?dir=${dirKey}<c:if test='${not empty sub}'>&sub=${sub}</c:if>&name=<c:out value='${r.name}'/>">
                        view
                    </a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</c:if>

<p style="color:#666; font-size:11px; margin-top:24px; max-width:780px;">
    <strong>Modernization note:</strong> these directories are bind-mounted into
    the Tomcat container. In a modern stack, this would typically be S3 buckets
    with EventBridge triggers and lifecycle policies; the
    <code>InboundPollerJob</code> Quartz job would become a Lambda/Cloud Function.
</p>

</main>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/>
</body>
</html>
