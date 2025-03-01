# Java Cucumber Selenium BDD Demo


[![Build Status](https://github.com/yourusername/your-repo/actions/workflows/your-workflow.yml/badge.svg)](https://github.com/yourusername/your-repo/actions/workflows/your-workflow.yml)
[![codecov](https://codecov.io/gh/yourusername/your-repo/branch/main/graph/badge.svg?token=YOUR_CODECOV_TOKEN)](https://codecov.io/gh/yourusername/your-repo)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)


This repository demonstrates Behavior-Driven Development (BDD) testing using Java, Selenium WebDriver, and Cucumber. It serves as a practical example for setting up and running automated web tests with a focus on clear and understandable feature specifications.

## Table of Contents

* [Getting Started](#getting-started)
* [Prerequisites](#prerequisites)
* [Installation](#installation)
* [Running the Tests](#running-the-tests)
* [Project Structure](#project-structure)
* [Writing Feature Files](#writing-feature-files)
* [Writing Step Definitions](#writing-step-definitions)
* [Page Object Model](#page-object-model)
* [Reporting](#reporting)
* [Contributing](#contributing)
* [License](#license)

## Getting Started

These instructions will guide you through setting up and running the project on your local machine.

## Prerequisites

* Java Development Kit (JDK) 8 or higher
* Maven
* An IDE (e.g., IntelliJ IDEA, Eclipse)
* A web browser (e.g., Chrome, Firefox)

## Installation

1.  Clone the repository:

    ```bash
    git clone [repository URL]
    ```

2.  Navigate to the project directory:

    ```bash
    cd java-cucumber-selenium-bdd-demo
    ```

3.  Build the project using Maven:

    ```bash
    mvn clean install
    ```

## Running the Tests

To run the Cucumber tests, use the following Maven command:

```bash
mvn test
```

This command will execute the tests defined in the feature files and generate a test report.

## Project Structure

```java-cucumber-selenium-bdd-demo/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── pages/        # Page Object Model classes
│   └── test/
│       ├── java/
│       │   └── stepDefinitions/ # Cucumber step definitions
│       └── resources/
│           └── features/     # Feature files (.feature)
├── pom.xml                   # Maven project configuration
└── README.md
```

## Writing Feature Files
Feature files are written in Gherkin syntax and describe the behavior of the application. They are located in the src/test/resources/features/ directory.

Example:

```
Feature: User Login

  Scenario: Successful login
    Given the user is on the login page
    When the user enters valid credentials
    And clicks the login button
    Then the user should be logged in
```

## Writing Step Definitions
Step definitions are Java methods that implement the steps defined in the feature files. They are located in the src/test/java/stepDefinitions/ directory.

Example:
```
public class LoginSteps {

    @Given("the user is on the login page")
    public void theUserIsOnTheLoginPage() {
        // Implementation
    }

    @When("the user enters valid credentials")
    public void theUserEntersValidCredentials() {
        // Implementation
    }

    // ... other step definitions
}
```

## Page Object Model
The Page Object Model (POM) is a design pattern that represents web pages as Java classes. It improves code maintainability and reusability. Page classes are located in the src/main/java/pages/ directory.

## Reporting
After running the tests, Cucumber generates a report that provides details about the test execution. You can configure different reporting plugins in the pom.xml file.

## Contributing

Contributions are welcome! Please feel free to submit a pull request or open an issue.

## License

This project is licensed under the [MIT License](LICENSE).
