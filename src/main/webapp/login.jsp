<%--
  Created by IntelliJ IDEA.
  User: DU
  Date: 15.11.2020
  Time: 11:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login page</title>
</head>
<body>
    <c:if test = "${error != null}">
        <h3 style="color: red">${error}</h3>
    </c:if>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <p>
            <label>Login: </label>
            <input type="text" name="login" />
        </p>
        <p>
            <label>Password: </label>
            <input type="password" name="password" />
        </p>
        <p><input type="submit"></p>
    </form>
</body>
</html>
