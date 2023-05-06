<%@ page import="org.example.utils.UrlPath" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>New advertisement</title>
</head>
<body>
<div align="center">
    <table>
        <tr>
            <td>
                <%@ include file="returnToUserMenuHeader.jsp" %>
            </td>
            <td>
                <h1>Add new advertisement</h1>
            </td>
        </tr>
    </table>
    <form action="${pageContext.request.contextPath}${UrlPath.NEW_AD}" method="post">
        <label for="name">Name:
            <input type="text" name="name" id="name">
        </label></br>
        <label for="price">Cost:
            <input type="text" name="price" id="price">
        </label></br>
        <label for="description">Description:
            <input type="text" name="description" id="description">
        </label></br>
        <select name="category" id="category">
            <c:forEach var="category" items="${sessionScope.categories}">
                <option label="${category}">${category}</option>
            </c:forEach>
        </select>
        <input type="submit" value="Create">
    </form>
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
