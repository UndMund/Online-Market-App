<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Change password</title>
</head>
<body>
<div align="center">

    <table>
        <tr>
            <td>
                <%@ include file="returnToUserMenuHeader.jsp" %>
            </td>
            <td>
                <h1>Change password</h1>
            </td>
        </tr>
        <tr>
            <form action="${pageContext.request.contextPath}<%=UrlPath.CHANGE_PASSWORD%>" method="post">
                <td>
                    <input type="password" name="password" id="password">
                </td>
                <td>
                    <button type="submit">Update</button>
                </td>
            </form>
        </tr>
    </table>
    <c:if test="${not empty requestScope.errors}">
        <div style="color: red">
            <c:forEach var="error" items="${requestScope.errors}">
                <span>${error.message}</span>
                <br>
            </c:forEach>
        </div>
    </c:if>
    <c:if test="${not empty requestScope.message}">
        <div style="color: green">
            <h4>${requestScope.message}</h4>
        </div>
    </c:if>
</div>
</body>
</html>
