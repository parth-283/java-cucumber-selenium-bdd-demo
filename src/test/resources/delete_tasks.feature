Feature: Delete tasks
  As a user
  I want to delete tasks from the To-Do list
  So that I can remove unnecessary items

  Scenario: Delete an existing task
    Given I have a task named "Buy milk"
    When I click the delete button for the task named "Buy milk"
    Then the task "Buy milk" should no longer appear in the task list
