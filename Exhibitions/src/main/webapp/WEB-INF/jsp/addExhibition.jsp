<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta content="text/html">
    <title>Welcome</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addExhibition.css" type="text/css">
</head>
<body>
<form:form action="/addExhibition" method="post">
    <label>Topic:</label>
    <input type="text" name="topic">
    <br>
    <label>Start date:</label>
    <input type="date" name="startDate">
    <br>
    <label>End date:</label>
    <input type="date" name="endDate">
    <br>
    <label>Rooms number:</label>
    <input type="text" name="rooms">
    <br>
    <label>Opens at:</label>
    <input type="time" name="startTime">
    <br>
    <label>Closes at:</label>
    <input type="time" name="endTime">
    <br>
    <label>Price</label>
    <input type="text" name="price" placeholder="Price...">
    <br>
    <input type="submit" value="Submit">
</form:form>
</body>
</html>
