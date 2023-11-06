package com.graduate2.project.controller;

import com.graduate2.project.domain.CafeId;
import com.graduate2.project.domain.Favorite;
import com.graduate2.project.domain.Promotion;
import com.graduate2.project.dto.UserDto;
import com.graduate2.project.repository.FavoriteRepository;
import com.graduate2.project.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/mypage")
public class MyPageController {
    private final HttpSession httpSession;
    private final FavoriteService favoriteService;
    //private final FavoriteRepository favoriteRepository;

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
    public String saveInfo(Model model, @RequestParam("favoriteId") Long id, @RequestParam("wifipass") String wifipass, @RequestParam("toiletpass") String toiletpass){
        favoriteService.addFavoriteInfo(id, wifipass, toiletpass);

        return "redirect:/mypage";
    }
}
