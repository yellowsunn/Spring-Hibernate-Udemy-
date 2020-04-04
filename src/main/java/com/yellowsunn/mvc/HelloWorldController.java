package com.yellowsunn.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloWorldController {
    // need a controller method to show the initial HTML form

    @GetMapping("/showForm")
    public String showForm() {
        return "helloWorld-form";
    }

    @GetMapping("/processForm")
    public String processForm() {
        return "helloWorld";
    }

    // need a controller method to process the HTML form
}
