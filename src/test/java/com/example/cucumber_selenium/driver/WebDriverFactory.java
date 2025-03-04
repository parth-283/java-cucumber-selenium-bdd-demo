package com.example.cucumber_selenium.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private String baseUrl = "http://localhost:5000";

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public WebDriver getDriver() {
		return getDriver(null);
	}

	public WebDriver getDriver(String relativeUrl) {
		if (driver.get() == null) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			driver.set(new ChromeDriver(options));
		}

		if (relativeUrl != null && !relativeUrl.isEmpty()) {
			driver.get().get(baseUrl + relativeUrl); // Navigate to base URL + relative URL
		} else if (baseUrl != null && !baseUrl.isEmpty()) {
			driver.get().get(baseUrl); // Navigate to base URL if relative URL is not provided
		}

		return driver.get();
	}

	public void quitDriver() {
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
		}
	}
}
