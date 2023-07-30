package org.example.tkani;

import org.example.Loader;
import org.example.PageLoader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TkaniMainPageLoader extends PageLoader {
    public TkaniMainPageLoader(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean loadPage() {
        return Loader.load(() -> {
            boolean isFound = false;
            List<WebElement> innerElements = driver.findElements(By.className("search-wrap"));
            for (WebElement innerElement : innerElements) {
                List<WebElement> target = innerElement.findElements(By.tagName("input"));
                for (WebElement te : target) {
                    if ("submit".equals(te.getAttribute("type"))) {
                        isFound = true;
                    }
                }
            }
            return isFound;
        });
    }
}
