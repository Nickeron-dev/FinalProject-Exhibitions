<%@ page import="java.time.LocalDate" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta content="text/html">
    <title>Welcome</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addexhibition.css" type="text/css">
</head>
<body>
<header>
    <form:form action="/change-language" method="post">
        <input type="submit" name="ukr" value="UKR">
        <input type="submit" name="eng" value="ENG">
    </form:form>
    <a href="/home">${home}</a>
    <a href="/registration">${register}</a>
    <c:if test="${isAuthorized == true}">
        <a href="/logout">${logout}</a>
    </c:if>
    <c:if test="${isAdmin == true}">
        <a href="/statistics">${statistics}</a>
    </c:if>
</header>
<form:form class="add-exhibition" action="/addExhibition" method="post">
    <label>${inputTopic}</label>
    <input type="text" name="topic" required>
    <br>

    <label>${inputStartDate}</label>
    <input type="date" name="startDate" value="${now}" min="${now}" required>

    <br>
    <label>${inputEndDate}</label>
    <input type="date" name="endDate"value="${now}" min="${now}" required>
    <br>
    <label>${inputRoomsNumber}</label>
    <input type="text" name="rooms" required>
    <br>
    <label>${inputStartTime}</label>
    <input type="time" name="startTime" required>
    <br>
    <label>${inputEndTime}</label>
    <input type="time" name="endTime" required>
    <br>
    <label>${inputPrice}</label>
    <input type="text" name="price" required>
    <br>
    <input type="submit" value="${submit}">
</form:form>
</body>
</html>
