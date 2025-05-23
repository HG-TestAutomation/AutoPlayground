# CI/CD Integration

This document explains how to set up continuous integration and continuous delivery for the login automation tests using both Jenkins and GitHub Actions.

## Overview

The project is configured with both:
1. **Jenkins Pipeline** - For traditional CI/CD environments
2. **GitHub Actions** - For cloud-based CI/CD directly in GitHub

Choose the approach that best fits your workflow and infrastructure.

## Integration Options

### GitHub + Jenkins

This setup uses GitHub for source control and Jenkins for CI/CD:

1. Code is pushed to GitHub repository
2. Jenkins pulls code from GitHub when triggered
3. Jenkins executes tests and generates reports
4. Reports can be viewed in the Jenkins UI

Key files:
- `Jenkinsfile`: Pipeline configuration
- `docs/JENKINS_SETUP.md`: Detailed Jenkins setup instructions

### GitHub + GitHub Actions

This setup uses GitHub for both source control and CI/CD:

1. Code is pushed to GitHub repository
2. GitHub Actions workflow is triggered
3. Tests run in GitHub-hosted runners
4. Reports are published as artifacts and/or to GitHub Pages

Key files:
- `.github/workflows/automation-tests.yml`: Workflow configuration
- Automatically publishes Allure reports to GitHub Pages

## Comparison

| Feature | Jenkins | GitHub Actions |
|---------|---------|---------------|
| Setup Complexity | Higher (requires server) | Lower (cloud-based) |
| Customization | Very high | High |
| Integration with tools | Extensive via plugins | Good via actions |
| Execution Environment | Self-managed | GitHub-managed |
| Cost | Self-hosted costs | Free for public repos |
| Report Storage | On Jenkins server | GitHub Pages / Artifacts |

## Recommended Approach

### For Existing Jenkins Users
If you already have a Jenkins server configured:
1. Follow the instructions in `docs/JENKINS_SETUP.md`
2. Use the provided `Jenkinsfile` 

### For Teams Without CI Server
If you don't have a Jenkins server or prefer cloud-based CI:
1. Use the provided GitHub Actions workflow
2. Reports will be available as artifacts and published to GitHub Pages

## Configuration Variables

Both CI systems support environment variables and parameters:

- `BROWSER`: Browser to use for testing (chrome/firefox)
- `TAGS`: Cucumber tags to include in test run

### Adding Custom Parameters

#### In Jenkins:
Modify the `parameters` block in `Jenkinsfile`

#### In GitHub Actions:
Add environment variables or inputs in `.github/workflows/automation-tests.yml`

## Scheduling Tests

### Jenkins:
Configure the cron expression in the job configuration or use the `Build periodically` option

### GitHub Actions:
The workflow is already configured to run daily at midnight UTC:
```yaml
schedule:
  - cron: '0 0 * * *'
```
