package com.graduate2.project.controller;

import com.graduate2.project.domain.*;
import com.graduate2.project.service.CafeService;
import com.graduate2.project.service.MenuService;
import com.graduate2.project.service.PromotionService;
import com.graduate2.project.service.SeleniumCrawlService;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Iterator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SaveController {
    private final CafeService cafeService;
    private final PromotionService promotionService;
    private final MenuService menuService;
    private final SeleniumCrawlService seleniumCrawlService;
    private WebDriver driver;

    @RequestMapping("/save")
    public String save() {
        Cafe cafe = new Cafe();
        cafe.setId(CafeEnum.STARBUCKS);

        cafeService.save(cafe);
        savePromotion(cafe);
        saveMenu(cafe);

        return "redirect:/";
    }

    /**
     * 이벤트(프로모션) 크롤링 후 DB저장
     */
    private void savePromotion(Cafe cafe) {
        //==크롤링 드라이버 연결==//
        driver = seleniumCrawlService.driver("https://www.starbucks.co.kr/whats_new/store_event_list.do");

        //List<WebElement> pages = driver.findElements(By.cssSelector("div.store_event_pagination > ul > li"));

        //Iterator<WebElement> iter = pages.iterator();
        //while (iter.hasNext()) {
        //==cssSelector를 통해 추출==//
        List<WebElement> elements = driver.findElements(By.cssSelector("div.store_event_list > ul > li"));

        //List<EventDto> promotionList = new ArrayList<>();
        for (WebElement element : elements) {
            Promotion promotion = new Promotion();

            // 이벤트 이름
            promotion.setName(element.findElement(By.cssSelector("h4"))
                    .getAttribute("innerText"));
            //이벤트 기간
            promotion.setPeriod(element.findElement(By.cssSelector(".date"))
                    .getText());
            // 이벤트 매장
            promotion.setStore(element.findElement(By.cssSelector(".store_t"))
                    .getAttribute("innerText"));
            //이벤트 내용
            promotion.setContent(element.findElement(By.cssSelector("ol"))
                    .getAttribute("innerHTML"));
            //이벤트 특이사항
            promotion.setSpecialInfo(element.findElement(By.cssSelector(".se_info"))
                    .getAttribute("innerHTML"));
            promotion.setCafe(cafe);

            promotionService.save(promotion);
        }

        //==다음 페이지 있다면, 클릭==//
            /*try {
                WebElement nextPage = iter.next().findElement(By.cssSelector("a"));
                ((ChromeDriver) driver).executeScript("arguments[0].click();", nextPage);
            } catch (Exception e) {
                break;
            }*/

        //}
    }

    /**
     * 메뉴 크롤링 후 DB 저장
     */
    private void saveMenu(Cafe cafe) {
        for (MenuType type : MenuType.values()) {
            //메뉴 url
            driver = seleniumCrawlService.driver("https://www.starbucks.co.kr/menu/" + type + "_list.do");

            //==cssSelector를 통해 추출==//
            List<WebElement> elements = driver.findElements(By.cssSelector("div.product_list li.menuDataSet"));

            //List<MenuDto> menuList = new ArrayList<>();


            for (WebElement element : elements) {
                Menu menu = new Menu();
                // 메뉴이름
                menu.setName(element.findElement(By.cssSelector("dl > dd")).getText());
                //메뉴 사진
                menu.setImage(element.findElement(By.cssSelector("img")).getAttribute("src"));
                //메뉴 타입
                menu.setType(type);
                menu.setCafe(cafe);

                menuService.save(menu);
            }
        }
    }

}
