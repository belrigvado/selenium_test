package org.example.tkani;

import org.checkerframework.checker.units.qual.C;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

public class TkaniTestCase {
    private static final String SITE = "https://tkani-feya.ru/";

    private WebDriver driver;

    private void makeSearch(String s) {
        driver.get(SITE);
        if (!(new TkaniMainPageLoader(driver).loadPage()))
            throw new IllegalStateException();

        List<WebElement> innerElements = driver.findElements(By.className("search-wrap"));
        for (WebElement innerElement : innerElements) {
            List<WebElement> target = innerElement.findElements(By.tagName("input"));
            boolean found = false;
            for (WebElement te : target) {
                if ("text".equals(te.getAttribute("type"))) {
                    te.sendKeys(s);
                    found = true;
                }
            }
            if (found) {
                for (WebElement te : target) {
                    if ("submit".equals(te.getAttribute("type"))) {
                        te.click();
                        return;
                    }
                }
            }
        }
        throw new IllegalStateException();
    }

    private List<String> getSearchResults() {
        if (!(new TkaniSearchResultPageLoader(driver).loadPage())) {
            throw new IllegalStateException();
        }

        List<String> results = new ArrayList<>();
        List<WebElement> innerElements = driver.findElements(By.className("catalog-list"));
        for (WebElement innerElement : innerElements) {
            List<WebElement> targetElements = innerElement.findElements(By.className("item-in"));
            for (WebElement targetElement : targetElements) {
                List<WebElement> textElements = targetElement.findElements(By.className("text-block"));
                for (WebElement textElement : textElements) {
                    List<WebElement> nameElements = textElement.findElements(By.tagName("a"));
                    for (WebElement nameElement : nameElements) {
                        results.add(nameElement.getText());
                        break;
                    }
                }
            }
        }
        return results;
    }

    @Before
    public void init() {
        // инициализируем веб-драйвер для работы
        System.setProperty("webdriver.chrome.driver", "/home/alisa/IdeaProjects/selenium_test/chromedriver_linux64/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }


    @Test
    public void positiveTest() {
        // делаем запрос со словом "замша". Ожидаем получить непустой результат, в каждом из результатов должно быть слово "замша"
        String request = "замша";
        makeSearch(request);
        List<String> searchResults = getSearchResults();
        Assert.assertFalse(searchResults.isEmpty());
        Assert.assertTrue(searchResults.stream().allMatch(s -> s.toLowerCase().contains(request.toLowerCase())));
    }

    @Test
    public void positiveTest1() {
        // делаем запрос со словом "мохер". Ожидаем получить непустой результат, в каждом из результатов должно быть слово "мохер"
        String request = "мохер";
        makeSearch(request);
        List<String> searchResults = getSearchResults();
        Assert.assertFalse(searchResults.isEmpty());
        Assert.assertTrue(searchResults.stream().allMatch(s -> s.toLowerCase().contains(request.toLowerCase())));
    }

    @Test
    public void positiveTest2() {
        // делаем запрос со словом "шелк атласный". Ожидаем получить непустой результат, в каждом из результатов должно быть слово "шелк атласный"
        String request = "шелк атласный";
        makeSearch(request);
        List<String> searchResults = getSearchResults();
        Assert.assertFalse(searchResults.isEmpty());
        Assert.assertTrue(searchResults.stream().allMatch(s -> s.toLowerCase().contains(request.toLowerCase())));
    }

    @Test
    public void positiveTest3() {
        // делаем запрос со словом "шелк". Ожидаем получить непустой результат, в каждом из результатов должно быть слово "шелк"
        String request = "шелк атласный";
        makeSearch(request);
        List<String> searchResults = getSearchResults();
        Assert.assertFalse(searchResults.isEmpty());
        Assert.assertTrue(searchResults.stream().allMatch(s -> s.toLowerCase().contains(request.toLowerCase())));

    }


    @Test
    public void positiveTest4() {
        // делаем запрос со словом "ситец". Ожидаем получить непустой результат, в каждом из результатов должно быть слово "ситец"
        String request = "ситец";
        makeSearch(request);
        List<String> searchResults = getSearchResults();
        Assert.assertFalse(searchResults.isEmpty());
        Assert.assertTrue(searchResults.stream().allMatch(s -> s.toLowerCase().contains(request.toLowerCase())));

    }
        @Test
        public void negativeTest () {
            // делаем запрос с пробелом. Ожидаем получить "заглушку" - пустую выдачу
            String request = " ";
            makeSearch(request);
            List<String> searchResults = getSearchResults();
            Assert.assertTrue(searchResults.isEmpty());
        }

        @After
        public void destroy () {
            driver.close();
        }
    }

