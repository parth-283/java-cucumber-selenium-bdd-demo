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
import pages.LoginPage;

public class ToggleCompletionSteps {

	private static final Logger logger = LoggerFactory.getLogger(ToggleCompletionSteps.class);
	private final Dotenv dotenv = Dotenv.load();
	private WebDriver driver;
	private WebDriverFactory webDriverFactory;

	private String userName = dotenv.get("DB_USERNAME");
	private String password = dotenv.get("DB_PASSWORD");
	private String baseURL = dotenv.get("PROJECT_URL");
	private static final int DEFAULT_WAIT_TIME = 5;

	public ToggleCompletionSteps(WebDriverFactory webDriverFactory) {
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
		LoginPage loginPage = new LoginPage(driver, DEFAULT_WAIT_TIME);
		loginPage.login(baseURL, userName, password);

		logger.info("Login method executed successfully.");
	}

	@Given("I have a task named {string} that is not completed")
	public void i_have_a_task_named_that_is_not_completed(String string) {
		try {
			this.loginMethod();

			boolean taskExists = false;

			System.out.println("Title: " + driver.getTitle());

			logger.info("Starting scenario: I have a task named '{}' that is not completed", string);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));

			// Wait for tasks list to be visible and fetch its items
			List<WebElement> tasksList = driver.findElements(By.xpath("//ul[@class='tasks']/li"));

			if (tasksList.isEmpty()) {
				System.out.println("No tasks found!");
				// throw new NotFoundException("No tasks found in the task list.");
			}

			// Iterate through tasks to find a matching one
			for (WebElement task : tasksList) {
				// Compare the task text with the input string
				logger.info("Comparing task text '{}' with the provided input '{}'", task.getText().trim(), string);

				if (string.trim().equalsIgnoreCase(task.getText().trim())) { // Case-insensitive comparison
					logger.info("Task '{}' already exists in the list.", task.getText().trim());
					taskExists = true;
					break;
				}
			}

