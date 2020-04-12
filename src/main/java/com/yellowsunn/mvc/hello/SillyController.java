package com.yellowsunn.mvc.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SillyController {
    @GetMapping("/showForm")
    public String displayTheForm() {
        return "silly";
    }
}
