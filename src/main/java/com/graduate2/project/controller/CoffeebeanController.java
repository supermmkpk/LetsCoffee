package com.graduate2.project.controller;

import com.graduate2.project.domain.*;
import com.graduate2.project.dto.PromotionDto;
import com.graduate2.project.service.CafeService;
import com.graduate2.project.service.MenuService;
import com.graduate2.project.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Controller
@RequestMapping(value="/coffeebean")
@RequiredArgsConstructor
public class CoffeebeanController {
    private final MenuService menuService;
    private final PromotionService promotionService;
    private final CafeId cafeId = CafeId.COFFEEBEAN;

    @GetMapping()
    public String coffeebean(Model model) {
        model.addAttribute("cafeName", "coffeebean");
        return "cafe";
    }

    /**
     * 메뉴
     */
    @PostMapping("/menu")
    public String menu(Model model, @RequestParam String type) throws Exception {
         List<Menu> menuList = menuService.findByCafeAndType(cafeId, MenuType.valueOf(type));

        model.addAttribute("menuList", menuList);

        model.addAttribute("type", type); //전달 받은 메뉴 타입

        return "menu";
    }
    /**
     * 이벤트
     */
    @GetMapping("/promotion")
    public String promotion(Model model) throws Exception {

        List<Promotion> promotions = promotionService.findAll();

        List<PromotionDto> promotionList = new ArrayList<>();

        for(Promotion promotion : promotions) {
            PromotionDto promotionDto = PromotionDto.builder()
                    .name(tokenizer(promotion.getName()))
                    .period(promotion.getPeriod())
                    .store(promotion.getStore())
                    .content(tokenizerList(promotion.getContent()))
                    .specialInfo(tokenizerList(promotion.getSpecialInfo()))
                    .build();
            promotionList.add(promotionDto);
        }

        model.addAttribute("promotionList", promotionList);

        return "promotion";
    }

    /**
     * 개행을 위해 문자열 쪼개기 (한 줄씩 반환)
     */
    private List<String> tokenizerList(String str) {
        List<String> result = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(str, "<br>");
        while(st.hasMoreTokens()) {
            result.add(st.nextToken());
        }
        return result;
    }
    /**
     * 개행을 위해 문자열 쪼개기 (첫 줄만 반환)
     */
    private String tokenizer(String str) {
        StringTokenizer st = new StringTokenizer(str, "<span>");
        return st.nextToken();
    }

}
