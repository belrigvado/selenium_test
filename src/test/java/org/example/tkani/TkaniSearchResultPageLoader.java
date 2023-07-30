package org.example.tkani;

import org.example.Loader;
import org.example.PageLoader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TkaniSearchResultPageLoader extends PageLoader {
    public TkaniSearchResultPageLoader(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean loadPage() {
        return Loader.load(() -> {
            List<WebElement> innerElements = driver.findElements(By.className("catalog-list"));
            return !innerElements.isEmpty();
        });
    }
}
