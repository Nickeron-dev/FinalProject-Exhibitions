<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta content="text/html">
    <title>Welcome</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registration.css" type="text/css">
</head>
<body>
<form:form action="/registration" method="post">
    <input type="text" name="email" placeholder="Email...">
    <input type="text" name="username" placeholder="Username...">
    <input type="password" name="password" placeholder="Password...">
    <input type="submit" value="Submit">
</form:form>
</body>
</html>
