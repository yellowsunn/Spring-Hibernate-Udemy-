<%--
  Created by IntelliJ IDEA.
  User: HK_Laptop
  Date: 2020-04-06
  Time: 오후 8:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Student Registration Form</title>
</head>
<body>
    <form:form action="processForm" modelAttribute="student">
        First name : <form:input path="firstName"/> <br><br/>
        Last name : <form:input path="lastName"/> <br/><br/>
        Country :
        <form:select path="country">
            <form:options items="${theCountryOptions}"/>
        </form:select> <br/><br/>

        Favorite Language :
        Java<form:radiobutton path="favoriteLanguage" value="Java"/>
        C#<form:radiobutton path="favoriteLanguage" value="C#"/>
        PHP<form:radiobutton path="favoriteLanguage" value="PHP"/>
        Ruby<form:radiobutton path="favoriteLanguage" value="Ruby"/> <br/><br/>
        <input type="submit" value="Submit"/>
    </form:form>
</body>
</html>
