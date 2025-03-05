Feature: User authentication
  As a user
  I want to log in to manage my tasks
  So that my tasks are stored securely

  Scenario: Access To-Do list when logged in
    Given I am logged in as a user
    When I visit the To-Do list page
    Then I should see my tasks

  Scenario: Restrict access for non-logged-in users
    Given I am not logged in
    When I visit the To-Do list page
    Then I should not see any tasks
    And I should be prompted to log in
