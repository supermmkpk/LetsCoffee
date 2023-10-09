package com.graduate2.project.controller;

import com.graduate2.project.domain.*;
import com.graduate2.project.dto.PromotionDto;
import com.graduate2.project.service.MenuService;
import com.graduate2.project.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;


@Controller
@RequestMapping("/{cafeName}")
@RequiredArgsConstructor
public class CafeController {
    private final MenuService menuService;
    private final PromotionService promotionService;
    private CafeId cafeId;

    @GetMapping("")
    public String cafe(Model model, @PathVariable("cafeName") String cafeName) {
        model.addAttribute("cafeName", cafeName);
        cafeId = CafeId.valueOf(cafeName.toUpperCase());
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
    private List<String> tokenizerList(String str) {
        List<String> result = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(str, "<br>");
        while(st.hasMoreTokens()) {
            result.add(st.nextToken());
        }
        return result;
    }

}
