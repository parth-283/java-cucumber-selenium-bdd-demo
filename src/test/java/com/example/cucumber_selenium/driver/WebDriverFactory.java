package com.example.cucumber_selenium.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;

public class WebDriverFactory {
	private final Dotenv dotenv = Dotenv.load();

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private String baseUrl = dotenv.get("PROJECT_URL");

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
			driver.get().get(baseUrl + relativeUrl);
		} else if (baseUrl != null && !baseUrl.isEmpty()) {
			driver.get().get(baseUrl);
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
