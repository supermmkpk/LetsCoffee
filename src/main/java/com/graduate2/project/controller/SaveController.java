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
    public String save() throws IOException {
        for(CafeId name : CafeId.values()) {
            Cafe cafe = new Cafe();
            cafe.setId(name);

            cafeService.save(cafe);
            savePromotion(cafe);
            saveMenu(cafe);
        }

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
    private void saveMenu(Cafe cafe) throws IOException {
        switch (cafe.getId()) {
            /** 스타벅스
             * 동적, SELENIUM
             */
            case STARBUCKS:
                for (MenuType type : MenuType.values()) {
                    //메뉴 url
                    driver = seleniumCrawlService.driver("https://www.starbucks.co.kr/menu/" + type.toString().toLowerCase() + "_list.do");

                    //==cssSelector를 통해 추출==//
                    List<WebElement> elements = driver.findElements(By.cssSelector("div.product_list li.menuDataSet"));


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
                break;

            /** 커피빈
             * 정적, JSOUP
             */
            case COFFEEBEAN:
                Document doc = Jsoup.connect("https://www.coffeebeankorea.com/menu/list.asp?category=32").get();


                for(int i = 1; i <= 2; i++) {
                    String query = "ul.lnb_wrap2 > li:nth-child(" + i + ") > ul > li";
                    Elements categories = doc.select(query);

                    for (Element category : categories) {
                        String url = category.select("a").attr("abs:href");
                        doc = Jsoup.connect(url).get();
                        Elements paging = doc.select("div.paging > a:not(.next)");
                        int nPage = paging.size();
                        System.out.println(nPage);

                        for (int j = 0; j < nPage; j++) {
                            Elements elements = doc.select("ul.menu_list._thumb5 > li");

                            for (Element element : elements) {
                                Menu menu = new Menu();
                                // 메뉴이름
                                menu.setName(element.select("dl.txt > dt > span.kor").text());
                                //메뉴 사진
                                menu.setImage(element.select("figure.photo > img").attr("abs:src"));
                                //메뉴 타입
                                if(i == 1)
                                    menu.setType(MenuType.DRINK);
                                else if(i == 2)
                                    menu.setType(MenuType.FOOD);
                                menu.setCafe(cafe);

                                menuService.save(menu);
                            }
                            String nextUrl = doc.select("div.paging > a.next").attr("abs:href");
                            if (!nextUrl.isEmpty()) {
                                doc = Jsoup.connect(nextUrl).get();
                            }
                        }
                    }
                }
                break;


        }

    }
}
