Feature: User Login
  
  As a registered user,
  I want to log in to the application,
  So that I can access my account and personalized features.

  Scenario: Successful Login
    Given the user is on the login page
    When the user enters valid credentials
      | username                     | password   |
      | parth@sourcenettechnology.in | Parth@2002 |
    And clicks the "loginbtn" button
    Then the user should be logged in

  Scenario: Incorrect Password
    Given the user is on the login page
    When the user enters incorrect password
      | username                     | password  |
      | parth@sourcenettechnology.in | Parth@123 |
    And clicks the "loginbtn" button
    Then the user should see an error message "Something went wrong. Please check your credentials."
    And the user should remain on the login page "login"

  Scenario: Incorrect Username
    Given the user is on the login page
    When the user enters incorrect username
      | username                       | password   |
      | parth12@sourcenettechnology.in | Parth@2002 |
    And clicks the "loginbtn" button
    Then the user should see an error message "Something went wrong. Please check your credentials."
    And the user should remain on the login page "login"

  Scenario: Empty Username and Password
    Given the user is on the login page
    When the user leaves username and password blank
      | username | password |
      |          |          |
    And clicks the "loginbtn" button
    Then the user should see an error message "Username and Password are required."
    And the user should remain on the login page "login"

  Scenario: Password Visibility Toggle
    Given the user is on the login page
    When the user enters a password
      | username                     | password   |
      | parth@sourcenettechnology.in | Parth@2002 |
    And clicks the "eye-icon" toggle
    Then the password should be visible
    When the user clicks the "eye-icon" toggle
    Then the password should be hidden
