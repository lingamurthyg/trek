<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>CargoTrak - Error</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/>
</head>
<body class="error-page">
<h1>Something went wrong</h1>
<p>Please contact CargoTrak support and quote the time below.</p>
<pre><%= new java.util.Date() %></pre>
<% if (exception != null) { %>
    <h2>Detail (admin only)</h2>
    <pre><%
        java.io.StringWriter sw = new java.io.StringWriter();
        exception.printStackTrace(new java.io.PrintWriter(sw));
        out.print(sw.toString());
    %></pre>
<% } %>
<p><a href="<%=request.getContextPath()%>/dashboard.do">Back to dashboard</a></p>
</body>
</html>
