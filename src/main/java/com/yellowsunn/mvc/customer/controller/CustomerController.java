package com.yellowsunn.mvc.customer.controller;

import com.yellowsunn.mvc.customer.Customer;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    // 입력 문자열을 다듬기 위해 InitBinder 추가
    // 좌측, 우측 공백을 제거해줌
    // 공백이 입력되어서 유효성 검사가 잘 안되는 문제를 해결
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showForm")
    public String showForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/customer-form";
    }

    @PostMapping("/processForm")
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
