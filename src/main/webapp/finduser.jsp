<%--
  Created by IntelliJ IDEA.
  User: DU
  Date: 14.11.2020
  Time: 13:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Find user</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/finduser" method="post">
    <p>
        User's last name: <input name="lastName">
    </p>
    <p>
        <input type="submit">
    </p>
</form>
</body>
</html>
