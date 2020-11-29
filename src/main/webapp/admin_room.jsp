<%--
  Created by IntelliJ IDEA.
  User: DU
  Date: 29.11.2020
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Личный кабинет администратора</title>
</head>
<body>
    <table>
        <tr>
            <th>Создать новую парковку</th>
            <th>Переидти на список пользователей</th>
        </tr>
        <tr>
            <td>
                <form action="${pageContext.request.contextPath}/createParking" method="get">
                    <button type="submit">Создать</button>
                </form>
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/alluser" method="get">
                    <button>Переидти</button>
                </form>
            </td>
        </tr>
    </table>
</body>
</html>
