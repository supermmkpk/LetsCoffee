package com.graduate2.project.controller;

import com.graduate2.project.dto.UserDto;
import com.graduate2.project.exception.NoSuchUserException;
import com.graduate2.project.exception.Over5FavoriteException;
import com.graduate2.project.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;

import static java.lang.System.out;

@Controller
@RequiredArgsConstructor
public class MapController {
    private final FavoriteService favoriteService;
    private final HttpSession httpSession;
    @RequestMapping("/map")
    public String map() {
        return "kakaoMap";
    }

    @PostMapping("/map")
    public String favorite(@RequestParam("storeName") String storeName, HttpServletResponse response) throws Exception {
        UserDto user = (UserDto) httpSession.getAttribute("user");

        if (user != null) {
            try {
                favoriteService.addFavorite(user.getId(), storeName);
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<script>alert('즐겨찾는 매장에 추가했습니다.'); window.location='/map';</script>");
                out.flush();
            } catch(Over5FavoriteException e1) {
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<script>alert('즐겨찾기는 최대 5개입니다. 삭제를 위해 마이페이지로 이동합니다.'); window.location='/mypage';</script>");
                out.flush();
            } catch(NoSuchUserException e2) {
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<script>alert('다시 로그인해 주세요.'); window.location='/map';</script>");
                out.flush();
            }
        }

        return "redirect:/map";
    }
}
