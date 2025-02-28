package com.example.cucumber_selenium;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import com.example.cucumber_selenium.driver.WebDriverFactory;
import com.example.utils.EmailUtils;
import jakarta.mail.MessagingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert;

public class EmailVerificationSteps {

	private EmailUtils emailUtils = new EmailUtils();
	private String verificationToken;
	private WebDriverFactory webDriverFactory;

	private static final Logger logger = LoggerFactory.getLogger(EmailVerificationSteps.class);

	public EmailVerificationSteps(WebDriver driver) {
		this.webDriverFactory = new WebDriverFactory();
	}

	@After
	public void quitDriver() {
		this.webDriverFactory.quitDriver();
	}

	@Given("the user receives a verification email with a valid token")
	public void the_user_receives_a_verification_email_with_a_valid_token() {
		try {
			String username = "parth@sourcenettechnology.in";
			String password = "Parth@123";
			String host = "smtp.hostinger.com";
			String folderName = "INBOX";

			String emailContent = emailUtils.getEmailContent(username, password, host, folderName);

			if (emailContent == null) {
				throw new MessagingException("Verification email not found.");
			}

			Pattern pattern = Pattern.compile("verificationToken=([a-zA-Z0-9\\-_\\.]+)", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(emailContent);

			if (matcher.find()) {
				verificationToken = matcher.group(1);
			} else {
				throw new MessagingException("Verification token not found in email.");
			}
		} catch (MessagingException e) {
			logger.error("Error retrieving verification email: {}", e.getMessage());
			Assert.fail("Error retrieving verification email: " + e.getMessage());
		}
	}

	@When("the user clicks the verification link in the email")
	public void the_user_clicks_the_verification_link_in_the_email() {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("the user should be redirected to the login page")
	public void the_user_should_be_redirected_to_the_login_page() {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("a success message {string} should be displayed")
	public void a_success_message_should_be_displayed(String string) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}
}