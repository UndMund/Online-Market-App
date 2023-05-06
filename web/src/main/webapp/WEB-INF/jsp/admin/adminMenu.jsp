<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.example.utils.UrlPath" %>
<html>
<head>
    <title>Admin menu</title>
</head>
<body>
<div align="center">
    <table>
        <tr>
            <td>
                <h2>Admin menu</h2>
            </td>
            <td>
                <%@include file="../mainMenu/returnToMainHeader.jsp" %>
            </td>
        </tr>
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}<%=UrlPath.ALL_USERS%>">All users</a>
            </td>
        </tr>
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}<%=UrlPath.ALL_ORDERS%>">All orders</a>
            </td>
        </tr>
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}<%=UrlPath.VERIFY_AD%>">Verify advertise</a>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
