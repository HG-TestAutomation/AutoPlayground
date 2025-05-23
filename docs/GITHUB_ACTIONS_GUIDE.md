# GitHub Actions Setup Guide

This guide explains how to use GitHub Actions for continuous integration with this project.

## What is GitHub Actions?

GitHub Actions is a CI/CD (Continuous Integration/Continuous Delivery) platform that allows you to automate your build, test, and deployment pipeline directly within GitHub. It's integrated with your GitHub repository and doesn't require a separate server.

## Workflow Configuration

The GitHub Actions workflow for this project is defined in `.github/workflows/automation-tests.yml`. This workflow:

1. Runs the tests when:
   - Code is pushed to the main/master branch
   - A pull request is created against main/master
   - Daily at midnight (UTC)
   - Manually triggered via the GitHub UI

2. Sets up the necessary environment:
   - Java 11
   - Chrome browser

3. Runs the tests and generates Allure reports

4. Publishes the results:
   - As downloadable artifacts
   - To GitHub Pages (if configured)

## Setting Up GitHub Pages for Reports

To view Allure reports on GitHub Pages:

1. Go to your repository on GitHub
2. Click on "Settings"
3. Scroll to "GitHub Pages" section
4. Under "Source", select "gh-pages branch"
5. Click "Save"

After the workflow runs successfully, your Allure report will be available at:
`https://[your-username].github.io/[repository-name]/allure-report/`

## Customizing the Workflow

### Changing the Schedule

To change when tests run automatically, modify the `cron` expression in the workflow file:

```yaml
schedule:
  - cron: '0 0 * * *'  # Currently set to run at midnight UTC
```

### Adding Environment Variables

To add environment variables, add them to the `env` section of the job:

```yaml
jobs:
  test:
    runs-on: ubuntu-latest
    env:
      BROWSER: chrome
      HEADLESS: true
```

### Running Tests with Different Parameters

To run tests with specific tags or browsers, modify the "Run Tests" step:

```yaml
- name: Run Tests
  run: mvn clean test -Dcucumber.filter.tags="@smoke" -Dbrowser=chrome
```

## Viewing Test Results

1. Go to your repository on GitHub
2. Click the "Actions" tab
3. Select the workflow run you're interested in
4. Under "Artifacts", you'll find:
   - `allure-results`: Raw results data
   - `allure-report`: Generated HTML report

If GitHub Pages is configured, you can also access the published report directly from the web.

## Troubleshooting

### Common Issues:

1. **Tests failing in GitHub Actions but passing locally:**
   - Check browser compatibility issues
   - Ensure proper waits are implemented for UI elements
   - Consider running tests in headless mode

2. **GitHub Pages not showing the report:**
   - Ensure the workflow has run successfully at least once
   - Check that GitHub Pages is configured to use the gh-pages branch
   - Verify permissions for the GitHub token

3. **Workflow not running automatically:**
   - Check the trigger conditions in the workflow file
   - Verify you're working with the correct branch names
