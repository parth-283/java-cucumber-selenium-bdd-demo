package com.example.cucumber_selenium;

import java.time.Duration;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.cdimascio.dotenv.Dotenv;

import com.example.cucumber_selenium.driver.WebDriverFactory;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ChangePasswordSteps {
	private static final Logger logger = LoggerFactory.getLogger(ChangePasswordSteps.class);
	private final Dotenv dotenv = Dotenv.load();
	private WebDriver driver;
	private WebDriverFactory webDriverFactory;

	private String userName = dotenv.get("DB_USERNAME");
	private String oldPassword = "Parth@2002";
	private String newPassword;
	private String lastPassword;

	public ChangePasswordSteps(WebDriverFactory webDriverFactory) {
		this.webDriverFactory = webDriverFactory;
	}

	private String getExpectedRedirectUrl() {
		return "http://localhost:5000/";
	}

	@Before
	public void setUp() {
		logger.info("Setting up the WebDriver");
		driver = webDriverFactory.getDriver("/");
	}

	@After
	public void tearDown() {
		logger.info("Quitting the WebDriver");
		if (driver != null) {
			webDriverFactory.quitDriver();
		}
	}

	public void logout() {
		// Wait for the menu to open
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement menuToggle = wait.until(ExpectedConditions.elementToBeClickable(By.id("menu-toggle")));

		// Click the menu toggle to open the menu
		menuToggle.click();

		// Wait for the "LogOut" option to be visible and clickable
		WebElement logoutOption = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='LogOut']")));

		// Click the "LogOut" option
		logoutOption.click();

		// Wait for the URL to change to the login page
		wait.until(ExpectedConditions.urlToBe(getExpectedRedirectUrl() + "login"));

		// Print the title of the login page
		System.out.println("Title: " + driver.getTitle());
	}

	@Given("the user is logged in and on the change password page")
	public void the_user_is_logged_in_and_on_the_change_password_page() {
		logger.info("Run feature 'the user is logged in and on the change password page' and redirect to login page.");
		driver.get(getExpectedRedirectUrl() + "login");
		System.out.println("Title: " + driver.getTitle());

		try {
			logger.info("Now try to login user with credential");
			WebElement usernameField = driver.findElement(By.name("username"));
			WebElement passwordField = driver.findElement(By.name("password"));

			usernameField.sendKeys(userName);
			passwordField.sendKeys(oldPassword);

			System.out.println("Username Field: " + usernameField);

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			WebElement Button = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginbtn")));

			Button.click();

			// Wait for an element that confirms the user is logged in, e.g., a user profile
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("task-container")));

			logger.info("Wait for redirect to change password page: " + getExpectedRedirectUrl() + "change-password");
			driver.get(getExpectedRedirectUrl() + "change-password");
			wait.until(ExpectedConditions.urlToBe(getExpectedRedirectUrl() + "change-password"));

			System.out.println("Title: " + driver.getTitle());
		} catch (Exception e) {
			System.err.println("An error has occurred on login: " + e.getMessage());
		}
	}

	@When("the user enters an incorrect current password")
	public void the_user_enters_an_incorrect_current_password(DataTable dataTable) {
		try {
			logger.info("the user enters an incorrect current password");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("oldPassword")));

			Map<String, String> creds = dataTable.asMaps(String.class, String.class).get(0);

			WebElement oldPasswordField = driver.findElement(By.name("oldPassword"));
			WebElement newPasswordField = driver.findElement(By.name("newPassword"));

			oldPasswordField.sendKeys(creds.get("currentPassword"));
			newPasswordField.sendKeys(creds.get("newPassword"));

			lastPassword = creds.get("newPassword");

			System.out.println("oldPassword Field: " + oldPasswordField);
			System.out.println("newPasswordField Field: " + newPasswordField);

		} catch (NoSuchElementException e) {
			System.err.println("Element not found: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("An error has occurred on login: " + e.getMessage());
		}
	}

	@When("clicks the change password {string} button")
	public void clicks_the_change_password_button(String string) {
		try {
			logger.info("clicks the change password " + string + " button");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			WebElement changePasswordButton = wait.until(ExpectedConditions.elementToBeClickable(By.id(string)));
			changePasswordButton.click();

		} catch (NoSuchElementException e) {
			System.err.println("Button not found: " + e.getMessage());
		} catch (TimeoutException e) {
			System.err.println("Change password button not clickable within timeout: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("An error occurred on change password button's click: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Then("the user should see an error message on change password {string}")
	public void the_user_should_see_an_error_message_on_change_password(String string) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

			WebElement errorMessageElement = wait
					.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.error-header p")));

			wait.until(ExpectedConditions.visibilityOf(errorMessageElement));

			String errorMessageText = errorMessageElement.getText();

			Assert.assertEquals(string, errorMessageText);

		} catch (NoSuchElementException e) {
			Assert.fail("Error message element not found: " + e.getMessage());
		} catch (Exception e) {
			Assert.fail("An error occurred while checking the error message: " + e.getMessage());
		}
	}

	@Then("the user should not be able to log in with the new password")
	public void the_user_should_not_be_able_to_log_in_with_the_new_password() {
		try {
			// Wait for the login page and attempt to log in with the incorrect new password
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			this.logout();

			// Wait for an element that confirms the user is logged in, e.g., a user profile
			logger.info("Wait for redirect to change password page: " + getExpectedRedirectUrl() + "login");
			driver.get(getExpectedRedirectUrl() + "login");

			wait.until(ExpectedConditions.urlToBe(getExpectedRedirectUrl() + "login"));

			System.out.println("Title: " + driver.getTitle());

			WebElement usernameField = driver.findElement(By.name("username"));
			WebElement passwordField = driver.findElement(By.name("password"));

			usernameField.sendKeys(userName);
			passwordField.sendKeys(lastPassword);
		} catch (NoSuchElementException e) {
			Assert.fail("Error message element not found: " + e.getMessage());
		} catch (Exception e) {
			Assert.fail("An error occurred while checking the error message: " + e.getMessage());
		}
	}

	@When("the user attempts to change password with empty required fields")
	public void the_user_attempts_to_change_password_with_empty_required_fields(DataTable dataTable) {
		try {
			logger.info("the user attempts to change password with empty required fields");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("oldPassword")));

			Map<String, String> creds = dataTable.asMaps(String.class, String.class).get(0);

			WebElement oldPasswordField = driver.findElement(By.name("oldPassword"));
			WebElement newPasswordField = driver.findElement(By.name("newPassword"));

			oldPasswordField.sendKeys(creds.get("currentPassword"));
			newPasswordField.sendKeys(creds.get("newPassword"));

			System.out.println("oldPassword Field: " + oldPasswordField);
			System.out.println("newPasswordField Field: " + newPasswordField);

		} catch (NoSuchElementException e) {
			System.err.println("Element not found: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("An error has occurred on login: " + e.getMessage());
		}
	}

	@When("the user enters valid current password, new password, and confirm new password")
	public void the_user_enters_valid_current_password_new_password_and_confirm_new_password(DataTable dataTable) {
		try {
			logger.info("the user enters valid current password, new password, and confirm new password");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("oldPassword")));

			Map<String, String> creds = dataTable.asMaps(String.class, String.class).get(0);

			WebElement oldPasswordField = driver.findElement(By.name("oldPassword"));
			WebElement newPasswordField = driver.findElement(By.name("newPassword"));

			oldPasswordField.sendKeys(creds.get("currentPassword"));
			newPasswordField.sendKeys(creds.get("newPassword"));

			newPassword = creds.get("newPassword");

			// Store the new password as the last updated password
			lastPassword = newPassword;

			System.out.println("oldPassword Field: " + oldPasswordField);
			System.out.println("newPasswordField Field: " + newPasswordField);

		} catch (NoSuchElementException e) {
			System.err.println("Element not found: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("An error has occurred on login: " + e.getMessage());
		}
	}

	@Then("the user should be able to log in with the new password")
	public void the_user_should_be_able_to_log_in_with_the_new_password() {
		try {
			// Wait for the menu to open
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			wait.until(ExpectedConditions.urlToBe(getExpectedRedirectUrl() + "login"));

			System.out.println("Title: " + driver.getTitle());

			logger.info("Now try to login user with credential");
			WebElement usernameField = driver.findElement(By.name("username"));
			WebElement passwordField = driver.findElement(By.name("password"));

			usernameField.sendKeys(userName);
			passwordField.sendKeys(newPassword);

			System.out.println("Username Field: " + usernameField);

			WebElement Button = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginbtn")));

			Button.click();

		} catch (NoSuchElementException e) {
			Assert.fail("Error message login with new password: " + e.getMessage());
		} catch (Exception e) {
			Assert.fail("An error occurred while checking login with new password: " + e.getMessage());
		}
	}

}
