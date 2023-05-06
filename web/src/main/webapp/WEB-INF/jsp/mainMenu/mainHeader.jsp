<%@ page import="org.example.utils.UrlPath" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<table>
    <tr>
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
            </br>
            <form action="${pageContext.request.contextPath}<%=UrlPath.LOGOUT%>" method="post">
                <button type="submit">Logout</button>
            </form>
        </c:if>
    </tr>
    <tr>
        </br>
        <form action="<%=UrlPath.MAIN%>" method="post">
            <td>
                <select name="currentCategory" id="currentCategory">
                    <c:forEach var="category" items="${sessionScope.categories}">
                        <option label="${category}">${category}</option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <button type="submit">Use filter</button>
            </td>
        </form>
    </tr>
</table>

