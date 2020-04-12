# Spring MVC Form Validation - Applying Built-In Validation Rules
## 1. Hibernate Validator
### Hibernate Validator 의존성 추가
>**pom.xml**
```xml
<dependency>
    <groupId>org.hibernate.validator</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>6.1.3.Final</version>
</dependency>
```

### Validation 규칙 적용하기
>**Custome.javar**
> * `@NotNull` : 해당 어노테이션이 적용된 부분은 Null이 될 수 없다
>   * **message 속성**으로 유효성 검사가 실패할 경우 해당 메시지를 보여준다  
> * `@Size` :  해당 어노테이션이 적용된 부분은 명시한 사이즈 범위에 속해야 한다
>   * **min 속성**으로 최소 사이즈를 지정
    
```java
@NoArgsConstructor
@Getter @Setter
public class Customer {
    private String firstName;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String lastName;
}
```

### @Valid 와 BindingResult.class
* @Valid 로 해당 객체의 유효성 검사를 수행한다
* 유효성 검사 결과를 BindingResult 에 저장한다

> **CustomerController.java**
> * `@Valid` : 해당 어노테이션이 적용된 객체(theCustomer)에 유효성 검사를 한다
>   * jsp 가 아닌 자바코드에서 유효성 검사를 수행함
>   * BindingResult 가 같이 있지 않으면 웹페이지 실행 오류가 발생한다.
> * `BindingResult` : 유효성 검사 결과는 해당 객체에 저장된다.
>   * 컨트롤러 메소드에서 BindingResult 매개변수의 위치는 반드시 @Valid 바로 오른쪽에 위치해야한다.
>   * `BindingResult.hasErrors()` 로 유효성 검사에 에러가 발생했는지 확인할 수 있다

```java
@Controller
@RequestMapping("/customer")
public class CustomerController {
    @GetMapping("/processForm")
    public String processForm(@Valid @ModelAttribute("customer") Customer theCustomer,
                              BindingResult bindingResult) {
        System.out.println("Last name : |" + theCustomer.getLastName() + "|");
        if (bindingResult.hasErrors()) {
            return "customer/customer-form";
        } else {
            return "customer/customer-confirmation";
        }
    }
}
```
----------------------------------

## 2. Spring JSP 태그 \<form:errors\>
* **\<form:errors\>** 로 유효성 검사가 실패한 경우 해당 태그로 에러 메시지를 띄울 수 있다.
  * \<span\> 태그로 에러 메시지를 출력
  * 유효성 검사가 성공할 경우 무시된다
> **customer-form.jsp**
> * `<form:errors>`
>   * **path 속성** : 에러가 발생한 오브젝트의 위치를 바인딩 한다.
>   * **cssClass 속성** : 에러메시지에 css 스타일을 적용할 수 있다.
```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Customer Registration Form</title>
    <style>
        .error {color: red}
    </style>
</head>
<body>
    Fill out form Asterisk (*) means required. <br/><br/>
    <form:form action="processForm" modelAttribute="customer" method="get">
        First name : <form:input path="firstName"/> <br/><br/>
        Last name (*): <form:input path="lastName"/>
        <form:errors path="lastName" cssClass="error"/> <br/><br/>
        <input type="submit" value="Submit"/>
    </form:form>
</body>
</html>
```
----------------------------------

## 3. 공백문자가 입력되는 것을 방지하기
* 공백문자가 HTML form 에서 전달되기 때문에 유효성 검사가 제대로 수행되지 못한다
* 해결책은?

### @InitBinder
* @Valid 로 검증이 필요한 객체를 가져오기 전에 수행할 메소드를 지정해주는 어노테이션이다.
> **CustomerController.java**
> * `StringTrimmerEditor` : 매개변수로 true 를 주면 공백문자만 있을때 null 을 만들어준다
>   * Spring API 에 정의 되어있는 클래스
> * `WebDataBinder.registerCustomEditor(String.class, stringTrimmerEditor)`
>   * 모든 String 클래스에 stringTrimmerEditor 가 적용된다 
```java
@Controller
@RequestMapping("/customer")
public class CustomerController {
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
    
    /* 생략*/
}
```
