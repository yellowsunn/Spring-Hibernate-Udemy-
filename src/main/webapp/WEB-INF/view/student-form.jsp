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
            <form:option value="Brazil" label="브라질"/>
            <form:option value="France" label="프랑스"/>
            <form:option value="Germany" label="독일"/>
            <form:option value="India" label="인도"/>
        </form:select> <br/><br/>
        <input type="submit" value="Submit"/>
    </form:form>
</body>
</html>
