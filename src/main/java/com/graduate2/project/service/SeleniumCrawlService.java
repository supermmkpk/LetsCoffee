package com.graduate2.project.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;


@Service
public class SeleniumCrawlService {
    private static Path path = Paths.get("C:\\Users\\user\\Downloads\\chromedriver-win64(2)\\chromedriver-win64\\chromedriver.exe");
    private WebDriver driver;


    //==크롤링 시작 시 호출: 설정, url 연결==//
    public WebDriver driver(String url) {
        //시스템에 프로퍼티 설정
        System.setProperty("webdriver.chrome.driver",path.toString());

        // 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-popup-blocking");   // 팝업 안띄움
        options.addArguments("headless");   // 브라우저 안띄움
        options.addArguments("--disable-gpu");  // gpu 비활성화
        options.addArguments("--blink-settings=imagesEnabled=false");   // 이미지 다운 안받음


        // 브라우저 선택시 파라미터로 옵션 전송
        driver = new ChromeDriver(options);
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(url);

        return driver;
    }

    //크롤링 종료 시 호출: driver 종료
    public void disconnect() {
        driver.quit();
    }

//    @PostConstruct
//    public void init() {
//        connect();
//    }

    @PreDestroy
    public void close() {
        disconnect();
    }



    /*//@PostConstruct
    public List<WebElement> getElements(String url, String elementsCss) throws IOException {

        //시스템에 프로퍼티 설정
        System.setProperty("webdriver.chrome.driver",path.toString());

        // 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-popup-blocking");   // 팝업 안띄움
        options.addArguments("headless");   // 브라우저 안띄움
        options.addArguments("--disable-gpu");  // gpu 비활성화
        options.addArguments("--blink-settings=imagesEnabled=false");   // 이미지 다운 안받음


        // 브라우저 선택시 파라미터로 옵션 전송
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(url);

        //html 분석, cssQuery로 추출
        List<WebElement> elements = driver.findElements(By.cssSelector(elementsCss));

        driver.quit();

        return elements;
    }*/
}
