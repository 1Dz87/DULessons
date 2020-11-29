<%@ page import="entity.Car" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: DU
  Date: 29.11.2020
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Личный кабинет: ${sessionScope.get("user").login}</title>
</head>
<body>
    <table border>
        <tr>
            <th>Авто</th>
            <th>Парковочная зона</th>
            <th COLSPAN="2">Парковочное место</th>
        </tr>
        <tr>
            <th></th>
            <th></th>
            <th>Номер</th>
            <th>Занято/Свободно</th>
        </tr>
        <%
            for (Car car : (List<Car>)request.getAttribute("cars")) {
        %>
        <tr>
            <td><%=car.getModel()%></td>
            <td><%=car.getParkingPlace().getParkingArea().getSide().getDisplayField()%></td>
            <td><%=car.getParkingPlace().getNumber()%></td>
            <td><%=car.getParkingPlace().isOccupied()%></td>
        </tr>
        <% } %>
    </table>
</body>
</html>
