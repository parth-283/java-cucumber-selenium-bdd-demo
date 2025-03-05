Feature: Add tasks
  As a user
  I want to add tasks to my To-Do list one by one
  So that I can keep track of my work

  Scenario Outline: Add a single task to the list
    Given I am on the To-Do list page
    When I enter "<Task>" in the task input box
    And I click the add "add-todo-btn" button
    Then the task "<Task>" should appear in the task list

    Examples: 
      | Task                 |
      | Buy groceries        |
      | Clean the room       |
      | Pay electricity bill |
