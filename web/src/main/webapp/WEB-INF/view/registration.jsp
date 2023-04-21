<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<div align="center">
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
                <option label="${position}">${position}</option>
                <br>
            </c:forEach>
        </select><br/>
        <input type="submit" value="Send">
    </form>

    <c:if test="${not empty requestScope.errors}">
        <div style="color: red">
            <c:forEach var="error" items="${requestScope.errors}">
                <span>${error.message}</span>
                <br>
            </c:forEach>
        </div>
    </c:if>
</div>
</body>
</html>
