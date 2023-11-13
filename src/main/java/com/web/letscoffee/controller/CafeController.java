package com.web.letscoffee.controller;

import com.web.letscoffee.domain.*;
import com.web.letscoffee.dto.PromotionDto;
import com.web.letscoffee.service.MenuService;
import com.web.letscoffee.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/{cafeName}")
@RequiredArgsConstructor
public class CafeController {
    private final MenuService menuService;
    private final PromotionService promotionService;
    private CafeId cafeId;

    @GetMapping("")
    public String cafe(Model model, @PathVariable("cafeName") String cafeName) {
        if(cafeName.equals("favicon.ico")) { //IllegalArgumentException 처리.
            return "redirect:/";
        }
        else {
            model.addAttribute("cafeName", cafeName);
            cafeId = CafeId.valueOf(cafeName.toUpperCase());
            return "cafe";
        }

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
     * 전체 이벤트
     */
    @PostMapping("/promotion")
    public String promotion(Model model, @RequestParam String type) throws Exception {

        List<Promotion> promotionList = promotionService.findByCafeAndType(cafeId, PromotionType.valueOf(type));
        model.addAttribute("type", type);

        switch(type) {
            /* 전체 이벤트 */
            case "ALL":
                model.addAttribute("promotionList", promotionList);
                break;

            /* 매장 이벤트 */
            case "STORE":
                List<PromotionDto> promotionDtoList = new ArrayList<>();
                for (Promotion promotion : promotionList) {
                    PromotionDto promotionDto = PromotionDto.builder()
                            .name(promotion.getName())
                            .type(promotion.getType())
                            //.image(promotion.getImage())
                            .period(promotion.getPeriod())
                            .store(promotion.getStore())
                            .content(tokenizerList(promotion.getContent())) //tokenizer로 파싱
                            .build();
                    promotionDtoList.add(promotionDto);
                }
                model.addAttribute("promotionList", promotionDtoList);
                break;
        } //end of switch

        return "promotion";
    }


    /**
     * 개행을 위해 문자열 쪼개기 (한 줄씩 반환)
     */
    private String[] tokenizerList(String str) {
        return str.split("<br>");
    }

}
