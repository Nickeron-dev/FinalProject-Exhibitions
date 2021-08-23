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
    <a href="/home">${home}</a>
    <a href="/registration">${register}</a>
    <c:if test="${isAuthorized == true}">
        <a href="/logout">${logout}</a>
    </c:if>
    <c:if test="${isAuthorized == false}">
        <a href="/login">${login}</a>
    </c:if>
    <c:if test="${isAdmin == true}">
        <a href="/statistics">${statistics}</a>
        <a href="/addExhibition">${addExhibition}</a>
    </c:if>
</header>

<form:form action="/" method="post">
    <label>${filterByDate}</label>
    <input type="date" name="filterDate" value="${now}" required>
    <input type="submit" name="filterSubmit" value="${submit}">
</form:form>
<p>${notGivenFilter}</p>

<table border="2">
    <tr>
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
    <c:choose>
        <c:when test="${content.size() > 0}">
            <c:forEach var="item" items="${content}">
                <tr>
                    <td>${item.topic}</td>
                    <td>${item.startDate}</td>
                    <td>${item.endDate}</td>
                    <td>${item.startTimeEveryDay}</td>
                    <td>${item.endTimeEveryDay}</td>
                    <td>${item.rooms}</td>
                    <td>${item.price}</td>
                    <td>${item.state}</td>
                    <td><form:form action="/buy" method="post">
                        <input class="buy" type="submit" name="${item.id}" value="${buy}">
                    </form:form></td>
                    <c:if test="${isAdmin == true}">
                        <td><form:form action="/cancel" method="post">
                            <input class="cancel" type="submit" name="${item.id}" value="${cancel}">
                        </form:form></td>
                    </c:if>
                    <c:if test="${isAdmin == true}">
                        <td><form:form action="/plan" method="post">
                            <input class="plan" type="submit" name="${item.id}" value="${plan}">
                        </form:form></td>
                    </c:if>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <h2>${noElementsFound}</h2>
        </c:otherwise>
    </c:choose>
</table>
<br>
<form:form action="/" method="post">
    <c:forEach var="i" begin="1" end="${pagesNumber}" step="1">
        <input class="pages" type="submit" name="id" value="${i}">
    </c:forEach>
</form:form>

</body>
</html>
