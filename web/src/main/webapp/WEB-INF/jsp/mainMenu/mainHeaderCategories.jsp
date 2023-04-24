<%@ page import="org.example.utils.UrlPath" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table>
    <tr>
        <h3>
        <c:forEach var="category" items="${sessionScope.categories}">
            <td>
                <a href="${pageContext.request.contextPath}<%=UrlPath.MAIN%>/${category}">${category.categoryName}</a>
            </td>
        </c:forEach>
        </h3>
    </tr>
</table>
