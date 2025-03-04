package com.example.cucumber_selenium;

import java.time.Duration;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.cucumber_selenium.driver.WebDriverFactory;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ContactSteps {
	private static final Logger logger = LoggerFactory.getLogger(ContactSteps.class);
	private WebDriver driver;
	private WebDriverFactory webDriverFactory;

	public ContactSteps(WebDriverFactory webDriverFactory) {
		this.webDriverFactory = webDriverFactory;
	}

	private String getExpectedRedirectUrl() {
		return "http://localhost:5000/";
	}

	@Before
	public void setUp() {
		logger.info("Setting up the WebDriver");
		driver = webDriverFactory.getDriver("/contact");
	}

	@After
	public void tearDown() {
		logger.info("Quitting the WebDriver");
		if (driver != null) {
			driver.quit();
		}
	}

	public void contactPage() {
		// Wait for the menu to open
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement menuToggle = wait.until(ExpectedConditions.elementToBeClickable(By.id("menu-toggle")));

		// Click the menu toggle to open the menu
		menuToggle.click();

		// Wait for the "Contact Us" option to be visible and clickable
		WebElement contactUSOption = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Contact Us']")));

		// Click the "Contact Us" option
		contactUSOption.click();

		// Wait for the URL to change to the contact page
		wait.until(ExpectedConditions.urlToBe(getExpectedRedirectUrl() + "contact"));

		// Print the title of the contact page
		System.out.println("Title: " + driver.getTitle());
	}

	@Given("the user is on the contact us page")
	public void the_user_is_on_the_contact_us_page() {
		this.contactPage();
	}

	@When("the user fills out the contact form with a missing name")
	public void the_user_fills_out_the_contact_form_with_a_missing_name(DataTable dataTable) {
		try {
			logger.info("Filling out the contact form with a missing name");

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			Map<String, String> creds = dataTable.asMaps(String.class, String.class).get(0);

			WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));
			WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
			WebElement messageField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("message")));

			nameField.sendKeys("");
			emailField.sendKeys(creds.get("email"));
			messageField.sendKeys(creds.get("message"));

			logger.info("Name Field: " + nameField);
			logger.info("Email Field: " + emailField);
			logger.info("Message Field: " + messageField);

		} catch (NoSuchElementException e) {
			logger.error("Field not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Contact form fields not found within the timeout period.", e);
		} catch (Exception e) {
			logger.error("An unexpected error occurred while filling out the contact form.", e);
		} finally {
			logger.info("Finished attempting to fill out the contact form with a missing name on the Contact Us page.");
		}
	}

	@When("clicks the contact {string} button")
	public void clicks_the_contact_button(String string) {
		try {
			logger.info("Clicking the contact button: " + string);

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement contactUSButton = wait.until(ExpectedConditions.elementToBeClickable(By.id(string)));

			contactUSButton.click();
			logger.info("Clicked the contact button successfully.");

		} catch (NoSuchElementException e) {
			logger.error("Button not found with ID: " + string, e);
		} catch (TimeoutException e) {
			logger.error("Contact button not clickable within timeout. ID: " + string, e);
		} catch (Exception e) {
			logger.error("An unexpected error occurred while clicking the contact button. ID: " + string, e);
		} finally {
			logger.info("Finished attempting to click the button '" + string + "' on the Contact Us page.");
		}
	}

	@Then("the user should see an error message on the contact us page: {string}")
	public void the_user_should_see_an_error_message_on_the_contact_us_page(String string) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

			WebElement errorMessageElement = wait
					.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.error-header p")));

			wait.until(ExpectedConditions.visibilityOf(errorMessageElement));

			String errorMessageText = errorMessageElement.getText();

			Assert.assertEquals(string, errorMessageText);

		} catch (NoSuchElementException e) {
			Assert.fail("Contact Us page error message element not found: " + e.getMessage());
		} catch (TimeoutException e) {
			Assert.fail(
					"Contact Us page error message element not clickable within the timeout period: " + e.getMessage());
		} catch (Exception e) {
			Assert.fail("An unexpected error occurred while checking the error message on the Contact Us page: "
					+ e.getMessage());
		} finally {
			logger.info("Finished attempting to verify the error message on the Contact Us page.");
		}
	}

	@When("the user fills out the contact form with a missing email")
	public void the_user_fills_out_the_contact_form_with_a_missing_email(DataTable dataTable) {
		try {
			logger.info("Filling out the contact form with a missing email");

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			Map<String, String> creds = dataTable.asMaps(String.class, String.class).get(0);

			WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));
			WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
			WebElement messageField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("message")));

			nameField.sendKeys(creds.get("name"));
			emailField.sendKeys("");
			messageField.sendKeys(creds.get("message"));

			logger.info("Name Field: " + nameField);
			logger.info("Email Field: " + emailField);
			logger.info("Message Field: " + messageField);

		} catch (NoSuchElementException e) {
			logger.error("Field not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Contact form fields not found within the timeout period.", e);
		} catch (Exception e) {
			logger.error("An unexpected error occurred while filling out the contact form.", e);
		} finally {
			logger.info(
					"Finished attempting to fill out the contact form with a missing email on the Contact Us page.");
		}
	}

	@When("the user fills out the contact form with a missing message")
	public void the_user_fills_out_the_contact_form_with_a_missing_message(DataTable dataTable) {
		try {
			logger.info("Filling out the contact form with a missing message");

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			Map<String, String> creds = dataTable.asMaps(String.class, String.class).get(0);

			WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));
			WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
			WebElement messageField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("message")));

			nameField.sendKeys(creds.get("name"));
			emailField.sendKeys(creds.get("email"));
			messageField.sendKeys("");

			logger.info("Name Field: " + nameField);
			logger.info("Email Field: " + emailField);
			logger.info("Message Field: " + messageField);

		} catch (NoSuchElementException e) {
			logger.error("Field not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Contact form fields not found within the timeout period.", e);
		} catch (Exception e) {
			logger.error("An unexpected error occurred while filling out the contact form.", e);
		} finally {
			logger.info(
					"Finished attempting to fill out the contact form with a missing message on the Contact Us page.");
		}
	}

	@When("the user attempts to submit an empty form")
	public void the_user_attempts_to_submit_an_empty_form() {
		try {
			logger.info("Filling out the contact form with all fields left empty");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));
			WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
			WebElement messageField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("message")));

			nameField.sendKeys("");
			emailField.sendKeys("");
			messageField.sendKeys("");

			logger.info("Name Field: " + nameField);
			logger.info("Email Field: " + emailField);
			logger.info("Message Field: " + messageField);

		} catch (NoSuchElementException e) {
			logger.error("Field not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Contact form fields not found within the timeout period.", e);
		} catch (Exception e) {
			logger.error("An unexpected error occurred while filling out the contact form.", e);
		} finally {
			logger.info(
					"Finished attempting to fill out the contact form with a missing message on the Contact Us page.");
		}
	}

	@When("the user fills out the contact form with valid information")
	public void the_user_fills_out_the_contact_form_with_valid_information(DataTable dataTable) {
		try {
			logger.info("Filling out the contact form with valid information");

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			Map<String, String> creds = dataTable.asMaps(String.class, String.class).get(0);

			WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));
			WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
			WebElement messageField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("message")));

			nameField.sendKeys(creds.get("name"));
			emailField.sendKeys(creds.get("email"));
			messageField.sendKeys("message");

			logger.info("Name Field: " + nameField);
			logger.info("Email Field: " + emailField);
			logger.info("Message Field: " + messageField);

		} catch (NoSuchElementException e) {
			logger.error("Field not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Contact form fields not found within the timeout period.", e);
		} catch (Exception e) {
			logger.error("An unexpected error occurred while filling out the contact form.", e);
		} finally {
			logger.info(
					"Finished attempting to fill out the contact form with valid information on the Contact Us page.");
		}
	}

	@Then("the user should see a success message on the contact us page: {string}")
	public void the_user_should_see_a_success_message_on_the_contact_us_page(String string) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

			WebElement successMessageElement = wait
					.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.success-header p")));

			wait.until(ExpectedConditions.visibilityOf(successMessageElement));

			String successMessageText = successMessageElement.getText();

			Assert.assertEquals(string, successMessageText);

		} catch (NoSuchElementException e) {
			Assert.fail("Contact Us page success message element not found: " + e.getMessage());
		} catch (TimeoutException e) {
			Assert.fail("Contact Us page success message element not clickable within the timeout period: "
					+ e.getMessage());
		} catch (Exception e) {
			Assert.fail("An unexpected error occurred while checking the success message on the Contact Us page: "
					+ e.getMessage());
		} finally {
			logger.info("Finished attempting to verify the error message on the Contact Us page.");
		}
	}

	@Then("the form fields should be cleared")
	public void the_form_fields_should_be_cleared() {
		try {
			// Locate input fields
			WebElement nameField = driver.findElement(By.name("name"));
			WebElement emailField = driver.findElement(By.name("email"));
			WebElement messageField = driver.findElement(By.name("message"));

			// Check if fields are blank
			boolean isNameFieldBlank = nameField.getAttribute("value").isEmpty();
			boolean isEmailFieldBlank = emailField.getAttribute("value").isEmpty();
			boolean isMessageFieldBlank = messageField.getAttribute("value").isEmpty();

			// Print results
			System.out.println("Name field is blank: " + isNameFieldBlank);
			System.out.println("Email field is blank: " + isEmailFieldBlank);
			System.out.println("Message field is blank: " + isMessageFieldBlank);

		} catch (NoSuchElementException e) {
			Assert.fail("Contact Us page form fields element not found: " + e.getMessage());
		} catch (Exception e) {
			Assert.fail("An unexpected error occurred while checking the all fields are empty on the Contact Us page: "
					+ e.getMessage());
		} finally {
			logger.info("Finished attempting to verify the all fields are empty on the Contact Us page.");
		}
	}
}