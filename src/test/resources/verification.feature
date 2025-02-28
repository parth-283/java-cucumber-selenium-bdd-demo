Feature: Email Verification
  
  As a registered user,
  I want to verify my email address,
  So that I can activate my account and access all features.

  Scenario: Successful Email Verification
    Given the user receives a verification email with a valid token
    When the user clicks the verification link in the email
    Then the user should be redirected to the login page
    And a success message "Email verified successfully!" should be displayed
