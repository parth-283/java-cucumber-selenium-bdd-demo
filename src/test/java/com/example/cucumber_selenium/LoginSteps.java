package com.example.cucumber_selenium;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.example.cucumber_selenium.driver.WebDriverFactory;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.cdimascio.dotenv.Dotenv;

import java.time.Duration;
import org.junit.Assert;

public class LoginSteps {

	private final Dotenv dotenv = Dotenv.load();
	private WebDriver driver;
	private WebDriverFactory webDriverFactory;

	private String baseURL = dotenv.get("PROJECT_URL");

	public LoginSteps(WebDriverFactory webDriverFactory) {
		this.webDriverFactory = webDriverFactory;
	}

	@Given("the user is on the login page")
	public void the_user_is_on_the_login_page() {
		driver = webDriverFactory.getDriver("/login");
		System.out.println("Title: " + driver.getTitle());
	}

	@When("the user enters valid credentials")
	public void the_user_enters_valid_credentials(DataTable dataTable) {
		try {
			Map<String, String> creds = dataTable.asMaps(String.class, String.class).get(0);
			WebElement usernameField = driver.findElement(By.name("username"));
			WebElement passwordField = driver.findElement(By.name("password"));

			usernameField.sendKeys(creds.get("username"));
			passwordField.sendKeys(creds.get("password"));

			System.out.println("Username Field: " + usernameField);
		} catch (NoSuchElementException e) {
			System.err.println("Element not found: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("An error has occurred on login: " + e.getMessage());
		}
	}

	@When("clicks the {string} button")
	public void clicks_the_button(String string) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			WebElement Button = wait.until(ExpectedConditions.elementToBeClickable(By.id(string)));

			Button.click();
		} catch (NoSuchElementException e) {
			System.err.println("Button not found: " + e.getMessage());
		} catch (TimeoutException e) {
			System.err.println("Login button not clickable within timeout: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("An error occurred on login button's click: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Then("the user should be logged in")
	public void the_user_should_be_logged_in() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

			String expectedUrl = baseURL;

			wait.until(ExpectedConditions.urlToBe(expectedUrl)); // Wait for the URL to match

			String currentUrl = driver.getCurrentUrl(); // Get the current URL after the wait

			Assert.assertEquals("User was not redirected to the correct page.", expectedUrl, currentUrl);

			webDriverFactory.quitDriver();
		} catch (Exception e) {
			System.err.println("An error has occurred on login: " + e.getMessage());
		}
	}

	@When("the user enters incorrect password")
	public void the_user_enters_incorrect_password(DataTable dataTable) {
		try {
			Map<String, String> creds = dataTable.asMaps(String.class, String.class).get(0);
			WebElement usernameField = driver.findElement(By.name("username"));
			WebElement passwordField = driver.findElement(By.name("password"));

			usernameField.sendKeys(creds.get("username"));
			passwordField.sendKeys(creds.get("password"));

			System.out.println("Username Field: " + usernameField);
		} catch (NoSuchElementException e) {
			System.err.println("Element not found: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("An error has occurred on login: " + e.getMessage());
		}
	}

	@Then("the user should see an error message {string}")
	public void the_user_should_see_an_error_message(String expectedErrorMessage) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

			WebElement errorMessageElement = driver.findElement(By.cssSelector("div.error-header p"));

			wait.until(ExpectedConditions.visibilityOf(errorMessageElement)); // Corrected line

			String errorMessageText = errorMessageElement.getText();

			Assert.assertEquals(expectedErrorMessage, errorMessageText);

		} catch (NoSuchElementException e) {
			Assert.fail("Error message element not found: " + e.getMessage());
		} catch (Exception e) {
			Assert.fail("An error occurred while checking the error message: " + e.getMessage());
		}
	}

	@Then("the user should remain on the login page {string}")
	public void the_user_should_remain_on_the_login_page(String URL) {
		try {
			String currentUrl = driver.getCurrentUrl();

			String expectedUrl = baseURL + URL;

			Assert.assertEquals("User was not on the login page.", expectedUrl, currentUrl);

			webDriverFactory.quitDriver();
		} catch (Exception e) {
			Assert.fail("An error occurred while checking the remain page: " + e.getMessage());
		}
	}

	@When("the user enters incorrect username")
	public void the_user_enters_incorrect_username(DataTable dataTable) {
		try {
			Map<String, String> creds = dataTable.asMaps(String.class, String.class).get(0);
			WebElement usernameField = driver.findElement(By.name("username"));
			WebElement passwordField = driver.findElement(By.name("password"));

			usernameField.sendKeys(creds.get("username"));
			passwordField.sendKeys(creds.get("password"));

			System.out.println("Username Field: " + usernameField);

		} catch (NoSuchElementException e) {
			System.err.println("Element not found: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("An error has occurred on login: " + e.getMessage());
		}
	}

	@When("the user leaves username and password blank")
	public void the_user_leaves_username_and_password_blank(DataTable dataTable) {
		try {
			Map<String, String> creds = dataTable.asMaps(String.class, String.class).get(0);
			WebElement usernameField = driver.findElement(By.name("username"));
			WebElement passwordField = driver.findElement(By.name("password"));

			usernameField.sendKeys(creds.get("username"));
			passwordField.sendKeys(creds.get("password"));

			System.out.println("Username Field: " + usernameField);

		} catch (NoSuchElementException e) {
			System.err.println("Element not found: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("An error has occurred on login: " + e.getMessage());
		}
	}

	@When("the user enters a password")
	public void the_user_enters_a_password(DataTable dataTable) {
		try {
			Map<String, String> creds = dataTable.asMaps(String.class, String.class).get(0);
			WebElement usernameField = driver.findElement(By.name("username"));
			WebElement passwordField = driver.findElement(By.name("password"));

			usernameField.sendKeys(creds.get("username"));
			passwordField.sendKeys(creds.get("password"));

			System.out.println("Username Field: " + usernameField);

		} catch (NoSuchElementException e) {
			System.err.println("Element not found: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("An error has occurred on login: " + e.getMessage());
		}
	}

	@When("clicks the {string} toggle")
	public void clicks_the_toggle(String eyeIconId) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			WebElement eyeIcon = wait.until(ExpectedConditions.elementToBeClickable(By.id(eyeIconId)));

			eyeIcon.click();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.err.println("Eye icon not found: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("An error has occurred on login: " + e.getMessage());
		}
	}

	@Then("the password should be visible")
	public void the_password_should_be_visible() {
		try {
			WebElement passwordField = driver.findElement(By.name("password"));

			String passwordType = passwordField.getAttribute("type");

			Assert.assertEquals("text", passwordType);
		} catch (Exception e) {
			System.err.println("An error has occurred: " + e.getMessage());
		}
	}

	@When("the user clicks the {string} toggle")
	public void the_user_clicks_the_toggle(String eyeIconId) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			WebElement eyeIcon = wait.until(ExpectedConditions.elementToBeClickable(By.id(eyeIconId)));

			eyeIcon.click();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.err.println("Eye icon not found: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("An error has occurred: " + e.getMessage());
		}
	}

	@Then("the password should be hidden")
	public void the_password_should_be_hidden() {
		try {
			WebElement passwordField = driver.findElement(By.name("password"));

			String passwordType = passwordField.getAttribute("type");

			Assert.assertEquals("password", passwordType);
			webDriverFactory.quitDriver();
		} catch (Exception e) {
			System.err.println("An error has occurred: " + e.getMessage());
		}
	}

}
