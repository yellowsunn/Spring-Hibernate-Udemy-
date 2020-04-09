package com.yellowsunn.mvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Value("#{countryOptions}")
    private Map<String, String> countryOptions;

    @GetMapping("/showForm")
    public String showForm(Model model) {
        // create a student object
        Student student = new Student();

        // add student object to the model
        model.addAttribute("student", student);
        model.addAttribute("theCountryOptions", countryOptions);

        return "student-form";
    }

    @PostMapping("/processForm")
    public String processForm(@ModelAttribute("student") Student theStudent) {
        // log the input data
        System.out.println("Student : " + theStudent.getFirstName() + " " + theStudent.getLastName());
        return "student-confirmation";
    }
}
