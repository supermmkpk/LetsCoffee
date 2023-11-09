package com.web.letscoffee.controller;

import com.web.letscoffee.domain.Favorite;
import com.web.letscoffee.dto.UserDto;
import com.web.letscoffee.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/mypage")
public class MyPageController {
    private final HttpSession httpSession;
    private final FavoriteService favoriteService;

    @GetMapping("")
    public String mypage(Model model) {
        UserDto user = (UserDto) httpSession.getAttribute("user");
        if (user != null) {
            List<Favorite> userFavoriteList = favoriteService.findAllById(user.getId());
            model.addAttribute("userFavoriteList", userFavoriteList);
        }
        return "mypage";
    }

    @PostMapping("/cancelFavorite")
    public String cancelFavorite(@RequestParam("favoriteId") Long id) {
        favoriteService.cancelFavorite(id);
        return "redirect:/mypage";
    }

    @PostMapping("/saveInfo")
    public void saveInfo(HttpServletResponse response,
                         @RequestParam("favoriteId") Long id, @RequestParam("wifipass") String wifipass,
                         @RequestParam("toiletpass") String toiletpass,  @RequestParam("otherInfo") String otherInfo) throws IOException {

        try {
            //해당 매장이 즐겨찾기 테이블에 없다면 추가합니다.
            favoriteService.addFavoriteInfo(id, wifipass, toiletpass, otherInfo);
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<script> alert('매장 메모를 저장했습니다.'); window.location.href='/mypage';</script>");
                out.flush();

        } catch(Exception e) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script> alert('저장애 실패했습니다.'); window.location.href='/mypage';</script>");
            out.flush();
        }

    }
}
