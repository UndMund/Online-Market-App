<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8"/>
    <title>aboutUser</title>
</head>
<body>
<div align="center">
    <table>
        <tr>
            <td>
                <%@ include file="returnToUserMenuHeader.jsp"%>
            </td>
            <td>
                <h1>Profile</h1>
            </td>
        </tr>
        <tr>
            <td>Name</td>
            <td>${sessionScope.user.username}</td>
        </tr>
        <tr>
            <td>Email</td>
            <td>${sessionScope.user.email}</td>
        </tr>
        <tr>
            <td>Phone</td>
            <td>${sessionScope.user.phoneNumber}</td>
        </tr>
        <tr>
            <td>Money</td>
            <td>${sessionScope.user.money}</td>
        </tr>

    </table>
    <p></p>
</div>
</body>
</html>