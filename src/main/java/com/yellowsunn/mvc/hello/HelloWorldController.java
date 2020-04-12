package com.yellowsunn.mvc.hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/hello")
public class HelloWorldController {
    // need a controller method to show the initial HTML form

    @GetMapping("/showForm")
    public String showForm() {
        return "hello/helloWorld-form";
    }

    // need a controller method to process the HTML form
    @GetMapping("/processForm")
    public String processForm() {
        return "hello/helloWorld";
    }

    // new a controller method to read form data and
    // add data to the model
    @GetMapping("/processFormVersionTwo")
    public String letsShoutDude(HttpServletRequest request, Model model) {
        // read the request parameter from the HTML form
        String theName = request.getParameter("studentName");

        // convert the data to all caps
        theName = theName.toUpperCase();

        // create the message
        String result = "Yo! " + theName;

        // add message to the model
        model.addAttribute("message", result);

        return "hello/helloWorld";
    }

    @GetMapping("/processFormVersionThree")
    public String processFormVersionThree(@RequestParam("studentName") String theName, Model model) {
//        @RequestParam("studentName") String theName 은 사실 아래 문장과 동일하다
//        String theName = request.getParameter("studentName");

        // convert the data to all caps
        theName = theName.toUpperCase();

        // create the message
        String result = "Hey My Friend from v3!  " + theName;

        // add message to the model
        model.addAttribute("message", result);

        return "hello/helloWorld";
    }
}