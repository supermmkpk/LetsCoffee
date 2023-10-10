package com.graduate2.project.controller;

import com.graduate2.project.dto.UserDto;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
public class UserController {
    @GetMapping("/loginInfo")
    public String getJson(Model model, HttpSession httpSession){

        UserDto user = (UserDto) httpSession.getAttribute("user");

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "redirect:/";
    }

    @PostMapping("/mypage")
    public String mypage(){ return "/mypage"; }

}
