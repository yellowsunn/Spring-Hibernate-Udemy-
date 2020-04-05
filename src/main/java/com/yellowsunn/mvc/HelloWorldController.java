package com.yellowsunn.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloWorldController {
    // need a controller method to show the initial HTML form

    @GetMapping("/showForm")
    public String showForm() {
        return "helloWorld-form";
    }

    // need a controller method to process the HTML form
    @GetMapping("/processForm")
    public String processForm() {
        return "helloWorld";
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

        return "helloWorld";
    }
}