package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class LoginPage {
	WebDriver driver;
	WebDriverWait wait;

	private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

	// Locators
	private By usernameField = By.name("username");
	private By passwordField = By.name("password");
	private By loginButton = By.id("loginbtn");

	// Constructor
	public LoginPage(WebDriver driver, int waitTime) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
	}

	// Methods to perform actions on the page
	public void enterUsername(String username) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));

		driver.findElement(usernameField).sendKeys(username);
	}

	public void enterPassword(String password) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));

		driver.findElement(passwordField).sendKeys(password);
	}

	public void clickLoginButton() {
		wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
	}

	public void login(String baseURL, String username, String password) {
		logger.info("Running login page object model.");
		driver.get(baseURL + "/login");
		this.enterUsername(username);
		this.enterPassword(password);
		this.clickLoginButton();
	}
}
