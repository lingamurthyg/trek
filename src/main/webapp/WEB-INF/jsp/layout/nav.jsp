<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.acme.cargotrak.domain.User" %>
<%@ page import="com.acme.cargotrak.service.AuthService" %>
<%@ page import="com.acme.cargotrak.util.Constants" %>
<%@ page import="com.acme.cargotrak.util.SpringContextHolder" %>
<%
    // scriptlet: business decision in the JSP. yes, we know.
    User _navUser = (User) session.getAttribute(Constants.SESSION_USER);
    AuthService _navAuth = (AuthService) SpringContextHolder.getBean("authService");
    boolean _navIsAdmin = _navAuth != null && _navAuth.userInRole(_navUser, "ADMIN");
%>
<ul class="cargo-nav">
    <li><a href="<%=request.getContextPath()%>/dashboard.do">Dashboard</a></li>
    <li><a href="<%=request.getContextPath()%>/customers.do">Customers</a></li>
    <li><a href="<%=request.getContextPath()%>/shipments.do">Shipments</a></li>
    <li><a href="<%=request.getContextPath()%>/drivers.do">Drivers</a></li>
    <li><a href="<%=request.getContextPath()%>/vehicles.do">Vehicles</a></li>
    <li><a href="<%=request.getContextPath()%>/warehouses.do">Warehouses</a></li>
    <li><a href="<%=request.getContextPath()%>/inventory.do">Inventory</a></li>
    <li><a href="<%=request.getContextPath()%>/invoices.do">Invoices</a></li>
    <li><a href="<%=request.getContextPath()%>/reports/revenue.do">Reports</a></li>
    <li><a href="<%=request.getContextPath()%>/upload/form.do">Upload</a></li>
    <li><a href="<%=request.getContextPath()%>/files.do">Files</a></li>
    <li><a href="<%=request.getContextPath()%>/notifications.do">Notifications</a></li>
    <% if (_navIsAdmin) { %>
        <li><a href="<%=request.getContextPath()%>/users.do">Users</a></li>
        <li><a href="<%=request.getContextPath()%>/admin/config.do">Admin: config</a></li>
        <li><a href="<%=request.getContextPath()%>/admin/dbHealth.do">DB health</a></li>
        <li><a href="<%=request.getContextPath()%>/admin/sql.do">SQL runner</a></li>
        <li><a href="<%=request.getContextPath()%>/admin/logs.do">Logs</a></li>
        <li><a href="<%=request.getContextPath()%>/admin/audit.do">Audit log</a></li>
    <% } %>
</ul>
