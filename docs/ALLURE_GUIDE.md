# Understanding Allure Reports

## Introduction

This guide explains how to interpret the Allure reports generated for the login feature tests.

## Accessing the Report

After running tests, the Allure report can be accessed in two ways:

1. **Static HTML Report**: 
   ```powershell
   mvn allure:report
   ```
   This generates a static report in `target/allure-reports` that can be opened in any browser.

2. **Web Server Report**:
   ```powershell
   mvn allure:serve
   ```
   This generates a report and opens it in your default browser via a local web server.

## Report Sections

### Overview Page

The main dashboard shows:
- Total tests run
- Pass/fail statistics
- Test duration
- Categories of failures
- Environment information

### Suites & Test Cases

This section shows all test scenarios grouped by feature files:
- Each test case shows the Gherkin steps and their status
- You can click on a test to view details including:
  - Full step execution
  - Attachments (screenshots)
  - Test duration
  - Environment variables

### Categories

Tests are categorized by failure type (if any failures occurred):
- Product defects
- Test defects
- Known issues
- Other problems

### Timeline

Shows the chronological execution of tests:
- Parallel execution visualization
- Duration of each test
- Helps identify slow tests or bottlenecks

### Behaviors

Tests are organized according to features and scenarios:
- Grouped by feature
- Shows pass/fail statistics for each feature
- Provides a BDD-oriented view of test results

### Screenshots

Screenshots are attached to each test and can be viewed by:
1. Click on a test case
2. Scroll to the bottom to see attachments
3. Click on the image to view it full size

Screenshots are taken at the end of each scenario regardless of pass/fail status.

## Understanding Test Results

### Passed Tests

- Marked with green color
- All steps completed successfully
- Screenshots show the final state after test completion

### Failed Tests

- Marked with red color
- The failing step is highlighted
- Screenshots show the state at the moment of failure
- Error messages and stack traces are available

### Broken Tests

- Marked with orange color
- Indicates infrastructure problems rather than test failures
- May include environment setup issues, WebDriver problems, etc.

## Report Features

### Filtering

You can filter tests by:
- Status (passed, failed, broken, etc.)
- Duration
- Feature
- Tags

### History

If history is enabled, you can see:
- Test stability across builds
- Trends in test execution times
- Recurring failures

### Trends

Shows how the test suite is evolving over time:
- Pass rate trends
- Duration trends
- Unstable tests

## Troubleshooting Common Issues

1. **Missing Screenshots**: Check if the WebDriver is properly initialized and the screenshot capture code is working
2. **Empty Reports**: Verify that tests are actually running and Allure listeners are configured correctly
3. **Broken Links**: Ensure file paths are correct and files exist where expected
4. **Missing Categories**: Check that category definitions are properly configured

## Customizing Reports

Allure reports can be customized via:
1. **allure.properties** file
2. **categories.json** to define custom failure categories
3. **environment.properties** to define environment variables to display
