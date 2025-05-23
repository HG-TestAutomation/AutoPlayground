Feature: Login Page Functionality
  As a user
  I want to use the login page
  So that I can test different login scenarios

  Background: 
    Given I am on the login page

@smoke
  Scenario: Verify login form elements are displayed
    Then the login form should be displayed
    And the username field should be displayed
    And the password field should be displayed
    And the remember me checkbox should be displayed
    And the login button should be displayed

@smoke
  Scenario: Login with empty fields
    When I click the login button
    Then the form should show validation messages

    @smoke
  Scenario: Successful login
    When I enter username "admin"
    And I enter password "password"
    And I click the login button
    Then I should be redirected to the home page


@smoke
  Scenario: Login with remember me checkbox
    When I enter username "admin"
    And I enter password "password"
    And I check the remember me checkbox
    And I click the login button
    Then I should be redirected to the home page


@smoke
  Scenario Outline: Login with different credentials
    When I enter username "<username>"
    And I enter password "<password>"
    And I click the login button
    Then I should see the error message "Invalid username or password"

    Examples:
      | username | password    |
      | admin    | wrong123    |
      | invalid  | password    |
      | invalid  | wrong123    |
