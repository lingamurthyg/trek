<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title><tiles:getAsString name="title"/></title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/>
    <script src="<%=request.getContextPath()%>/js/cargotrak.js"></script>
</head>
<body>
    <tiles:insertAttribute name="header"/>
    <tiles:insertAttribute name="nav"/>
    <main class="cargo-main">
        <tiles:insertAttribute name="body"/>
    </main>
    <tiles:insertAttribute name="footer"/>
</body>
</html>
