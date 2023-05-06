<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Main</title>
</head>
<body>
<h2>
    <%@ include file="mainHeader.jsp" %>
</h2>
<div align="center">
    <h2>${requestScope.currentCategory}</h2>
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Cost</th>
            <th>Description</th>
            <th>Trader name</th>
            <th>Trader phone number</th>
        </tr>
        <c:forEach var="product" items="${sessionScope.products}">
        <tr>
            <td>${product.name}</td>
            <td>${product.price}$</td>
            <td>${product.description}</td>
            <td>${product.user.username}</td>
            <td>${product.user.phoneNumber}</td>
        </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
