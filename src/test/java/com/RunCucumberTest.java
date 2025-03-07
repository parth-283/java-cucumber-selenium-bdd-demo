package com;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {
		        "src/test/resources/register.feature",
		        "src/test/resources/login.feature",
		        "src/test/resources/contact.feature",
		        "src/test/resources/user_auth.feature",
		        "src/test/resources/add_tasks.feature",
		        "src/test/resources/edit_tasks.feature",
		        "src/test/resources/delete_tasks.feature",
		        "src/test/resources/filter_tasks.feature",
		        "src/test/resources/toggle_completion.feature",
		        "src/test/resources/change-password.feature"
		    }, 
		glue = { "com.example.cucumber_selenium" },
		plugin = { "pretty","html:target/cucumber-reports" }, 
		monochrome = false,
		publish = true
)
public class RunCucumberTest {
}