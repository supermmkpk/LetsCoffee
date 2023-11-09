package com.web.letscoffee.controller;

import com.web.letscoffee.domain.*;
import com.web.letscoffee.dto.UserDto;
import com.web.letscoffee.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final PromotionService promotionService;
    private final HttpSession httpSession;
    private final FavoriteService favoriteService;

    @RequestMapping("/")
    public String main(Model model) {
        for (CafeId id : CafeId.values()) {
            String idStr = id.toString().toLowerCase();
            String name = idStr + "PromotionList"; //Ex) starbucksPromotionList
            List<Promotion> cafePromotionList = promotionService.findByCafeId(id);
            model.addAttribute(name, cafePromotionList);
        }
       UserDto user = (UserDto) httpSession.getAttribute("user");

        if (user != null) {
            httpSession.setAttribute("username", user.getName());

            List<Favorite> userFavoriteList = favoriteService.findAllById(user.getId());
            model.addAttribute("userFavoriteList", userFavoriteList);
        }

        return "main";
    }
}