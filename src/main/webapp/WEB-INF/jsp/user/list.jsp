<%@ page pageEncoding='UTF-8' contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title></title>
</head>
<body>
    dajflajdfklaj
    <c:forEach var="user" items="${userList}">
        id:${user.id}<br/>
        age:${user.age}<br/>
        name:${user.name}<br/>
    </c:forEach>
</body>
</html>
