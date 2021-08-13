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

<table>
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
            <td><c:out value="${item.getId()}" /></td>
            <td><c:out value="${item.getTopic()}" /></td>
            <td><c:out value="${item.getStartDate()}" /></td>
            <td><c:out value="${item.getEndDate()}" /></td>
            <td><c:out value="${item.getStartTimeEveryDay()}" /></td>
            <td><c:out value="${item.getEndTimeEveryDay()}" /></td>
            <td><c:out value="${item.getRooms()}." /></td>
            <td><c:out value="${item.getPrice()}" /></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
