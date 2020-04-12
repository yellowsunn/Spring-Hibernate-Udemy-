# Spring MVC - Request Params and Request Mappings
## 1. Request Params
* 요청한 파라미터를 컨트롤러 변수에 대입하여 쓸 수 있다   
    ex) http://localhost:8080/hello/processForm?studentName=Hankook 을 Request 할 경우   
    ? 이후에 나오는 파라미터 값 studentName을 컨트롤러에서 변수로 불러와서 사용할 수 있다.
    
* Request Params 를 컨트롤러에서 불러오는 방법
    * **HttpServletRequest.getParameter()**
    * **@RequestParam**
    
### HttpServletRequest.getParameter()
> **HelloWorldController.java**
> * HttpServletRequest.getParameter("studentName") 로 studentName 파라미터를 읽어들인다.   
>   * URL 로 ?studentName=Hankook 이 전달된 경우 studentName 의 값인 Hankook 을 읽어들임
> * 컨트롤러 메소드의 매개변수는 handlerAdapter 가 전달해준다
>   * HttpServletRequest, Model 등
> * 변경한 데이터를 모델 속성()에 추가하여 데이터를 전달해주고 있다.
``` java
@Controller
@RequestMapping("/hello")
public class HelloWorldController {
    @GetMapping("/processForm")
    public String processForm(HttpServletRequest request, Model model) {
        // HTML form으로부터 Request Parameter 를 읽는다
        String theName = request.getParameter("studentName");

        // 데이터를 전부 대문자로 변경한다
        theName = theName.toUpperCase();

        // 메시지 생성
        String result = "Yo! " + theName;

        // 메시지를 Model 에 추가한다.
        model.addAttribute("message", result);

        return "hello/helloWorld";
    }
}
```

### @RequestParam
> **HelloWorldController.java**
> * @RequestParam 으로 student 파라미터를 읽어들여 String theName에 대입한다.
>   * String theName = request.getParameter("studentName") 과 사실 동일한 표현이다

``` java
@Controller
@RequestMapping("/hello")
public class HelloWorldController {
    @GetMapping("/processForm")
    public String processForm(@RequestParam("studentName") String theName, Model model) {
        // 데이터를 전부 대문자로 변경한다
        theName = theName.toUpperCase();

        // 메시지 생성
        String result = "Hey My Friend from v3!  " + theName;

        // 메시지를 Model 에 추가한다.
        model.addAttribute("message", result);

        return "hello/helloWorld";
    }
}
```
