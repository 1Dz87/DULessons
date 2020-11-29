<%@ page import="lib.ParkingAreaSide" %><%--
  Created by IntelliJ IDEA.
  User: DU
  Date: 29.11.2020
  Time: 13:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Создание новой парковки</title>
</head>
<body>
<c:if test = "${error != null}">
    <h3 style="color: red">${error}</h3>
</c:if>
<form action="${pageContext.request.contextPath}/createParking" method="post" >
    <table border="1">
        <tr>
            <th>Наименование парковки: </th>
            <td><input type="text" name="parkingName" width="50%"></td>
        </tr>
        <tr>
            <th>Количество парковочных зон:</th>
            <%
                for (ParkingAreaSide side : ParkingAreaSide.values()) {
            %>
            <td><input type="checkbox" name="<%=side.name()%>"><%=side.getDisplayField()%></td>
            <td>Количество парковочных мест:
                <input type="number" name="<%=side.name()%>_places">
            </td>
            <%}%>
        </tr>
    </table>
    <button type="submit">Создать</button>
</form>
</body>
</html>