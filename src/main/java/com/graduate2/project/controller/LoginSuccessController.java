package com.graduate2.project.controller;

import com.graduate2.project.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginSuccessController {
    private final HttpSession httpSession;
    @RequestMapping("/login_success")
    public String loginSuccess(HttpServletResponse response, HttpServletRequest request) throws IOException {

        // 세션 스토리지에서 이전 페이지 URL을 가져옴
        String redirectURL = (String) request.getSession().getAttribute("currentURL");

        UserDto user = (UserDto) httpSession.getAttribute("user");

        if (user != null) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            String script = "<script>alert('환영합니다! " + user.getName() + "(" + user.getProvider() + ") 님.'); window.location.href='/' </script>";
            out.println(script);
            out.flush();
            if(redirectURL != null && !redirectURL.isEmpty()){
                return "redirect:" + redirectURL;
            }
        }
        return "redirect:/";
    }
}
