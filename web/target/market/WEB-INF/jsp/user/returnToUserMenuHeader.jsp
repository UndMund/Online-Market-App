<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.example.utils.UrlPath" %>

<form action="<%= request.getContextPath()%><%=UrlPath.USER_PROFILE%>" method="post">
  <button type="submit">Return</button>
</form>
