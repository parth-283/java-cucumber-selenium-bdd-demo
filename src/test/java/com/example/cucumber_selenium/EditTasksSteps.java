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
import pages.LoginPage;

public class EditTasksSteps {

	private static final Logger logger = LoggerFactory.getLogger(EditTasksSteps.class);
	private final Dotenv dotenv = Dotenv.load();
	private WebDriver driver;
	private WebDriverFactory webDriverFactory;

	private String userName = dotenv.get("DB_USERNAME");
	private String password = dotenv.get("DB_PASSWORD");
	private String baseURL = dotenv.get("PROJECT_URL");
	private static final int DEFAULT_WAIT_TIME = 5;

	public EditTasksSteps(WebDriverFactory webDriverFactory) {
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

	@Given("I have a task named {string}")
	public void i_have_a_task_named(String string) {
		try {
			this.loginMethod();

			boolean taskExists = false;

			System.out.println("Title: " + driver.getTitle());
			logger.info("Starting scenario: I have a task named '{}'", string);

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));

			// Wait for tasks list to be visible and fetch its items
			List<WebElement> tasksList = wait
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//ul[@class='tasks']/li")));

			if (tasksList.isEmpty()) {
				System.out.println("No tasks found!");
				throw new NotFoundException("No tasks found in the task list.");
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
			logger.info("Finished executing the step 'I have a task named {}'", string);
		}
	}

	@When("I click the edit button for the task named {string}")
	public void i_click_the_edit_button_for_the_task_named(String taskName) {
		try {
			logger.info("Running feature for 'I click the edit button for the task named {}'", taskName);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));

			// Locate the specific task by its text
			List<WebElement> taskElements = wait
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//ul[@class='tasks']/li")));

			// Iterate through the tasks to find the one matching the task name
			for (WebElement taskElement : taskElements) {
				WebElement taskNameElement = taskElement.findElement(By.xpath(".//span")); // Locate the task name
																							// element
				String actualTaskName = taskNameElement.getText().trim();

				if (actualTaskName.equalsIgnoreCase(taskName)) { // Compare the task name (case-insensitive)
					logger.info("Found the task '{}'. Attempting to locate the 'Edit' button.", taskName);

					// Locate the corresponding edit button within this task
					WebElement editButton = taskElement
							.findElement(By.xpath(".//button[contains(@class, 'button-edit')]"));

					// Click the edit button
					editButton.click();
					logger.info("Successfully clicked the 'Edit Task' button for task '{}'.", taskName);
					return; // Exit the loop after clicking the button
				}
			}

		} catch (NoSuchElementException e) {
			logger.error("Task or button not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Elements not found within the timeout period.", e);
		} catch (Exception e) {
			logger.error("An error occurred: {}", e.getMessage(), e);
		} finally {
			logger.info("Finished executing the step 'I click the edit button for the task named {}'", taskName);
		}
	}

	@When("I change the text {string} to {string}")
	public void i_change_the_text_to(String oldText, String newText) {
		try {
			logger.info("Running feature for 'I change the text {} to {}'", oldText, newText);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));

			// Locate the specific task by its text
			List<WebElement> taskElements = wait
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//ul[@class='tasks']/li")));

			// Iterate through the tasks to find the one matching the task name
			for (WebElement taskElement : taskElements) {
				WebElement taskNameElement = taskElement
						.findElement(By.xpath(".//input[contains(@value, '" + oldText + "')]"));

				// Clear the existing value in the input field
				taskNameElement.clear();

				// Enter the new text
				taskNameElement.sendKeys(newText);

				logger.info("Successfully updated the task '{}' with new text '{}'.", oldText, newText);
				return; // Exit the loop after editing the task
			}

		} catch (NoSuchElementException e) {
			logger.error("Fields not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Fields not found within the timeout period.", e);
		} catch (Exception e) {
			logger.error("An error occurred: {}", e.getMessage(), e);
		} finally {
			logger.info("Finished executing the step 'I change the text {} to {}'", oldText, newText);
		}
	}

	@When("I click the save button for the task named {string}")
	public void i_click_the_save_button_for_the_task_named(String taskName) {
		try {
			logger.info("Running feature for 'I click the save button for the task named {}'", taskName);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));

			// Locate the specific task by its text
			List<WebElement> taskElements = wait
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//ul[@class='tasks']/li")));

			// Iterate through the tasks to find the one matching the task name
			for (WebElement taskElement : taskElements) {
				WebElement taskNameElement = taskElement
						.findElement(By.xpath(".//input[contains(@value, '" + taskName + "')]"));

				String actualTaskName = taskNameElement.getAttribute("value").trim();

				if (actualTaskName.equalsIgnoreCase(taskName)) { // Compare the task name (case-insensitive)
					logger.info("Found the task '{}'. Attempting to locate the 'Save' button.", taskName);

					// Locate the corresponding edit button within this task
					WebElement editButton = taskElement
							.findElement(By.xpath(".//button[contains(@class, 'button-edit')]"));

					// Click the edit button
					editButton.click();
					logger.info("Successfully clicked the 'Edit Task' button for task '{}'.", taskName);
					return; // Exit the loop after clicking the button
				}
			}

		} catch (NoSuchElementException e) {
			logger.error("Task or button not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Elements not found within the timeout period.", e);
		} catch (Exception e) {
			logger.error("An error occurred: {}", e.getMessage(), e);
		} finally {
			logger.info("Finished executing the step 'I click the save button for the task named {}'", taskName);
		}
	}

	@Then("the task should update to {string} in the task list")
	public void the_task_should_update_to_in_the_task_list(String string) {
		try {
			logger.info("Running feature for 'the task should update to {} in the task list'", string);

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));

			// Wait for tasks list to be visible and fetch its items
			List<WebElement> tasksList = wait
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//ul[@class='tasks']/li")));

			if (tasksList.isEmpty()) {
				System.out.println("No tasks found!");
				throw new NotFoundException("No tasks found in the task list.");
			}

			// Iterate through tasks to find a matching one
			for (WebElement task : tasksList) {
				// Compare the task text with the input string
				if (string.trim().equalsIgnoreCase(task.getText().trim())) { // Case-insensitive comparison
					logger.info("Successfully getting task {} into the task list", string);
					break;
				} else {
					logger.error("{} task found in the task list.", string);
					throw new NotFoundException(string + " task found in the task list.");
				}
			}

		} catch (NoSuchElementException e) {
			logger.error("Fields not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Fields not found within the timeout period.", e); // Clarified message
		} catch (Exception e) {
			logger.error("An error occurred: {}", e.getMessage(), e); // Enhanced logging with logger.error
		} finally {
			logger.info("Finished executing the step 'I have a task named {}'", string);
		}
	}
}
