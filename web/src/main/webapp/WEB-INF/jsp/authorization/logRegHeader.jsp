<%@ page import="org.example.utils.UrlPath" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table>
    <tr>
        <td><a href="${pageContext.request.contextPath}<%=UrlPath.LOGIN%>">Login</a></td>
        <td><pre>   </pre></td>
        <td><a href="${pageContext.request.contextPath}<%=UrlPath.REGISTRATION%>">Registration</a></td>
    </tr>
</table>
