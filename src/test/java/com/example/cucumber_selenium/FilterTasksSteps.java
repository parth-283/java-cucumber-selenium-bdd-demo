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
import com.github.dockerjava.api.exception.ConflictException;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.cdimascio.dotenv.Dotenv;

public class FilterTasksSteps {

	private static final Logger logger = LoggerFactory.getLogger(FilterTasksSteps.class);
	private final Dotenv dotenv = Dotenv.load();
	private WebDriver driver;
	private WebDriverFactory webDriverFactory;

	private String userName = dotenv.get("DB_USERNAME");
	private String Password = dotenv.get("DB_PASSWORD");
	private String baseURL = dotenv.get("PROJECT_URL");
	private static final int DEFAULT_WAIT_TIME = 5;

	public FilterTasksSteps(WebDriverFactory webDriverFactory) {
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

	private void loginMethod() {
		logger.info("Running the login method.");
		driver.get(baseURL + "/login");
		System.out.println("Title: " + driver.getTitle());

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));

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
			System.err.println("An error occurred during login: " + e.getMessage());
		} finally {
			logger.info("Finished attempting to log in.");
		}
	}

	@Given("I have both completed and incomplete tasks")
	public void i_have_both_completed_and_incomplete_tasks() {
		try {
			this.loginMethod();

			int totalCheckedTasks = 0;

			logger.info("Running the feature 'I have both completed and incomplete tasks'");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));

			// Wait for the <ul> element to become visible
			WebElement tasksList = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"react-target\"]/div/div[1]/div/ul")));

			// Find all checked checkboxes within the list
			List<WebElement> tasks = tasksList.findElements(By.xpath(".//li/input[@type='checkbox']"));

			for (WebElement task : tasks) {
				// Get the 'checked' attribute of the checkbox
				String value = task.getAttribute("checked");
				System.out.println("Checkbox Value: " + value);

				if ("true".equals(value)) {
					totalCheckedTasks++;
				}
			}

			if (totalCheckedTasks == 0) {
				System.out.println("No completed tasks found in the task list.");
				throw new NotFoundException("No completed tasks found in the task list.");
			}

		} catch (NoSuchElementException e) {
			logger.error("Field not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Completed tasks not found within the timeout period.", e);
		} catch (Exception e) {
			System.err.println("An error occurred while finding completed tasks: " + e.getMessage());
		} finally {
			logger.info("Finished checking if both completed and incomplete tasks exist in the task list.");
		}
	}

	@When("I click the filter {string} button")
	public void i_click_the_filter_button(String string) {
		try {
			logger.info("Running the feature 'I click the filter {string} button'.");

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));

			logger.info("Attempting to click the " + string + " button.");
			WebElement filterButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='" + string + "']")));

			filterButton.click();

		} catch (NoSuchElementException e) {
			logger.error("Button not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Add button not found within the timeout period.", e);
		} catch (Exception e) {
			System.err.println("An error occurred while clicking the " + string + " button: " + e.getMessage());
		} finally {
			logger.info("Finished attempting to click the %s button on the To-Do list.", string);
		}
	}

	@Then("only incomplete tasks should be visible in the task list")
	public void only_incomplete_tasks_should_be_visible_in_the_task_list() {
		try {
			this.loginMethod();

			int totalCheckedTasks = 0;

			logger.info("Running the feature 'only incomplete tasks should be visible in the task list'");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));

			// Wait for the <ul> element to become visible
			WebElement tasksList = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"react-target\"]/div/div[1]/div/ul")));

			// Find all checked checkboxes within the list
			List<WebElement> tasks = tasksList.findElements(By.xpath(".//li/input[@type='checkbox']"));

			for (WebElement task : tasks) {
				// Get the 'checked' attribute of the checkboxes
				String value = task.getAttribute("checked");
				System.out.println("Checkbox Value: " + value);

				if ("true".equals(value)) {
					totalCheckedTasks++;
				}
			}

			if (totalCheckedTasks > 0) {
				System.out.println("Completed tasks found in the task list.");
				throw new ConflictException("Completed tasks found in the task list.");
			}

		} catch (NoSuchElementException e) {
			logger.error("Field not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Incomplete tasks not found within the timeout period.", e);
		} catch (Exception e) {
			System.err.println("An error occurred while finding completed tasks: " + e.getMessage());
		} finally {
			logger.info("Finished getting incomplete tasks into the task list.");
		}
	}

	@Then("all tasks, completed and incomplete, should be visible")
	public void all_tasks_completed_and_incomplete_should_be_visible() {
		try {
			this.loginMethod();

			int totalCheckedTasks = 0;

			logger.info("Running the feature 'all tasks, completed and incomplete, should be visible'");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));

			// Wait for the <ul> element to become visible
			WebElement tasksList = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"react-target\"]/div/div[1]/div/ul")));

			// Find all checked checkboxes within the list
			List<WebElement> tasks = tasksList.findElements(By.xpath(".//li/input[@type='checkbox']"));

			for (WebElement task : tasks) {
				// Get the 'checked' attribute of the checkboxes
				String value = task.getAttribute("checked");
				System.out.println("Checkbox Value: " + value);

				if ("true".equals(value)) {
					totalCheckedTasks++;
				}
			}

			if (totalCheckedTasks > 0 && totalCheckedTasks >= tasks.size()) {
				System.out.println("Completed tasks not found in the task list.");
				throw new NoSuchElementException("Completed tasks not found in the task list.");
			}

		} catch (NoSuchElementException e) {
			logger.error("Field not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Incomplete tasks not found within the timeout period.", e);
		} catch (Exception e) {
			System.err.println("An error occurred while finding completed tasks: " + e.getMessage());
		} finally {
			logger.info("Finished getting incomplete tasks into the task list.");
		}
	}
}
