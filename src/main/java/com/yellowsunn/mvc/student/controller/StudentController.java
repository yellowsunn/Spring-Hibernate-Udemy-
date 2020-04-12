package com.yellowsunn.mvc.student.controller;

import com.yellowsunn.mvc.student.Student;
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
    private Map<String, String> theCountryOptions;

    @GetMapping("/showForm")
    public String showForm(Model model) {
        Student student = new Student();

        model.addAttribute("student", student);
        model.addAttribute("countryOptions", theCountryOptions);
        return "student/student-form";
    }

    @PostMapping("/processForm")
    public String processForm(@ModelAttribute("student") Student theStudent) {
        System.out.println("Student: " + theStudent.getFirstName() + " " + theStudent.getLastName());
        return "student/student-confirmation";
    }
}
