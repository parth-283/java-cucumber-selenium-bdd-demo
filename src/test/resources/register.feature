Feature: User Registration
  
  As a new user,
  I want to register for an account,
  So that I can access the application's features.

  Scenario: Successful Registration
    Given the user is on the registration page
    When the user enters valid registration details
      | username                | password  |
      | parth@sourcenettechnology.in | Parth@123 |
    And clicks the register "registerbtn" button
    Then the user should be auto logged in

  Scenario: Username Already Exists
    Given the user is on the registration page
    When the user enters registration details with an existing username
      | username                | password  |
      | parth@sourcenettechnology.in | Parth@123 |
    And clicks the register "registerbtn" button
    Then the user should remain on the registration page "/register"
    And an error message "Something went wrong. Please check your credentials." should be displayed

  Scenario: Empty Required Fields
    Given the user is on the registration page
    When the user attempts to register with empty required fields
      | username | password |
      |          |          |
    And clicks the register "registerbtn" button
    Then the user should remain on the registration page "/register"
    And an error message "Username and Password are required." should be displayed

  Scenario: Password Visibility Toggle
    Given the user is on the registration page
    When the user enters a password on the registration page
      | username                     | password   |
      | parth@sourcenettechnology.in | Parth@2002 |
    And clicks the register "eye-icon" toggle
    Then the password should be visible on the registration page
    When clicks the register "eye-icon" toggle
    Then the password should be hidden on the registration page
