<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="cargo-header">
    <div class="brand">CargoTrak <span class="version">v3.4.2</span></div>
    <div class="user">
        <c:if test="${not empty sessionScope.currentUser}">
            <c:out value="${sessionScope.currentUser.fullName}"/>
            (<c:out value="${sessionScope.currentUser.username}"/>)
            &nbsp;|&nbsp;
            <a href="<%=request.getContextPath()%>/logout.do">Logout</a>
        </c:if>
    </div>
</div>
