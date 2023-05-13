<%@ page import="org.example.utils.UrlPath" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Registration</title>
</head>
<body>
<div align="center">
    <h1>Register new user</h1>
    <%@ include file="logRegHeader.jsp"%>
    <form action="${pageContext.request.contextPath}/registration" method="post">
        <label for="username">Name:
            <input type="text" name="username" id="username">
        </label><br/>
        <label for="email">Email:
            <input type="text" name="email" id="email">
        </label><br/>
        <label for="phoneNumber">Phone:
            <input type="text" name="phoneNumber" id="phoneNumber">
        </label><br/>
        <label for="password">Password:
            <input type="password" name="password" id="password">
        </label><br/>
        <select name="position" id="position">
            <c:forEach var="position" items="${requestScope.positions}">
                <option label="${position.name}">${position.name}</option>
            </c:forEach>
        </select>
        <input type="submit" value="Register">
    </form>
    <c:if test="${not empty requestScope.errors}">
        <div style="color: red">
            <c:forEach var="error" items="${requestScope.errors}">
                <span>${error.message}</span>
                <br>
            </c:forEach>
        </div>
    </c:if>
    <h2>
        <a href="${pageContext.request.contextPath}<%=UrlPath.MAIN%>">Trading floor</a>
    </h2>
</div>
</body>
</html>
