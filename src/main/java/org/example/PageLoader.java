package org.example;

import org.openqa.selenium.WebDriver;

public abstract class PageLoader {

    protected WebDriver driver;

    public PageLoader(WebDriver driver) {
        this.driver = driver;
    }

    public abstract boolean loadPage();
}
