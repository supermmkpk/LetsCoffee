package com.web.letscoffee.controller;

import com.web.letscoffee.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public void loginSuccess(HttpServletResponse response) throws IOException {

        UserDto user = (UserDto) httpSession.getAttribute("user");

        if (user != null) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            String script = "<script>alert('환영합니다! " + user.getName() + "(" + user.getProvider() + ") 님.'); window.location.href='/' </script>";
            out.println(script);
            out.flush();
        }
    }
}
