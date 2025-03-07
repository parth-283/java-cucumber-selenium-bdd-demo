Feature: Toggle task completion
  As a user
  I want to mark tasks as completed or incomplete
  So that I can manage task progress

  Scenario: Mark a task as completed
    Given I have a task named "Clean the room" that is not completed
    When I check the box next to the task name "Clean the room"
    Then the task "Clean the room" should be marked as completed

  Scenario: Unmark a completed task
    Given I have a completed task named "Water the plants"
    When I uncheck the box next to the task named "Water the plants"
    Then the task "Water the plants" should be marked as incomplete
