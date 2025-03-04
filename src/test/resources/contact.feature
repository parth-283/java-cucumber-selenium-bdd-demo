Feature: Contact Us
  
  As a user,
  I want to contact the website administrators,
  So that I can ask questions or provide feedback.

  Scenario: Contact Form Submission with Missing Name
    Given the user is on the contact us page
    When the user fills out the contact form with a missing name
      | name | email                | message                                |
      | null | john.doe@example.com | I have a question about your services. |
    And clicks the contact "contact_btn" button
    Then the user should see an error message on the contact us page: "Name, Email, and Message are required fields."

  Scenario: Contact Form Submission with Missing Email
    Given the user is on the contact us page
    When the user fills out the contact form with a missing email
      | name     | email | message                                |
      | John Doe | null  | I have a question about your services. |
    And clicks the contact "contact_btn" button
    Then the user should see an error message on the contact us page: "Name, Email, and Message are required fields."

  Scenario: Contact Form Submission with Missing Message
    Given the user is on the contact us page
    When the user fills out the contact form with a missing message
      | name     | email                | message |
      | John Doe | john.doe@example.com | null    |
    And clicks the contact "contact_btn" button
    Then the user should see an error message on the contact us page: "Name, Email, and Message are required fields."

  Scenario: Contact Form Submission with Empty Form
    Given the user is on the contact us page
    When the user attempts to submit an empty form
    And clicks the contact "contact_btn" button
    Then the user should see an error message on the contact us page: "Name, Email, and Message are required fields."

  Scenario: Successful Contact Form Submission
    Given the user is on the contact us page
    When the user fills out the contact form with valid information
      | name     | email                        | message                                |
      | John Doe | parth@sourcenettechnology.in | I have a question about your services. |
    And clicks the contact "contact_btn" button
    Then the user should see a success message on the contact us page: "Your message has been sent successfully!"
    And the form fields should be cleared
