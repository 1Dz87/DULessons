<%@ page import="entity.User" %><%--
  Created by IntelliJ IDEA.
  User: Дмитрий
  Date: 01.11.2020
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update</title>
</head>
<body>

<%
    User user = (User) request.getAttribute("user");
%>
<form action="${pageContext.request.contextPath}/updateuser" method="post" enctype="multipart/form-data">
<p>
    <input type="hidden" name="id" value="<%=user.getId()%>">
</p>
<p>
    First name: <input name="first_name" value="<%=user.getFirstName()%>" >
</p>
<p>
    Last name: <input name="last_name" value="<%=user.getLastName()%>" >
</p>
<p>
    Avatar: <input type="file" name="photo" />
</p>
<p>
    <input type="submit">
</p>
</form>

</body>
</html>
