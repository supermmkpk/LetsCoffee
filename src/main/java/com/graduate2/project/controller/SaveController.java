package com.graduate2.project.controller;

import com.graduate2.project.domain.*;
import com.graduate2.project.service.CafeService;
import com.graduate2.project.service.MenuService;
import com.graduate2.project.service.PromotionService;
import com.graduate2.project.service.SeleniumCrawlService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

@Controller
@RequiredArgsConstructor
public class SaveController {
    private final CafeService cafeService;
    private final PromotionService promotionService;
    private final MenuService menuService;
    private final SeleniumCrawlService seleniumCrawlService;
    private WebDriver driver;
    private List<WebElement> elements; //selenium 사용 시 이용

    @RequestMapping("/save")
    public String save() throws Exception {
        for(CafeId id : CafeId.values()) {
            Cafe cafe = new Cafe();
            cafe.setId(id);

            cafeService.save(cafe);
            savePromotion(cafe);
            saveMenu(cafe);
        }

        return "redirect:/";
    }

    /**
     * 이벤트(프로모션) 크롤링 후 DB저장
     */
    private void savePromotion(Cafe cafe) throws Exception {
        CafeId id = cafe.getId();

        /** 스타벅스
         * 동적, SELENIUM
         */
        if(id == CafeId.STARBUCKS) {
            /* 전체 이벤트 */
            driver = seleniumCrawlService.driver("https://www.starbucks.co.kr/whats_new/campaign_list.do");

            elements = driver.findElements(By.cssSelector("div.campaign_list > dl > dd:nth-child(2) li"));
            for (WebElement element : elements) {
                Promotion promotion = new Promotion();
                // 프로모션 이름
                promotion.setName(element.findElement(By.cssSelector("img"))
                        .getAttribute("alt"));
                // 프로모션 이미지
                promotion.setImage(element.findElement(By.cssSelector("img"))
                        .getAttribute("src"));
                // 프로모션 링크
                String prod = element.findElement(By.cssSelector("a.goPromotionView")).getAttribute("prod");
                String url = "https://www.starbucks.co.kr/whats_new/campaign_view.do?pro_seq=" + prod + "&menu_cd=";
                promotion.setLink(url);
                // 프로모션 기간
                promotion.setPeriod(element.findElement(By.cssSelector("p.date"))
                        .getText());
                // 프로모션  타입
                promotion.setType(PromotionType.ALL);
                promotion.setCafe(cafe);

                promotionService.save(promotion);
            } //end of for loop(전체)


            /* 매장 별 이벤트 */

            driver = seleniumCrawlService.driver("https://www.starbucks.co.kr/whats_new/store_event_list.do");

            elements = driver.findElements(By.cssSelector("div.store_event_list > ul > li"));
            for (WebElement element : elements) {
                Promotion promotion = new Promotion();
                // 프로모션 이름
                String nameHTML = element.findElement(By.cssSelector("h4")).getAttribute("innerHTML");
                promotion.setName(tokenizer(nameHTML)); //tokenizer로 파싱
                // 프로모션 기간
                promotion.setPeriod(element.findElement(By.cssSelector(".date"))
                        .getText());
                // 프로모션 매장
                promotion.setStore(element.findElement(By.cssSelector(".store_t"))
                        .getAttribute("innerText"));
                // 프로모션 내용(특이사항까지)
                String content = element.findElement(By.cssSelector("ol")).getAttribute("innerHTML")
                        + " <br> " + element.findElement(By.cssSelector(".se_info")).getAttribute("innerHTML");
                promotion.setContent(content);
                // 프로모션 타입
                promotion.setType(PromotionType.STORE);
                promotion.setCafe(cafe);

                promotionService.save(promotion);
            } //end of for loop(매장별)
        }//end of STARBUCKS


        /** 커피빈
         * 정적, JSOUP
         */
        else if(id == CafeId.COFFEEBEAN) {
            Document doc = Jsoup.connect("https://www.coffeebeankorea.com/promotion/list.asp?category=1").get();

            Elements elems = doc.select("div.promotion_list > ul > li");
            for (Element elem : elems) {
                Promotion promotion = new Promotion();
                // 프로모션 이름
                promotion.setName(elem.select("span.tit")
                        .text());
                // 프로모션 이미지
                promotion.setImage(elem.select("img")
                        .attr("abs:src"));
                // 프로모션 링크
                promotion.setLink(elem.select("a")
                        .attr("abs:href"));
                // 프로모션 타입
                promotion.setType(PromotionType.ALL);
                promotion.setCafe(cafe);

                promotionService.save(promotion);
            } //end of for loop
        }//end of COFFEEBEAN


        /** 메가
         * 동적, SELENIUM
         */
         else if(id == CafeId.MEGA) {
            driver = seleniumCrawlService.driver("https://www.mega-mgccoffee.com/bbs/?bbs_category=3");

            elements = driver.findElements(By.cssSelector("div.cont_list.cont_list4.cont_list_m.cont_list_m2.cont_gallery_list.board_list_gallery > ul > li > a"));
            for (WebElement element : elements) {
                try {
                    Promotion promotion = new Promotion();
                    // 프로모션 이름
                    promotion.setName(element.findElement(By.cssSelector("div.text.text1"))
                            .getText());
                    // 프로모션 이미지
                    promotion.setImage(element.findElement(By.cssSelector("img"))
                            .getAttribute("src"));

                    // 프로모션 링크
                    promotion.setLink(element.getAttribute("href"));
                    // 프로모션 타입
                    promotion.setType(PromotionType.ALL);
                    promotion.setCafe(cafe);

                    promotionService.save(promotion);
                } catch (Exception e) {
                    break;
                }
            } //end of for loop
        } //end of MEGA

    } //end of savePromotion()


