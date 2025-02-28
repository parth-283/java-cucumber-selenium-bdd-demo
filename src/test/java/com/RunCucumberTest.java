package com;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        glue = {"com.example.cucumber_selenium"},
        plugin = {"pretty", "html:target/cucumber-reports"},
        publish = true
        
)
public class RunCucumberTest {
}