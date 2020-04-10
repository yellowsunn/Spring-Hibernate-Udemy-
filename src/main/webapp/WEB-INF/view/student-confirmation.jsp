<%--
  Created by IntelliJ IDEA.
  User: HK_Laptop
  Date: 2020-04-06
  Time: 오후 8:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Student Confirmation</title>
</head>
<body>
    The student is confirmed: ${student.firstName} ${student.lastName} <br/><br/>
    Country : ${student.country} <br/><br/>
    Favorite Language : ${student.favoriteLanguage}<br/><br/>
    Operating Systems :
    <ul>
        <c:forEach var="temp" items="${student.operatingSystems}">
            <li>${temp}</li>
        </c:forEach>
    </ul>
</body>
</html>
