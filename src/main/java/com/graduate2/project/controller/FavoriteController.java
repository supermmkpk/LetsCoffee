package com.graduate2.project.controller;

import com.graduate2.project.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.graduate2.project.domain.*;
import com.graduate2.project.repository.*;

import javax.transaction.Transactional;

@Controller
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService; // 서비스 레이어의 favoriteService 주입.

    @Autowired
    private UserFavoriteRepository userFavoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/map")
    @Transactional
    public String addFavorite(@RequestParam String title) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.getPrincipal() instanceof OAuth2User){
            Favorite favorite = new Favorite();
            favorite.setTitle(title);
            favoriteService.saveFavorite(favorite);

            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

            // 사용자 정보에 접근.
            String email = oAuth2User.getAttribute("email");

            User user = userRepository.findByEmail(email).orElse(null);

            Favorite favorite2  = favoriteService.findByTitle(title);

            if (favorite2 != null && user != null){
                UserFavorite userFavorite = new UserFavorite();
                userFavorite.setUser(user);
                userFavorite.setFavorite(favorite2);
                userFavoriteRepository.save(userFavorite);
            }

        }

        return "redirect:/";
    }
}