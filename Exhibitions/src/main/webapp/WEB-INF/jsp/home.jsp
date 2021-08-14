<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta content="text/html">
    <title>Welcome</title>
</head>
<body>
<form:form action="/change-language" method="post">
    <input type="submit" name="ukr" value="UKR">
    <input type="submit" name="eng" value="ENG">
</form:form>
<button>${register}</button>
<button>${login}</button>

<table border="2">
    <tr>
        <th>ID</th>
        <th>Topic</th>
        <th>Start Date</th>
        <th>End Date</th>
        <th>Start Time</th>
        <th>End Time</th>
        <th>Rooms</th>
        <th>Price</th>
    </tr>
    <c:forEach var="item" items="${listExhibitions}">
        <tr>
            <td>${item.id}</td>
            <td>${item.topic}</td>
            <td>${item.startDate}</td>
            <td>${item.endDate}</td>
            <td>${item.startTimeEveryDay}</td>
            <td>${item.endTimeEveryDay}</td>
            <td>${item.rooms}</td>
            <td>${item.price}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
