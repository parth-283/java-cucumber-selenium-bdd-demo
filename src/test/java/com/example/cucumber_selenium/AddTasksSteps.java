package com.example.cucumber_selenium;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
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

public class AddTasksSteps {
	private static final Logger logger = LoggerFactory.getLogger(AddTasksSteps.class);
	private final Dotenv dotenv = Dotenv.load();
	private WebDriver driver;
	private WebDriverFactory webDriverFactory;

	private String userName = dotenv.get("DB_USERNAME");
	private String Password = dotenv.get("DB_Password");
	private String baseURL = dotenv.get("PROJECT_URL");

	public AddTasksSteps(WebDriverFactory webDriverFactory) {
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

	@Given("I am on the To-Do list page")
	public void i_am_on_the_to_do_list_page() {
		logger.info("Running the feature 'I am on the To-Do list page' and redirecting to the login page.");
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

			// Wait for an element that confirms the user is logged in, e.g., a user profile
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("task-container")));

		} catch (NoSuchElementException e) {
			logger.error("Field not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Login form fields not found within the timeout period.", e);
		} catch (Exception e) {
			System.err.println("An error occurred during login while adding tasks: " + e.getMessage());
		} finally {
			logger.info("Finished attempting to log in and ensuring I am on the home page.");
		}
	}

	@When("I enter {string} in the task input box")
	public void i_enter_in_the_task_input_box(String string) {
		try {
			logger.info("Running the feature 'I enter {string} in the task input box'.");
			System.out.println("Title: " + driver.getTitle());

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

			logger.info("Attempting to fill the To-Do list.");
			WebElement taskField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("task")));

			taskField.sendKeys(string);

		} catch (NoSuchElementException e) {
			logger.error("Field not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Task input field not found within the timeout period.", e);
		} catch (Exception e) {
			System.err.println("An error occurred while adding a task to the To-Do list: " + e.getMessage());
		} finally {
			logger.info("Finished attempting to fill a task on the To-Do list.");
		}
	}

	@When("I click the add {string} button")
	public void i_click_the_add_button(String string) {
		try {
			logger.info("Running the feature 'I click the add {string} button'.");

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

			logger.info("Attempting to click the 'Add' button.");
			WebElement addButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(string)));

			addButton.click();

		} catch (NoSuchElementException e) {
			logger.error("Button not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Add button not found within the timeout period.", e);
		} catch (Exception e) {
			System.err.println("An error occurred while clicking the 'Add' button: " + e.getMessage());
		} finally {
			logger.info("Finished attempting to click the 'Add' button on the To-Do list.");
		}
	}

	@Then("the task {string} should appear in the task list")
	public void the_task_should_appear_in_the_task_list(String string) {
		try {
			logger.info("\"Runing the feature 'the task {string} should appear in the task list'.");

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

			logger.info("Attempting to find the last added task.");
			WebElement todoList = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"react-target\"]/div/div[1]/div/ul/li/span")));

			// Check if the string value exists in the todo list
			if (todoList.getText().equals(string)) {
				logger.info("Task '" + string + "' successfully found in the task list.");
				System.out.println("Task found: " + string);
			} else {
				logger.error("Task '" + string + "' was not found in the task list.");
				throw new AssertionError("The task '" + string + "' does not exist in the task list.");
			}
		} catch (NoSuchElementException e) {
			logger.error("Field not found: ", e);
		} catch (TimeoutException e) {
			logger.error("The task element was not found within the timeout period.", e);
		} catch (Exception e) {
			System.err.println("An error has occurred on todo list for button click : " + e.getMessage());
		} finally {
			logger.info("Finished attempting to click button for add task on the todo list.");
		}
	}

}
