<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.example.utils.UrlPath" %>
<html>
<head>
    <title>User profile</title>
</head>
<body>
<div align="center">
    <table>
        <tr>
            <td>
                <h2>Menu</h2>
            </td>
            <td>
                <%@include file="../mainMenu/returnToMainHeader.jsp" %>
            </td>
        </tr>
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}<%=UrlPath.ABOUT%>">About user</a>
            </td>
        </tr>
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}<%=UrlPath.CHANGE_PASSWORD%>">Change password</a>
            </td>
        </tr>
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}<%=UrlPath.NEW_AD%>">New advertisement</a>
            </td>
        </tr>
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}<%=UrlPath.MY_AD%>">My advertisements</a>
            </td>
        </tr>
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}<%=UrlPath.REPLENISH_BALANCE%>">Replenish balance</a>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
