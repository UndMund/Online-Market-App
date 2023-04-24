<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Login</title>
</head>
<body>
<div align="center">
    <h1>Log In</h1>
    <%@ include file="logRegHeader.jsp"%>
    <form action="${pageContext.request.contextPath}<%=UrlPath.LOGIN%>" method="post">
        <label for="username">Username:
            <input type="text" name="username" id="username">
        </label><br>
        <label for="password">Password:
            <input type="password" name="password" id="password" required>
        </label><br>
        <button type="submit">Login</button>
            <c:if test="${not empty requestScope.errors}">
                <div style="color: red">
                    <c:forEach var="error" items="${requestScope.errors}">
                        <span>${error.message}</span>
                        <br>
                    </c:forEach>
                </div>
            </c:if>
    </form>
    <h2>
        <a href="${pageContext.request.contextPath}<%=UrlPath.MAIN%>">Trading floor</a>
    </h2>
</div>
</body>
</html>