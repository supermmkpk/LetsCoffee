package com.graduate2.project.controller;

import com.graduate2.project.domain.CafeId;
import com.graduate2.project.domain.Favorite;
import com.graduate2.project.domain.Promotion;
import com.graduate2.project.dto.UserDto;
import com.graduate2.project.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
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
}
