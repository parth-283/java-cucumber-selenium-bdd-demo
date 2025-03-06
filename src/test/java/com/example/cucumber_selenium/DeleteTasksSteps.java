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
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeleteTasksSteps {

	private static final Logger logger = LoggerFactory.getLogger(DeleteTasksSteps.class);
	private WebDriver driver;
	private WebDriverFactory webDriverFactory;

	private static final int DEFAULT_WAIT_TIME = 5;

	public DeleteTasksSteps(WebDriverFactory webDriverFactory) {
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

	@When("I click the delete button for the task named {string}")
	public void i_click_the_delete_button_for_the_task_named(String taskName) {
		try {
			logger.info("Running feature for 'I click the delete button for the task named {}'", taskName);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));

			// Locate the specific task by its text
			List<WebElement> taskElements = wait
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//ul[@class='tasks']/li")));

			// Iterate through the tasks to find the one matching the task name
			for (WebElement taskElement : taskElements) {
				WebElement taskNameElement = taskElement.findElement(By.xpath(".//span")); // Locate the task name
																							// element
				String actualTaskName = taskNameElement.getText().trim();

				if (actualTaskName.equalsIgnoreCase(taskName)) {
					logger.info("Found the task '{}'. Attempting to locate the 'Edit' button.", taskName);

					// Locate the corresponding delete button within this task
					WebElement deleteButton = taskElement
							.findElement(By.xpath(".//button[contains(@class, 'button-delete')]"));

					// Click the edit button
					deleteButton.click();
					logger.info("Successfully clicked the 'Delete Task' button for task '{}'.", taskName);
					return; // Exit the loop after clicking the button
				}
			}

		} catch (NoSuchElementException e) {
			logger.error("Button not found: ", e);
		} catch (TimeoutException e) {
			logger.error("Elements not found within the timeout period.", e);
		} catch (Exception e) {
			logger.error("An error occurred: {}", e.getMessage(), e);
		} finally {
			logger.info("Finished executing the step 'I click the delete button for the task named {}'", taskName);
		}
	}

	@Then("the task {string} should no longer appear in the task list")
	public void the_task_should_no_longer_appear_in_the_task_list(String string) {
		try {

			boolean isDeleted = true;

			logger.info("Running feature for 'the task {} should no longer appear in the task list'", string);

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
				if (string.trim().equalsIgnoreCase(task.getText().trim())) {
					logger.info("Task {} founded, Something went wrong in delete method.", string);
					isDeleted = false;
					throw new NotFoundException("Task founded, Something went wrong in delete method.");
				}
			}

			if (isDeleted) {
				logger.info("Successfully remove task {} into the task list", string);
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
