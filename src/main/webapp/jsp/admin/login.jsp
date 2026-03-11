<%--
  Created by IntelliJ IDEA.
  User: cemre
  Date: 2023-04-28
  Time: 10:23 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Login - Admin</title>
</head>
<body>
<form method="POST" action="<c:url value="/admin/login/"/>">
  <label for="email">Email:</label>
  <input name="email" id="email" type="text"/><br/><br/>
  <label for="password">Password:</label>
  <input name="password" id="password" type="password"/><br/><br/>
  <button type="submit">Submit</button><br/>
</form>
<c:choose>
  <c:when test="${message.error}">
    <p><c:out value="${message.message}"/></p>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>
</body>
</html>
