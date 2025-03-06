Feature: Edit tasks
  As a user
  I want to edit existing tasks
  So that I can update their details

  Scenario: Edit a task successfully
    Given I have a task named "Call mom"
    When I click the edit button for the task named "Call mom"
    And I change the text "Call mom" to "Call mom and dad"
    And I click the save button for the task named "Call mom and dad"
    Then the task should update to "Call mom and dad" in the task list
