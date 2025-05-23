# Jenkins Integration Guide

## Prerequisites

- Jenkins server installed and running
- Git plugin installed in Jenkins
- Maven plugin installed in Jenkins
- Allure plugin installed in Jenkins

## Setting Up Jenkins Job

### 1. Create a New Jenkins Job

1. Log in to your Jenkins dashboard
2. Click on "New Item"
3. Enter a name for your job (e.g., "Login-Automation-Tests")
4. Select "Pipeline" project
5. Click "OK"

### 2. Configure the Pipeline

In the configuration page:

1. Under "General":
   - Add a description
   - Check "Discard old builds" and set retention policy (e.g., keep last 10 builds)

2. Under "Build Triggers":
   - Select how you want to trigger the build:
     - Periodically with "Build periodically" (e.g., `H/4 * * * *` for every 4 hours)
     - On code changes with "Poll SCM" 
     - Via webhooks with "GitHub hook trigger for GITScm polling"

3. Under "Pipeline":
   - Select "Pipeline script from SCM"
   - Choose "Git" as SCM
   - Enter your repository URL (e.g., `https://github.com/YOUR_USERNAME/login-automation-testing.git`)
   - Specify branch (e.g., `*/master` or `*/main`)
   - Script Path: `Jenkinsfile`

4. Click "Save"

## Creating a Jenkinsfile

Create a file named `Jenkinsfile` in the root of your project with the following content:

```groovy
pipeline {
    agent any

    tools {
        maven 'Maven 3.8.6'  // Use the Maven version configured in Jenkins
        jdk 'JDK 11'         // Use the JDK version configured in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Allure Report') {
            steps {
                sh 'mvn allure:report'
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }
    
    post {
        always {
            // Clean workspace after build
            cleanWs()
        }
        success {
            echo 'Tests executed successfully!'
        }
        failure {
            echo 'Tests failed! Check Allure report for details.'
        }
    }
}
```

## Configuring Allure Plugin in Jenkins

1. Go to Jenkins dashboard
2. Navigate to "Manage Jenkins" > "Global Tool Configuration"
3. Find the "Allure Commandline" section
4. Click "Add Allure Commandline"
5. Give it a name (e.g., "Allure 2.24.0")
6. Select the version or specify an installation path
7. Click "Save"

## Viewing Allure Reports in Jenkins

After a successful pipeline run:

1. Go to your job page
2. Click on the build number
3. Click on "Allure Report" in the left sidebar

## Setting Up GitHub Webhooks

For automatic builds when code is pushed to GitHub:

1. In your GitHub repository, go to Settings > Webhooks
2. Click "Add webhook"
3. In "Payload URL" enter: `http://YOUR_JENKINS_URL/github-webhook/`
4. Select "Content type" as `application/json`
5. Choose which events trigger the webhook (usually "Just the push event")
6. Click "Add webhook"

## Additional Considerations

### Parameterized Builds

You can enhance your Jenkins job to allow parameterized builds:

1. In your job configuration, check "This project is parameterized"
2. Add parameters like:
   - Choice parameter for browser selection (e.g., chrome, firefox)
   - String parameter for test tags (e.g., @smoke, @regression)
   
Then update your Jenkinsfile to use these parameters:

```groovy
stage('Test') {
    steps {
        sh 'mvn test -Dcucumber.filter.tags="${TEST_TAGS}" -Dbrowser="${BROWSER}"'
    }
}
```

### Scheduled Builds

To run tests periodically (e.g., nightly):
1. In "Build Triggers", check "Build periodically"
2. Enter a cron expression (e.g., `0 0 * * *` for daily at midnight)

### Email Notifications

Add email notifications for build status:

```groovy
post {
    always {
        emailext (
            subject: "Build ${currentBuild.result}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
            body: """<p>Build Status: ${currentBuild.result}</p>
                    <p>Check the build page: <a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a></p>""",
            recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']]
        )
    }
}
```
