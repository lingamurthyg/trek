<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    // Already logged in? bounce to dashboard.
    if (session.getAttribute("currentUser") != null) {
        response.sendRedirect(request.getContextPath() + "/dashboard.do");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>CargoTrak - Sign in</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/>
</head>
<body class="login-page">
<div class="login-card">
    <h1>CargoTrak</h1>
    <h2>Sign in</h2>
    <c:if test="${not empty error}">
        <div class="error"><c:out value="${error}"/></div>
    </c:if>
    <form method="post" action="<%=request.getContextPath()%>/login.do">
        <input type="hidden" name="next" value="<c:out value='${param.next}'/>"/>
        <label>Username
            <input type="text" name="username" autofocus required/>
        </label>
        <label>Password
            <input type="password" name="password" required/>
        </label>
        <button type="submit">Sign in</button>
    </form>
    <p class="hint">
        Default: <code>admin</code> / <code>admin123</code>
    </p>
</div>
</body>
</html>
