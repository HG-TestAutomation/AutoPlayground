# Test Maintenance Guide

This document provides guidelines for maintaining and extending the login feature tests.

## Code Structure

```
src/
├── test/
│   ├── java/
│   │   └── com/
│   │       └── automation/
│   │           ├── stepdefinitions/  # Step definition classes
│   │           │   ├── LoginStepDefs.java
│   │           │   └── Hooks.java
│   │           ├── runners/          # Test runners
│   │           │   └── TestRunnerTest.java
│   │           └── utils/            # Utility classes
│   │               └── DriverManager.java
│   └── resources/
│       ├── features/                 # Feature files
│       │   └── login.feature
│       └── config/                   # Configuration files
```

## Modifying Tests

### Adding a New Scenario

1. Open the feature file (`src/test/resources/features/login.feature`)
2. Add a new scenario following the Gherkin syntax:

```gherkin
@smoke
Scenario: New login scenario
  When I enter username "new_user"
  And I enter password "new_password"
  And I click the login button
  Then I should see error message "Account locked"
```

3. Implement any missing step definitions in `LoginStepDefs.java`

### Adding a New Step Definition

1. Open the step definitions file (`src/test/java/com/automation/stepdefinitions/LoginStepDefs.java`)
2. Add a new method with the appropriate Cucumber annotation:

```java
@Then("I should see error message {string}")
public void iShouldSeeErrorMessage(String errorMessage) {
    // Implementation
    assert(loginPage.getErrorMessage().equals(errorMessage));
}
```

### Modifying Existing Tests

When modifying existing tests:
1. Ensure backward compatibility when possible
2. Update documentation to reflect changes
3. Run tests to verify changes don't break existing functionality

## Best Practices

### Writing Feature Files

1. **Be Descriptive**: Use clear, descriptive scenario names
2. **One Assertion Per Scenario**: Focus each scenario on testing one thing
3. **Use Background**: For common setup steps
4. **Use Tags**: Categorize tests with tags like `@smoke`, `@regression`, `@wip`
5. **Keep It Simple**: Write scenarios in plain language that non-technical stakeholders can understand

### Writing Step Definitions

1. **Single Responsibility**: Each step should do one thing
2. **Reusability**: Design steps to be reusable across scenarios
3. **Parameterization**: Use parameters for dynamic values
4. **Assertions**: Include clear assertions with meaningful messages
5. **Error Handling**: Handle exceptions gracefully

### Screenshot Management

The `Hooks.java` file manages screenshot capture:
- Screenshots are taken at the end of each scenario
- They are attached to both Cucumber and Allure reports
- Screenshots are named after the scenario for easy identification

## Common Pitfalls

1. **Flaky Tests**: Tests that sometimes pass and sometimes fail
   - Solution: Add appropriate waits, strengthen selectors

2. **Browser Compatibility**: Tests working in one browser but not another
   - Solution: Use browser-agnostic selectors and explicit waits

3. **Slow Tests**: Tests taking too long to execute
   - Solution: Optimize selectors, minimize browser operations

4. **Dependency Between Tests**: Tests that require other tests to run first
   - Solution: Make each test independent and self-contained

## Test Execution Strategy

1. **Continuous Integration**: Configure tests to run on every commit
2. **Parallel Execution**: Configure parallel execution for faster feedback
3. **Selective Execution**: Use tags to run specific subsets of tests
4. **Scheduled Execution**: Configure nightly runs for comprehensive test suites

## Updating Dependencies

When updating dependencies:
1. Check the changelog for breaking changes
2. Update one dependency at a time
3. Run tests after each update to catch issues early
4. Update documentation to reflect new versions
