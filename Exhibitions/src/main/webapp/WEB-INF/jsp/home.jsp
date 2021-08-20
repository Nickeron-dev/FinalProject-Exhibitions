<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta content="text/html">
    <title>Welcome</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css" type="text/css">
</head>
<body>
<header>
    <form:form action="/change-language" method="post">
        <input type="submit" name="ukr" value="UKR">
        <input type="submit" name="eng" value="ENG">
    </form:form>
    <a href="/registration">${register}</a>
    <a href="/login">${login}</a>
</header>

<form:form action="/" method="post">
    <label>Filter by date</label>
    <input type="date" name="filterDate">
    <input type="submit" name="filterSubmit" value="Submit">
</form:form>
<p>${notGivenFilter}</p>

<table border="2">
    <tr>
        <th>ID</th>
        <th>${topic}</th>
        <th>${startDate}</th>
        <th>${endDate}</th>
        <th>${startTime}</th>
        <th>${endTime}</th>
        <th>${rooms}</th>
        <th>${price}</th>
        <th>${state}</th>
        <th>${buyTicket}</th>
        <c:if test="${isAdmin == true}">
            <th>${cancel}</th>
        </c:if>
        <c:if test="${isAdmin == true}">
            <th>${plan}</th>
        </c:if>

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
            <td>${item.state}</td>
            <td><form:form action="/buy" method="post">
                <input class="buy" type="submit" name="${item.id}" value="Buy">
            </form:form></td>
            <c:if test="${isAdmin == true}">
                <td><form:form action="/cancel" method="post">
                    <input class="cancel" type="submit" name="${item.id}" value="Cancel">
                    </form:form></td>
            </c:if>
            <c:if test="${isAdmin == true}">
                <td><form:form action="/plan" method="post">
                    <input class="plan" type="submit" name="${item.id}" value="Plan">
                </form:form></td>
            </c:if>
        </tr>
    </c:forEach>
</table>

</body>
</html>
