package com.example.cucumber_selenium.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {

    private WebDriver driver;

    public WebDriver getDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();

    		ChromeOptions options = new ChromeOptions();
    		options.addArguments("--remote-allow-origins=*");
    		
            driver = new ChromeDriver(options);
        }
        return driver;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}