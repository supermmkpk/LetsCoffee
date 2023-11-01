package com.graduate2.project.controller;

import com.graduate2.project.domain.*;
import com.graduate2.project.dto.UserDto;
import com.graduate2.project.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
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
            model.addAttribute("userName", user.getName());

            List<Favorite> userFavoriteList = favoriteService.findAllById(user.getId());
            model.addAttribute("userFavoriteList", userFavoriteList);
        }

        return "main";
    }
}