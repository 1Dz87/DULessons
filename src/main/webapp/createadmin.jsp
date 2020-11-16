<%--
  Created by IntelliJ IDEA.
  User: DU
  Date: 15.11.2020
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create ADMIN</title>
</head>
<body>
<c:if test = "${error != null}">
    <h3 style="color: red">${error}</h3>
</c:if>
<h1>${sessionScope.get("error")}</h1>
<form action="${pageContext.request.contextPath}/createAdmin" method="post" >
    <p>
        Login: <input name="login">
    </p>
    <p>
        Password: <input type="password" name="password">
    </p>
    <p>
        First name: <input name="first_name">
    </p>
    <p>
        Last name: <input name="last_name">
    </p>
    <p>
        <input type="submit">
    </p>
</form>
</body>
</html>
