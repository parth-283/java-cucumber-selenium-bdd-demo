@tag
Feature: Change Password
  
  As a logged-in user,
  I want to change my password,
  So that I can maintain the security of my account.

  @tag1
  Scenario: Incorrect Current Password
    Given the user is logged in and on the change password page
    When the user enters an incorrect current password
      | currentPassword | newPassword |
      | WrongPassword   | Parth@123   |
    And clicks the change password "change-pwd-btn" button
    Then the user should see an error message on change password "Something went wrong. Please check your credentials."
    And the user should not be able to log in with the new password

  @tag2
  Scenario: Empty Required Fields
    Given the user is logged in and on the change password page
    When the user attempts to change password with empty required fields
      | currentPassword | newPassword |
      |                 |             |
    And clicks the change password "change-pwd-btn" button
    Then the user should see an error message on change password "Old Password and New Password are required."

  @tag3
  Scenario: Successful Password Change
    Given the user is logged in and on the change password page
    When the user enters valid current password, new password, and confirm new password
      | currentPassword | newPassword |
      | Parth@2002      | Parth@123   |
    And clicks the change password "change-pwd-btn" button
    Then the user should be able to log in with the new password
