# Spring MVC - Form Tags and Data Binding
## 1. JSP 에서 외부 태그 라이브러리 불러오기
* Spring 프레임워크에서 지원하는 JSP form 태그를 `taglib` 로 불러올 수 있다.
``` jsp
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
```

------------------------------------------------------------------

## 2. Spring 프레임워크 JSP Form 태그 사용
> **student-form.jsp**
> * `<form:form>`
>   * **action 속성** 은 기본적으로 POST 방식이다
>   * **modelAttribute 속성** 으로 모델 속성에 있는 오브젝트를 불러올 수 있다
> * `path 속성`으로 데이터를 바인딩할 수 있다.
>   * modelAttribute 로 불러온 오브젝트의 getter 와 setter 를 이용하여 필드를 바인딩한다.
>   * jsp 페이지가 요청될때 getter가 실행되고, form 을 전송하는 경우에 setter가 실행된다
> * `items 속성`으로 Collection, Map, array 타입의 객체를 전달받아 내부적으로 태그 리스트를 만든다.
> * <form:checkbox>
>   * `path 속성`으로 String[] 을 넘겨준다.
>       * 체크박스는 복수 선택이 가능하므로 String[] 배열을 바인딩한다
```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Student Registration Form</title>
</head>
<body>
    <form:form action="processForm" modelAttribute="student">
        First name : <form:input path="firstName"/> <br/><br/>
        Last name : <form:input path="lastName"/> <br/><br/>
        Country :
        <form:select path="country">
            <form:options items="${countryOptions}"/>
        </form:select> <br/><br/>

        Favorite Language :
        <form:radiobuttons path="favoriteLanguage" items="${student.favoriteLanguageOptions}" delimiter=" "/> <br/><br/>

        Operating Systems :
        Linux<form:checkbox path="operatingSystems" value="Linux"/>
        Mac OS<form:checkbox path="operatingSystems" value="Mac OS"/>
        MS Windows<form:checkbox path="operatingSystems" value="MS Windows"/> <br/><br/>
        <input type="submit" value="Submit"/>
    </form:form>
</body>
</html>
```
---------------------------

## 3. 프로퍼티 파일로 Map 객체 생성하기
### 프로퍼티 파일 작성
> **countries.properties**
```properties
BR = Brazil
FR = France
CO = Colombia
In = India
```

### ApplicationContext 설정파일에 프로퍼티 파일 등록하기
> **appConfig.xml**
```xml
<beans>
    <!-- 생략 -->
    <util:properties id="countryOptions" location="WEB-INF/view/student/countries.properties"/>
</beans>
```

### @Value 로 프로퍼티 파일에 저장된 값 불러오기
> **StudentController.java**
> * @Value 어노테이션에서 속성으로 "#{프로퍼티 id}" 을 주면 객체에 맞게 자동으로 생성해준다
``` java
@Controller
@RequestMapping("/student")
public class StudentController {
    @Value("#{countryOptions}")
    private Map<String, String> theCountryOptions;
    
    /* 생략 */
}
```
--------------------------------------
## 4. JSTL 태그 라이브러리
* 조건문, 반복문 등을 쉽게 작성할 수 있는 태그를 제공한다.
### JSTL 태그 라이브러리 불러오기
* JSTL 태그중에서 중요한 기능들을 담은 core 태그를 불러온다
```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
```

### JSTL 태그의 forEach 태그
* 반복문 forEach 를 실행할 수 있는 태그
> **student-confirmation.jsp**
> * items 속성에 있는 값들을 하나씩 꺼내서 var 속성의 속성값에 담는다.
>   * 해당 속성값은 ${속성값 이름} 로 불러올 수 있다.
```jsp
<html>
<head>
    <title>Student Confirmation</title></head>
<body>
    <!-- 생략 -->
    Operation Systems :
    <ul>
        <c:forEach var="temp" items="${student.operatingSystems}">
            <li>${temp}</li>
        </c:forEach>
    </ul>
</body>
</html>
```
