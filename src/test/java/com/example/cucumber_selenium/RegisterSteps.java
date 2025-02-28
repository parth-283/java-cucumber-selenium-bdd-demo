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

import com.example.cucumber_selenium.driver.WebDriverFactory;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RegisterSteps {

	private WebDriver driver;
	private WebDriverFactory webDriverFactory;

	@Given("the user is on the registration page")
	public void the_user_is_on_the_registration_page() {
		driver = webDriverFactory.getDriver();

		driver.get("http://localhost:3000/register");
		System.out.println("Title: " + driver.getTitle());
	}

	@When("the user enters valid registration details")
	public void the_user_enters_valid_registration_details(DataTable dataTable) {
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
			System.err.println("An error has occurred on register: " + e.getMessage());
		}
	}

	@When("clicks the register {string} button")
	public void clicks_register_the_button(String string) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			WebElement Button = wait.until(ExpectedConditions.elementToBeClickable(By.id(string)));

			Button.click();
		} catch (NoSuchElementException e) {
			System.err.println("Button not found: " + e.getMessage());
		} catch (TimeoutException e) {
			System.err.println("Register button not clickable within timeout: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("An error occurred on register button's click: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Then("the user should be auto logged in")
	public void the_user_should_be_logged_in() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

			String expectedUrl = getExpectedRedirectUrl();

			wait.until(ExpectedConditions.urlToBe(expectedUrl)); // Wait for the URL to match

			String currentUrl = driver.getCurrentUrl(); // Get the current URL after the wait

			Assert.assertEquals("User was not redirected to the correct page.", expectedUrl, currentUrl);

			driver.quit();
		} catch (Exception e) {
			System.err.println("An error has occurred on login: " + e.getMessage());
		}
	}

	private String getExpectedRedirectUrl() {
		return "http://localhost:3000/";
	}

	@When("the user enters registration details with an existing username")
	public void the_user_enters_registration_details_with_an_existing_username(DataTable dataTable) {
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
			System.err.println("An error has occurred on register: " + e.getMessage());
		}
	}

	@Then("the user should remain on the registration page")
	public void the_user_should_remain_on_the_registration_page() {
		try {
			String currentUrl = driver.getCurrentUrl();

			String expectedUrl = "http://localhost:3000/register";

			Assert.assertEquals("User was not on the login page.", expectedUrl, currentUrl);

		} catch (Exception e) {
			Assert.fail("An error occurred while checking the remain page: " + e.getMessage());
		}
	}

	@Then("an error message {string} should be displayed")
	public void an_error_message_should_be_displayed(String expectedErrorMessage) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

			WebElement errorMessageElement = driver.findElement(By.cssSelector("div.error-header p"));

			wait.until(ExpectedConditions.visibilityOf(errorMessageElement));

			String errorMessageText = errorMessageElement.getText();

			Assert.assertEquals(expectedErrorMessage, errorMessageText);

			driver.quit();
		} catch (NoSuchElementException e) {
			Assert.fail("Error message element not found: " + e.getMessage());
		} catch (Exception e) {
			Assert.fail("An error occurred while checking the error message: " + e.getMessage());
		}
	}

	@When("the user attempts to register with empty required fields")
	public void the_user_attempts_to_register_with_empty_required_fields(DataTable dataTable) {
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
			System.err.println("An error has occurred on register: " + e.getMessage());
		}
	}

	@When("the user enters a password on the registration page")
	public void the_user_enters_a_password_on_the_registration_page(DataTable dataTable) {
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
			System.err.println("An error has occurred on register: " + e.getMessage());
		}
	}

	@When("clicks the register {string} toggle")
	public void clicks_the_register_toggle(String eyeIconId) {
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

	@Then("the password should be visible on the registration page")
	public void the_password_should_be_visible_on_the_registration_page() {
		try {
			WebElement passwordField = driver.findElement(By.name("password"));

			String passwordType = passwordField.getAttribute("type");

			Assert.assertEquals("text", passwordType);
		} catch (Exception e) {
			System.err.println("An error has occurred: " + e.getMessage());
		}
	}

	@Then("the password should be hidden on the registration page")
	public void the_password_should_be_hidden_on_the_registration_page() {
		try {
			WebElement passwordField = driver.findElement(By.name("password"));

			String passwordType = passwordField.getAttribute("type");

			Assert.assertEquals("password", passwordType);
			driver.quit();
		} catch (Exception e) {
			System.err.println("An error has occurred: " + e.getMessage());
		}
	}

}
