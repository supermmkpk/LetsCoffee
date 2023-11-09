package com.web.letscoffee.controller;

import com.web.letscoffee.dto.UserDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/oauth")
public class UserController {
    @GetMapping("/loginInfo")
    public String getJson(Model model, HttpSession httpSession) {

        UserDto user = (UserDto) httpSession.getAttribute("user");

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "redirect:/";
    }

    @PostMapping("/mypage")
    public String mypage(){ return "/mypage"; }

}