    /**
     * 메뉴 크롤링 후 DB 저장
     */
    private void saveMenu(Cafe cafe) throws Exception {
        CafeId id = cafe.getId();
        /** 스타벅스
         * 동적, SELENIUM
         */
        if(id == CafeId.STARBUCKS) {
            for (MenuType type : MenuType.values()) {
                //메뉴 url
                driver = seleniumCrawlService.driver("https://www.starbucks.co.kr/menu/" + type.toString().toLowerCase() + "_list.do");

                elements = driver.findElements(By.cssSelector("div.product_list li.menuDataSet"));
                for (WebElement element : elements) {
                    Menu menu = new Menu();
                    // 메뉴이름
                    menu.setName(element.findElement(By.cssSelector("dl > dd"))
                            .getText());
                    // 메뉴 사진
                    menu.setImage(element.findElement(By.cssSelector("img"))
                            .getAttribute("src"));
                    // 메뉴 타입
                    menu.setType(type);
                    menu.setCafe(cafe);

                    menuService.save(menu);
                } //end of for loop
            } //end of for loop
        }//end of STARBUCKS


        /** 커피빈
         * 정적, JSOUP
         */
        else if(id == CafeId.COFFEEBEAN) {
            Document doc = Jsoup.connect("https://www.coffeebeankorea.com/menu/list.asp?category=32").get();

            for (int i = 1; i <= 2; i++) {
                String query = "ul.lnb_wrap2 > li:nth-child(" + i + ") > ul > li";

                Elements categories = doc.select(query);
                for (Element category : categories) {
                    String url = category.select("a").attr("abs:href");
                    doc = Jsoup.connect(url).get();
                    Elements paging = doc.select("div.paging > a:not(.next)");
                    int nPage = paging.size();

                    for (int j = 0; j < nPage; j++) {
                        Elements elements = doc.select("ul.menu_list._thumb5 > li");
                        for (Element element : elements) {
                            Menu menu = new Menu();
                            // 메뉴이름
                            menu.setName(element.select("dl.txt > dt > span.kor")
                                    .text());
                            //메뉴 사진
                            menu.setImage(element.select("figure.photo > img")
                                    .attr("abs:src"));
                            //메뉴 타입
                            if (i == 1)
                                menu.setType(MenuType.DRINK);
                            else if (i == 2)
                                menu.setType(MenuType.FOOD);
                            menu.setCafe(cafe);

                            menuService.save(menu);
                        }
                        String nextUrl = doc.select("div.paging > a.next").attr("abs:href");
                        if (!nextUrl.isEmpty()) {
                            doc = Jsoup.connect(nextUrl).get();
                        }
                    } //end of for loop
                } //end of for loop
            } //end of for loop
        }//end of COFFEEBEAN

        /** 투썸
         */
        //else if(id == CafeId.TWOSOME) {}

        /** 메가
         * 동적, SELENIUM
         */
        else if(id == CafeId.MEGA) {
            for (int i = 1; i <= 2; i++) { //1: 음료, 2: 푸드
                String url = "https://www.mega-mgccoffee.com/menu/?menu_category1=" + i + "&menu_category2=" + i;
                driver = seleniumCrawlService.driver(url); //메뉴 url

                while (true) {
                    elements = driver.findElements(By.cssSelector("ul#menu_list > li"));
                    for (WebElement element : elements) {
                        Menu menu = new Menu();
                        // 메뉴이름
                        menu.setName(element.findElement(By.cssSelector("div.cont_text div.text.text1 > b"))
                                .getText());
                        //메뉴 사진
                        menu.setImage(element.findElement(By.cssSelector("img"))
                                .getAttribute("src"));
                        //메뉴 타입
                        if (i == 1)
                            menu.setType(MenuType.DRINK);
                        else if (i == 2)
                            menu.setType(MenuType.FOOD);
                        menu.setCafe(cafe);

                        menuService.save(menu);
                    }

                    //페이지 번호 넘어가기! (다음) 있으면!
                    try {
                        driver.findElement(By.cssSelector("a.board_page_next.board_page_link")).click();
                        Thread.sleep(1000); //stale element 오류: 뜨기도 전에 요청해서 => 기다리기.
                    } catch (Exception e) { //없으면 그만!
                        break;
                    }
                } // end of while loop
            } // end of for loop
        }//end of MEGA

    } //end of saveMenu()


    /**
     * 개행을 위해 문자열 쪼개기 (첫 줄만 반환)
     */
    private String tokenizer(String str) {
        StringTokenizer st = new StringTokenizer(str, "<span>");
        return st.nextToken();
    }
}
