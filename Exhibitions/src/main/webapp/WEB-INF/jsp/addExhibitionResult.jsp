<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta content="text/html">
    <title>Add Exhibition</title>
    <link href="${pageContext.request.contextPath}/css/addexhibitionresult.css" rel="stylesheet" type="text/css">
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
    </c:if>
</header>

<h1>${result}</h1>
</body>
</html>
