<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Login</title>
</head>
<body>
<div align="center">
    <form action="${pageContext.request.contextPath}/login" method="post">
        <label for="username">Username:
            <input type="text" name="username" id="username">
        </label><br>
        <label for="password">Password:
            <input type="password" name="password" id="password" required>
        </label><br>
        <button type="submit">Login</button>
        <a href="${pageContext.request.contextPath}/registration">
            <button type="button">Register</button>
        </a>
        <c:if test="${param.error != null}">
            <div style="color: #000000">
                <span>Email or password is not correct</span>
            </div>
        </c:if>
    </form>
</div>
</body>
</html>