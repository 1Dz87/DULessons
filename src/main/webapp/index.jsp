<%@ page import="entity.User" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.UserDao" %>
<%@ page import="dao.impl.UserDaoFromDBImpl" %>
<%@ page import="java.util.Base64" %>

<%--
  Created by IntelliJ IDEA.
  User: Дмитрий
  Date: 01.11.2020
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<table border="1px">
    <tr>
        <th bgcolor="#7fffd4">First name</th>
        <th bgcolor="#7fffd4">Last name</th>
        <th bgcolor="#7fffd4">User avatar</th>
    </tr>
    <%
        UserDao userDao = UserDaoFromDBImpl.getInstance();
        List<User> users = userDao.findAll();
        for (User user : users) {
    %>
    <tr>
        <td><%=user.getFirstName()%>
        </td>
        <td><%=user.getLastName()%>
        </td>
        <td>
            <img src="<%= user.getImage() == null ? "" : "data:image/jpg;base64," + Base64.getEncoder().encodeToString(user.getImage().getPhoto()) %>" alt="Аватар пользователя" width="300" height="200"/>
        </td>

        <td>
            <form action="${pageContext.request.contextPath}/deleteuser" method="post">
                <input type="hidden" name="id" value="<%=user.getId()%>">
                <button type="submit">Delete</button>
            </form>
        </td>

        <td>
            <form action="${pageContext.request.contextPath}/updateuser" method="get">
                <input type="hidden" name="id" value="<%=user.getId()%>">
                <button type="submit">Update</button>
            </form>
        </td>
    </tr>
    <%
        }
    %>
</table>
<p>
<form action="${pageContext.request.contextPath}/createuser" method="get">
    <button type="submit">Create user</button>
</form>
</p>
<p>
<form action="${pageContext.request.contextPath}/finduser" method="get">
    <button type="submit">Find user</button>
</form>
</p>

</body>
</html>
