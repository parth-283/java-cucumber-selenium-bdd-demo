Feature: Filter tasks
  As a user
  I want to filter out completed tasks
  So that I can focus on pending work

  Scenario: Hide completed tasks
    Given I have both completed and incomplete tasks
    When I click the "Hide Completed" button
    Then only incomplete tasks should be visible in the task list

  Scenario: Show all tasks
    Given I have both completed and incomplete tasks
    When I click the "Show All" button
    Then all tasks, completed and incomplete, should be visible
