# Spring MVC Form Validation - Validation Number Ranges and Regular Expressions
## 1. 숫자 범위에 대한 유효성 검사

> **Customer.java**
> * `@Min` : 입력되는 숫자는 해당 어노테이션으로 설정된 최솟값보다 같거나 커야한다.
> * `@Max` : 입력되는 숫자는 해당 어노테이션으로 설정된 최댓값보다 같거나 작아야한다
> * @Valid 로 유효성 검사 결과를 확인할 수 있다 (15장 참고)
```java
@NoArgsConstructor
@Getter @Setter
public class Customer {
    /* 생략 */
    
    @Min(value = 0, message = "must be greater than or equal to zero")
    @Max(value = 10, message = "must be less than or equal to 10")
    private int freePasses;
}
```

## 2. Regular Expression (정규 표현식)
* 특정한 규칙을 가진 문자열의 집합을 표현하는 데 사용하는 형식 언어
* `@Pattern` 으로 정규표현식을 이용하여 원하는 문자열 집합으로 제한할 수 있다

>**Customer.java**
> * `@Pattern` 의 **regexp** 속성으로 정규표현식을 사용할 수 있다.
>   * `^x` : 문자열이 x로 시작해야 한다
>   * `[x-z]` : x-z 사이의 문자 중 하나를 찾는다
>   * `x{n}` : x를 n번 반복한 문자를 찾는다
```java
@NoArgsConstructor
@Getter @Setter
public class Customer {
    /* 생략 */
    
    @Pattern(regexp = "^[a-zA-Z0-9]{5}", message = "only 5 chars/digits")
    private String postalCode;
}
```
-------------------------
## 3. 다른 조건에서 발생할 수 있는 에러들 (기본적으로 제공해주는 유효성 검사)
### int 형 데이터에 null 이 입력되는 경우
* null 이 입력되는 경우 String -> int 로 변환이 안되서 오류가 발생한다
    * typeMismatch 유효성 검사에 실패하여 발생한 오류 메시지
    
<img src="/Image01.PNG"></img>

* int 를 Integer 클래스로 바꾸면 해결할 수 있다.
> **Customer.java**
> * null 이 입력되면 `@NotNull`에 유효성 검사 규칙이 적용된다
```java
@NoArgsConstructor
@Getter @Setter
public class Customer {
    @NotNull(message = "is required")
    @Min(value = 0, message = "must be greater than or equal to zero")
    @Max(value = 10, message = "must be less than or equal to 10")
    private Integer freePasses;
}
```
<br/>

### 숫자 데이터에 문자가 입력되는 경우
* 문자가 입력되는 경우 String -> Integer 로 변환이 안되서 오류가 발생한다
* 해당 에러는 **typeMismatch** 유효성 검사에 의해 발생되어 오류 메시지가 출력된다
    * 기본적으로 지원해주는 유효성 검사인듯
* **typeMismatch** 에 의해 발생되는 오류 메시지를 **ResourceBundleMessageSource** 로 변경할 수 있다.

#### 1. 유효성 검사 결과 Log 확인하기
> **CustomController.java**
> * BindingResult 객체를 출력하면 유효성 검사 결과 Log를 출력해준다
```java
@Controller
@RequestMapping("/customer")
public class CustomerController {
@PostMapping("/processForm")
    public String processForm(@Valid @ModelAttribute("customer") Customer theCustomer,
                              BindingResult bindingResult) {
        System.out.println("Binding result : " + bindingResult);
        /* 생략 */
    }
}
```

#### 2. 출력된 유효성 검사 결과 Log
> **Log**
> * customer 오브젝트의 freePasses 필드에서 오류가 발생한 것을 확인할 수 있다
> * codes [..] 을 보면 typeMismatch 유효성 검사가 실패한 것을 확인할 수 있다
>   * NotNull 유효성 검사가 실패한 경우에는 codes [...] 에 NotNull 이 적혀있는 것을 확인할 수 있다
> * 에러메시지 출력 결과 범위
>   * `typeMismatch.customer.freePasses` : customer 객체의 freePasses 필드에서 typeMismatch 유효성 검사가 실패했을 때 에러메시지
>   * `typeMismatch.freePasses` : 모든 객체의 freePasses 필드에서 typeMismatch 유효성 검사가 실패했을 때 에러메시지
>   * `typeMismatch.java.lang.Integer` : 모든 객체에서 Integer 타입의 필드에서 typeMismatch 유효성 검사가 실패했을 때 에러메시지
>   * `typeMismatch` : typeMismatch 유효성 검사가 실패했을 때 에러메시지
> * 같은 유효성 검사가 실패했을 때 조건에 따라 다양하게 에러메시지를 줄 수 있다.
```
Binding result : org.springframework.validation.BeanPropertyBindingResult: 1 errors
Field error in object 'customer' on field 'freePasses': rejected value [safsdf]; codes [typeMismatch.customer.freePasses,typeMismatch.freePasses,typeMismatch.java.lang.Integer,typeMismatch]; ...(등등)
```

### 3. ResourceBundleMessageSource 로 에러메시지 변경하기
> **ApplicationContext 설정파일**
> * 스프링에서 제공하는 ResourceBundleMessageSource 로 에러 메시지를 변경 할 수있다
> * **basenames** 필드에 프로퍼티파일로 value를 설정해서 빈으로 등록하면 된다
>   * 프로퍼티 파일은 src/resources 디렉토리에 위치해야 한다

```xml
<beans>
    <!-- 생략 -->
    <bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
        <property name="basenames" value="messages"/>
    </bean>
</beans>
```

<br/>

> **messages.properties**
> * 해당 프로퍼티 파일은 src/resources 디렉토리에 위치한다
> * **typeMismatch.customer.freePasses** 의 유효성 검사가 실패한 경우 에러 메시지로 "Invalid number" 를 넘겨준다
>   * customer 객체의 freePasses 필드에서 typeMismatch 유효성 검사가 실패했을 때 에러메시지
```properties
typeMismatch.customer.freePasses = Invalid number
```

