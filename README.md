# Spring MVC Form Validation - Creating Custom Validation Rules
## 1. 유효성 검사를 하는 Custom Annotaion 만들기
### 커스텀 어노테이션 생성
* 어노테이션 사용 용도
    * **컴파일러에게 코드 문법 에러를 체크하도록 정보를 제공** (지금 사용하는 용도)
    * 소프트웨어 개발 툴이 빌드나 배치 시 코드를 자동으로 생성할 수 있도록 정보를 제공
    * 실행시(런타임 시) 특정 기능을 실행하도록 정보를 제공
> **CourseCode.java**
> * `@Target` : 어노테이션의 적용 대상을 지정한다
>   * *ElementType.METHOD* 와 *ElementType.FIELD* 속성으로 메소드와 필드에 어노테이션을 적용
> * `Retention` : 어노테이션을 언제까지 유지할 지 정의할 수 있다
>   * *RetentionPolicy.RUNTIME* 속성으로 런타임 시에도 어노테이션 정보를 얻을 수 있다

```java
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CourseCode {
    public String value() default "LUV";
}
```

### 유효성 검사 규칙 만들기
* ConstraintValidator 인터페이스를 구현하여 유효성 검사 규칙을 만들 수 있다.
> **CourseCodeConstraintValidator.java**
> * public interface ConstraintValidator<A extends Annotation, T>
>   * A : 유효성 검사 규칙을 적용할 어노테이션
>   * T : 유효성 검사 규칙의 성공, 실패를 확인하는 타입
> * `initialize()` : 유효성 검사를 하기전에 실행하는 초기화 메소드
>
> * `isValid()` : 유효성 검사를 하는 메소드
>   * 유효성 검사 성공 조건을 true 와 false 를 반환하는 것으로 정의 할 수 있다.
>   * 여기서 사용한 *startsWith()* 는 String 클래스가 제공하는 기본 메소드이며 해당 문자열로 실행하는지 확인한다
```java
public class CourseCodeConstraintValidator implements ConstraintValidator<CourseCode, String> {
    private String coursePrefix;

    @Override
    public void initialize(CourseCode courseCode) {
        coursePrefix = courseCode.value();
    }

    @Override
    public boolean isValid(String theCode, ConstraintValidatorContext constraintValidatorContext) {
        boolean result;

        if (theCode != null) {
            result = theCode.startsWith(coursePrefix);
        } else {
            result = true;
        }

        return result;
    }
}
```

### 유효성 검사 규칙을 커스텀 어노테이션에 적용하기
* @Constraint 로 유효성 검사 규칙을 어노테이션에 적용할 수 있다

> **CourseCode.java**
> * `@Constraint` 의 **validatedBy** 속성으로 직접 만든 유효성 검사 규칙을 지정할 수 있다
> * @Constraint 을 적용때 반드시 어노테이션에서 다음과 같은 속성을 추가해줘야 한다
>   * **public String message() default [...]** : 유효성 검사 실패 에러 메시지
>   * **public Class<?>[] groups() default {}**
>   * **public Class<? extends Payload>[] payload() default {}**

```java
@Constraint(validatedBy = CourseCodeConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CourseCode {
    // define default course code
    public String value() default "LUV";

    // define default error message
    public String message() default "must start with LUV";

    // define default groups
    public Class<?>[] groups() default {};

    // define default payloads
    public Class<? extends Payload>[] payload() default {};
}
```

## 2. 커스텀 어노테이션으로 유효성 검사 해보기
> **Customer.java**
> * `@CourseCode` : 커스텀 어노테이션으로 시작하는 문자열이 value 속성값과 같은 지 유효성 검사를 한다
>   * 기본값은 value = "LUV" 이고 message = "must start with LUV" 로 정의를 해놨다

```java
@NoArgsConstructor
@Getter @Setter
public class Customer {
    /* 생략 */
    // @CourseCode
    @CourseCode(value = "TOPS", message = "must start with TOPS")
    private String courseCode;
}
```

## 3. 실행 결과
* 문자열은 반드시 TOPS 로 시작해야 한다.   

<img src="/Image01.PNG"></img>
