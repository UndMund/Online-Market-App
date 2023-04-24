<%@ page import="org.example.utils.UrlPath" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form action="<%= request.getContextPath()%><%=UrlPath.ADMIN_PROFILE%>" method="post">
  <button type="submit">Return</button>
</form>