<%@ page import="org.example.utils.UrlPath" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<table>
    <tr>
        <h3>
            <c:if test="${empty sessionScope.user}">
                <a href="${pageContext.request.contextPath}<%=UrlPath.LOGIN%>">Login</a>
            </c:if>
            <c:if test="${not empty sessionScope.user}">
                <a href="${pageContext.request.contextPath}<%=UrlPath.USER_PROFILE%>">${sessionScope.user.username}</a>
                <label>, $${sessionScope.user.money}</label>
                <c:if test="${sessionScope.user.position eq 'ADMIN'}">
                    </br>
                    <a href="${pageContext.request.contextPath}<%=UrlPath.ADMIN_PROFILE%>">Admin menu</a>
                </c:if>
                <form action="${pageContext.request.contextPath}<%=UrlPath.LOGOUT%>" method="post">
                    <button type="submit">Logout</button>
                </form>
            </c:if>
        </h3>
    </tr>
</table>