			if (!taskExists) {
				logger.info("Attempting to add the task to the To-Do list.");
				WebElement taskField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("task")));

				taskField.sendKeys(string);

				logger.info("Attempting to click the 'Add Task' button.");
				WebElement addButton = wait
						.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Add Task']")));

				addButton.click();
			}

		} catch (NoSuchElementException e) {
			logger.error("Fields not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Fields not found within the timeout period.", e); // Clarified message
		} catch (Exception e) {
			logger.error("An error occurred: {}", e.getMessage(), e); // Enhanced logging with logger.error
		} finally {
			logger.info("Finished executing the step 'I have a task named '{}' that is not completed'", string);
		}
	}

	@When("I check the box next to the task name {}")
	public void i_check_the_box_next_to_the_task_name(String string) {
		try {
			logger.info("Starting scenario: I check the box next to the task name {}", string);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));

			// Locate the checkbox
			WebElement checkInput = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@type,'checkbox')]")));

			// Check if the checkbox is selected
			boolean isChecked = checkInput.isSelected();
			logger.info("Checkbox checked state: {}", isChecked);

			// Print the checkbox checked state
			System.out.println("Is checkbox checked? " + isChecked);

			if (!isChecked) {
				// Click the checkbox
				checkInput.click();
			}

		} catch (NoSuchElementException e) {
			logger.error("Fields not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Fields not found within the timeout period.", e); // Clarified message
		} catch (Exception e) {
			logger.error("An error occurred: {}", e.getMessage(), e); // Enhanced logging with logger.error
		} finally {
			logger.info("Finished executing the step 'I check the box next to the task name {}'", string);
		}
	}

	@Then("the task {string} should be marked as completed")
	public void the_task_should_be_marked_as_completed(String string) {
		try {
			logger.info("the task {} should be marked as completed", string);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));

			// Locate the checkbox
			WebElement checkInput = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@type,'checkbox')]")));

			// Check if the checkbox is selected
			boolean isChecked = checkInput.isSelected();
			logger.info("Checkbox checked state: {}", isChecked);

			if (!isChecked) {
				logger.info("{} task marked as uncompleted", string);
				throw new ConflictException(string + " taks is uncompleted.");
			}

		} catch (NoSuchElementException e) {
			logger.error("Fields not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Fields not found within the timeout period.", e); // Clarified message
		} catch (Exception e) {
			logger.error("An error occurred: {}", e.getMessage(), e); // Enhanced logging with logger.error
		} finally {
			logger.info("Finished executing the step 'the task {string} should be marked as completed'", string);
		}
	}

	@Given("I have a completed task named {string}")
	public void i_have_a_completed_task_named(String string) {
		try {
			this.loginMethod();

			boolean taskExists = false;

			System.out.println("Title: " + driver.getTitle());

			logger.info("Starting scenario: I have a task named '{}' that is not completed", string);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));

			// Wait for tasks list to be visible and fetch its items
			List<WebElement> tasksList = driver.findElements(By.xpath("//ul[@class='tasks']/li"));

			if (tasksList.isEmpty()) {
				System.out.println("No tasks found!");
				// throw new NotFoundException("No tasks found in the task list.");
			}

			// Iterate through tasks to find a matching one
			for (WebElement task : tasksList) {
				// Compare the task text with the input string
				logger.info("Comparing task text '{}' with the provided input '{}'", task.getText().trim(), string);

				if (string.trim().equalsIgnoreCase(task.getText().trim())) { // Case-insensitive comparison
					logger.info("Task '{}' already exists in the list.", task.getText().trim());
					taskExists = true;
					break;
				}
			}

			if (!taskExists) {
				logger.info("Attempting to add the task to the To-Do list.");
				WebElement taskField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("task")));

				taskField.sendKeys(string);

				logger.info("Attempting to click the 'Add Task' button.");
				WebElement addButton = wait
						.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Add Task']")));

				addButton.click();
			}
		} catch (NoSuchElementException e) {
			logger.error("Fields not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Fields not found within the timeout period.", e); // Clarified message
		} catch (Exception e) {
			logger.error("An error occurred: {}", e.getMessage(), e); // Enhanced logging with logger.error
		} finally {
			logger.info("Finished executing the step 'I have a task named '{}' that is not completed'", string);
		}
	}

	@When("I uncheck the box next to the task named {}")
	public void i_uncheck_the_box_next_to_the_task_named(String string) {
		try {
			logger.info("Starting scenario: I uncheck the box next to the task named {}", string);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));

			// Locate the checkbox
			WebElement checkInput = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@type,'checkbox')]")));

			// Check if the checkbox is selected
			boolean isChecked = checkInput.isSelected();
			logger.info("Checkbox checked state: {}", isChecked);

			if (isChecked) {
				// Click the checkbox
				checkInput.click();
			}

		} catch (NoSuchElementException e) {
			logger.error("Fields not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Fields not found within the timeout period.", e); // Clarified message
		} catch (Exception e) {
			logger.error("An error occurred: {}", e.getMessage(), e); // Enhanced logging with logger.error
		} finally {
			logger.info("Finished executing the step 'I uncheck the box next to the task named {}'", string);
		}

	}

	@Then("the task {string} should be marked as incomplete")
	public void the_task_should_be_marked_as_incomplete(String string) {
		try {
			logger.info("the task {} should be marked as incomplete", string);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));

			// Locate the checkbox
			WebElement checkInput = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@type,'checkbox')]")));

			// Check if the checkbox is selected
			boolean isChecked = checkInput.isSelected();
			logger.info("Checkbox checked state: {}", isChecked);

			if (isChecked) {
				logger.info("{} task marked as completed", string);
				throw new ConflictException(string + " taks is completed.");
			}

		} catch (NoSuchElementException e) {
			logger.error("Fields not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Fields not found within the timeout period.", e); // Clarified message
		} catch (Exception e) {
			logger.error("An error occurred: {}", e.getMessage(), e); // Enhanced logging with logger.error
		} finally {
			logger.info("Finished executing the step 'the task {} should be marked as incomplete'", string);
		}
	}
}
