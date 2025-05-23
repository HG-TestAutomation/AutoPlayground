# Login Feature Testing

## Overview

This project demonstrates automated testing of a login page using Selenium WebDriver with Cucumber for BDD (Behavior-Driven Development) and Allure reporting.

[![Automation Tests](https://github.com/YOUR_USERNAME/login-automation-testing/actions/workflows/automation-tests.yml/badge.svg)](https://github.com/YOUR_USERNAME/login-automation-testing/actions/workflows/automation-tests.yml)

## Features Tested

The login functionality is tested through several scenarios:

1. **Verify login form elements are displayed**
   - Validates that all UI elements of the login form are correctly displayed
   - Elements verified: login form, username field, password field, remember me checkbox, login button

2. **Login with empty fields**
   - Tests the form validation when attempting to submit with empty fields
   - Verifies that appropriate validation messages are displayed

3. **Successful login**
   - Tests that a user with valid credentials can log in successfully
   - Verifies successful redirection to the home page after login

4. **Login with remember me checkbox**
   - Tests the login flow when the "remember me" checkbox is selected
   - Verifies successful authentication with this option

5. **Login with different credentials** (Scenario Outline)
   - Tests various combinations of valid/invalid usernames and passwords
   - Verifies appropriate error messages for invalid login attempts
   - Test cases include:
     - Valid username with invalid password
     - Invalid username with valid password
     - Invalid username with invalid password

## Test Structure

- **Feature File**: Located at `src/test/resources/features/login.feature`
- **Step Definitions**: Located at `src/test/java/com/automation/stepdefinitions/LoginStepDefs.java`
- **Test Runner**: Located at `src/test/java/com/automation/runners/TestRunnerTest.java`
- **WebDriver Management**: Handled by `com.automation.utils.DriverManager`

## How to Run Tests

### Prerequisites

- Java JDK 11 or higher
- Maven installed
- Chrome or Firefox browser installed

### Running the Tests Locally

1. **Run all tests using Maven**:

```powershell
mvn clean test
```

2. **Generate and view Allure report**:

```powershell
# Generate the report
mvn allure:report

# Open the report in browser
mvn allure:serve
```

3. **Run using the batch file**:

```powershell
.\generate-allure-report.bat
```

### Running Tests with GitHub Actions

This project is configured to run automatically with GitHub Actions:

1. **Automatic runs:**
   - On every push to main/master branch
   - On every pull request to main/master branch
   - Every day at midnight (UTC)

2. **Manual runs:**
   - Go to your GitHub repository
   - Click on "Actions" tab
   - Select "Login Automation Tests" workflow
   - Click "Run workflow" button

3. **Viewing results:**
   - After the workflow completes, Allure reports are available as artifacts
   - Reports are also published to GitHub Pages (if configured)

### Tags

Tests are organized using tags. The default configuration runs all tests with the `@smoke` tag.

To run specific tests, you can modify the tags in the TestRunnerTest.java file or specify at runtime:

```powershell
mvn test -Dcucumber.filter.tags="@smoke"
```

## Reporting

### Allure Reports

- Screenshots are automatically captured for each scenario and attached to the Allure report
- Reports are generated in `target/allure-reports` directory
- Detailed step information and test results are available in the report
- The report includes environment information, test duration, and visual representations of test results

## Project Configuration

### Dependencies

- Selenium WebDriver for browser automation
- Cucumber for BDD test definition and execution
- Allure for advanced reporting
- JUnit for test assertions and execution

### Key Files

- **pom.xml**: Contains all project dependencies and build configurations
- **generate-allure-report.bat**: Batch script to clean, run tests, and generate Allure reports
- **Hooks.java**: Contains setup and teardown methods for tests, including screenshot capture

## Best Practices

1. **Screenshot capture**: Screenshots are automatically captured for each scenario, regardless of pass/fail status
2. **Clean WebDriver management**: Browser instances are properly initialized and closed
3. **BDD approach**: Tests are written in Gherkin syntax for better readability and business alignment
4. **Separate concerns**: Step definitions, page objects, and utility classes are separated
5. **Parameterization**: Scenario outlines are used for data-driven testing
6. **CI/CD Integration**: Project is ready for integration with Jenkins or GitHub Actions

## Adding New Tests

To add new test cases:

1. Add new scenarios to the feature file or create new feature files
2. Implement step definitions in `LoginStepDefs.java` or create new step definition classes
3. Run the tests and verify the Allure report

## Troubleshooting

- **Driver issues**: Ensure WebDriverManager is properly configured and browser versions are compatible
- **Report generation failures**: Check that the Allure CLI is properly installed
- **Test failures**: Review screenshots in the Allure report for visual debugging
