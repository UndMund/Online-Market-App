<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Menu</title>
</head>
<body>
<div align="center">
    <h1>Hello, User!</h1>
    <h3><a href="${pageContext.request.contextPath}/login">Log In</a></h3>
    <h3><a href="${pageContext.request.contextPath}/registration">Register</a></h3>
</div>
</body>
</html>