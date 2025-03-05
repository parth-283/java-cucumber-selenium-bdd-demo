package com.example.cucumber_selenium;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.cucumber_selenium.driver.WebDriverFactory;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.cdimascio.dotenv.Dotenv;

public class UserAuthSteps {

	private static final Logger logger = LoggerFactory.getLogger(UserAuthSteps.class);
	private final Dotenv dotenv = Dotenv.load();
	private WebDriver driver;
	private WebDriverFactory webDriverFactory;

	private String userName = dotenv.get("DB_USERNAME");
	private String Password = dotenv.get("DB_Password");
	private String baseURL = dotenv.get("PROJECT_URL");

	public UserAuthSteps(WebDriverFactory webDriverFactory) {
		this.webDriverFactory = webDriverFactory;
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
			driver.quit();
		}
	}

	private boolean isLoginPageDisplayed() {
		try {
			WebElement usernameField = driver.findElement(By.name("username"));

			return usernameField.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@Given("I am logged in as a user")
	public void i_am_logged_in_as_a_user() {
		logger.info("Running the feature 'I am logged in as a user' and redirecting to the login page.");
		driver.get(baseURL + "/login");
		System.out.println("Title: " + driver.getTitle());

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			logger.info("Attempting to log in with user credentials.");
			WebElement usernameField = driver.findElement(By.name("username"));
			WebElement passwordField = driver.findElement(By.name("password"));

			usernameField.sendKeys(userName);
			passwordField.sendKeys(Password);

			System.out.println("Username Field: " + usernameField);

			WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginbtn")));

			loginButton.click();

		} catch (NoSuchElementException e) {
			logger.error("Field not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Login form fields not found within the timeout period.", e);
		} catch (Exception e) {
			System.err.println("An error occurred during login while adding tasks: " + e.getMessage());
		} finally {
			logger.info("Finished attempting to log in.");
		}
	}

	@When("I visit the To-Do list page")
	public void i_visit_the_to_do_list_page() {
		logger.info("Running the feature 'I visit the To-Do list page'");
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

			// Wait for an element that confirms the user is logged in (e.g., task
			// container)
			WebElement taskContainer = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.className("task-container")));

			// Check if the user has successfully accessed the home page
			if (taskContainer != null) {
				logger.info("Successfully visited the home page.");
				System.out.println("Home page visited. Element located: " + taskContainer);
			} else {
				logger.error("Home page elements were not found.");
				throw new AssertionError("Home page elements were not found.");
			}

		} catch (NoSuchElementException e) {
			logger.error("Element not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Task container not found within the timeout period.", e);
		} catch (Exception e) {
			logger.error("An error occurred while visiting the home page: ", e);
			throw e;
		} finally {
			logger.info("Finished attempting to visit the home page.");
		}
	}

	@Then("I should see my tasks")
	public void i_should_see_my_tasks() {
		logger.info("Running the feature 'I should see my tasks'");
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

			WebElement tasksList = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"react-target\"]/div/div[1]/div/ul")));

			// Find all <span> elements under the <ul> element (relative XPath)
			List<WebElement> tasks = tasksList.findElements(By.xpath(".//li/span"));

			if (tasks.isEmpty()) {
				System.out.println("No tasks found in the task list.");
				throw new NotFoundException("No tasks found in the task list.");
			}

			for (WebElement task : tasks) {
				System.out.println(task.getText());
			}

		} catch (NoSuchElementException e) {
			logger.error("Element not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Tasks not found within the timeout period.", e);
		} catch (Exception e) {
			logger.error("An error occurred while getting list of tasks: ", e);
			throw e;
		} finally {
			logger.info("Finished getting list of tasks.");
		}
	}

	@Given("I am not logged in")
	public void i_am_not_logged_in() {
		logger.info("Running the feature 'I am not logged in'...");
		driver.get(baseURL + "/");
		System.out.println("Page Title: " + driver.getTitle());

		try {
			if (this.isLoginPageDisplayed()) {
				logger.info("Confirmed: User is not logged in.");
				System.out.println("User is on the login page.");
			} else {
				logger.error("Login page elements were not found.");
				throw new AssertionError("Login page elements were not found.");
			}

		} catch (NoSuchElementException e) {
			logger.error("Element not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Login elements were not found within the timeout period.", e);
		} catch (Exception e) {
			logger.error("An unexpected error occurred while verifying the 'not logged in' state: ", e);
			throw e;
		} finally {
			logger.info("Finished verifying the 'not logged in' state.");
		}
	}

	@Then("I should not see any tasks")
	public void i_should_not_see_any_tasks() {
		logger.info("Running the feature 'I should not see any tasks'...");
		try {
			if (this.isLoginPageDisplayed()) {
				logger.info("Confirmed: User is not logged in and is on the login page.");
				System.out.println("User is on the login page. No tasks are visible.");
			} else {
				logger.error("Login page elements were not found.");
				throw new AssertionError("Expected login page elements, but they were not found.");
			}

		} catch (NoSuchElementException e) {
			logger.error("Login field not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Login page elements were not found within the timeout period.", e);
		} catch (Exception e) {
			logger.error("An unexpected error occurred while verifying no tasks are visible: ", e);
			throw e;
		} finally {
			logger.info("Finished verifying the state where no tasks should be visible.");
		}
	}

	@Then("I should be prompted to log in")
	public void i_should_be_prompted_to_log_in() {
		logger.info("Running the feature 'I should be prompted to log in'...");

		try {
			if (this.isLoginPageDisplayed()) {
				logger.info("User is correctly prompted to log in.");
				System.out.println("Login page is displayed as expected.");
			} else {
				logger.error("Login page elements were not displayed as expected.");
				throw new AssertionError("User was not correctly prompted to log in.");
			}

		} catch (NoSuchElementException e) {
			logger.error("One or more login page elements were not found: ", e);
			throw new AssertionError("One or more login page elements were missing.");
		} catch (TimeoutException e) {
			logger.error("Login page elements were not found within the timeout period.", e);
			throw new AssertionError("Login page did not load in time.");
		} catch (Exception e) {
			logger.error("An unexpected error occurred while verifying login prompt: ", e);
			throw e;
		} finally {
			logger.info("Finished verifying the login prompt.");
		}
	}

}
