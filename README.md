# Spring MVC
## 1. 스프링 MVC 프로젝트 만들기
### 프로젝트 생성
    Maven 프로젝트 -> Create from archetype 체크 -> org.apache.maven.archetypes:maven-archetype-webapp 선택

### web.xml 서블릿 버전 변경
> **web.xml**
> * 서블릿 4.0 버전으로 업데이트

``` xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app
    xmlns="http://xmlns.jcp.org/xml/ns/javaee"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
    version="4.0">
```

### 서블릿 의존성 추가
* 서블릿을 사용하기 위해서는 서블릿 라이브러리가 필요하다
> **pom.xml**
> * Maven 컴파일러 버전을 지정한다
> * 의존성은 Maven Repository 사이트에서 Servlet API 검색 후 붙여넣기

``` xml
<properties>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <maven.compiler.source>11</maven.compiler.source>
  <maven.compiler.target>11</maven.compiler.target>
</properties>

<dependencies>
  <dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.2.5.RELEASE</version>
  </dependency>
  <!-- https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api -->
  <dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
    <scope>provided</scope>
  </dependency>
</dependencies>
```
--------------------------------------------------
## 2. DispatcherServlet
### DispatcherServlet 등록
* 스프링 프레임 워크가 제공해 주는 서블릿
* ApplicationContext 를 지정해주면 자동으로 생성해준다
> **web.xml**
> * DispatcherServlet 을 `/` 에 매핑했다
> * `<init-param>` 에 `contextConfiguration` 으로 ApplicationContext 설정 파일을 지정해준다
> * `<init-param>` 에 `contextClass` 로 특정 ApplicationContext 객체를 지정해줄 수도 있다
>   * 생략하면 자동으로 지정해준다
``` xml
<web-app>
  <display-name>Archetype Created Web Application</display-name>
  <servlet>
    <servlet-name>dispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>WEB-INF/spring-mvc-demo-servlet.xml</param-value>
    </init-param>
  </servlet>
  <servlet-mapping>
    <servlet-name>dispatcherServlet</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>
</web-app>
```

### DispatcherServlet 동작 원리
<img src="https://baekjungho.github.io/images/posts/201906/m3.jpg"></img>

* **handlerMapping**
  * 요청한 URL 에 해당하는 컨트롤러의 메소드를 찾는다
  * `@Controller` 가 설정된 빈들을 찾아서 적절한 메소드를 찾아낸다.   
        *ex) public java.lang.String com.yellowsunn.HelloWorldController.showForm() 을 반환*

* **handlerAdapter**
  * handlerMapping 으로 찾아낸 컨트롤러의 메소드를 실행시킨다.
  * 메소드를 실행시킬때 매개변수로 request, response, locale, model 등을 넘겨 줄 수 있다.
  * 컨트롤러 메소드의 실행 결과를 handlerAdapter 가 받아 가공해 DispatcherServlet에 넘겨준다
    * Model 과 View 정보 등 넘겨준다

* **ViewResolver**
  * HandlerMapping 에서 받은 View 정보로 적절한 View 위치를 찾는다.
  * InternalResourceViewResolver 를 빈으로 등록해 prefix, suffix 값을 지정할 수도 있다.
  #
  > **ApplicationContext 설정파일(xml)**
  ``` xml
  <beans>
      <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
          <property name="prefix" value="/WEB-INF/view/"/>
          <property name="suffix" value=".jsp"/>
      </bean>
  </beans>
  ```

* **View**
  * ViewResolver 로 찾은 View 를 찾아 보여준다

### Model 데이터 전달하기
> **Controller.java**
> * 매개변수로 request, model 을 전달해 줄 수 있다   
>    * handlerAdapter 가 자동으로 연결해준다
> * `request.getParamter()` 로 Form 에서 전달한 데이터를 받는다.
>    * `<form><input type="text" name="studentName"></form>` 에서 전달 받음
> * `model.addAttribute()` 를 이용해 Model 로 데이터를 전달한다.
``` java
@Controller
public class HelloWorldController {
    @GetMapping("/processForm")
    public String letsShoutDude(HttpServletRequest request, Model model) {
        String theName = request.getParameter("studentName");
        theName = theName.toUpperCase();
        String result = "Yo! " + theName;

        model.addAttribute("message", result);
        
        return "helloWorld";
    }
}
```

## 3. Controller 객체 구현
### Spring MVC form 태그
* 기본 `<form>` 태그 대신에 스프링 MVC 에서 지원해주는 `<form>`태그를 사용할 수 있다.
> **student-form.jsp**
> * `<%@ taglib %>` 를 이용해서 외부 라이브러리를 불러올 수 있다.   
>   * `prefix` 옵션으로 외부에서 불러올 태그를 설정할 수 있다.
> * `<form:form>` 태그의 `modelAttribute` 옵션으로 전달 받은 모델 애트리뷰트를 불러올 수 있다.
> * `<form:input>` 태그의 `path` 옵션으로 modelAttribute 로 받은 오브젝트의 필드를 getter와 setter로 바인딩 해준다.
>   * 정확히는 setter가 아닌 필드 이름을 전달해준다

``` jsp
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
        <input type="submit" value="Submit"/>
    </form:form>
</body>
</html>
```

### @ModelAttribute
* DispatcherServlet 의 HandlerAdapter 가 매개변수로 전달한 오브젝트를 모델로 전달할 수 있게 해준다.
> **StudentController.java**
> * @ModelAttribute 로 Student 객체를 모델로 넘겨준다.
>   * Model.addAttribute("student", theStudent) 와 사실상 같다.
> * HandlerAdapter 가 매개변수로 전달한 오브젝트의 setter 를 이용해서 객체를 생성해준다.
>   * jsp 에서 전달받은 정보의 이름과 오브젝트의 필드이름을 비교해서 같으면 setter 로 값을 설정한다   
>       ex) jsp 에서 "firstName" 의 이름을 가진 정보를 전달하면 HandlerAdpater 가 Student.setFirstName 으로 값을 세팅해준다.


``` java

@Controller
@RequestMapping("/student")
public class StudentController {
    @PostMapping("/processForm")
    public String processForm(@ModelAttribute("student") Student theStudent) {
        // log the input data
        System.out.println("Student : " + theStudent.getFirstName() + " " + theStudent.getLastName());
        return "student-confirmation";
    }
}
```
