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

import java.io.IOException;
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
    public void favorite(@RequestParam("storeName") String storeName, HttpServletResponse response) throws IOException {
        UserDto user = (UserDto) httpSession.getAttribute("user");

        if (user != null) {
            try {
                //해당 매장이 즐겨찾기 테이블에 없다면 추가합니다.
                if(favoriteService.findByStoreName(storeName).isEmpty()) {
                    favoriteService.addFavorite(user.getId(), storeName);
                    response.setContentType("text/html; charset=UTF-8");
                    PrintWriter out = response.getWriter();

                    out.println("<script> alert('즐겨찾는 매장에 추가했습니다.'); </script>");
                    out.flush();
                }
                //있다면 중복 추가할 수 없습니다.
                else {
                    response.setContentType("text/html; charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    out.println("<script>alert('이미 추가한 매장입니다.');</script>");
                    out.flush();
                }

            } catch(Over5FavoriteException e1) {
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<script>alert('즐겨찾기는 최대 5개입니다! 마이페이지에서 삭제 가능합니다.');</script>");
                out.flush();

            } catch(NoSuchUserException e2) {
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<script>alert('로그아웃 후 다시 로그인해 주세요.'); window.location.href='/';</script>");
                out.flush();
            }
        }
    }
}
