pipeline {
    agent any

    tools {
        maven 'Maven 3.8.6'  // Use the Maven version configured in Jenkins
        jdk 'JDK 11'         // Use the JDK version configured in Jenkins
    }

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox'], description: 'Browser for test execution')
        string(name: 'TAGS', defaultValue: '@smoke', description: 'Cucumber tags to execute')
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
                sh "mvn test -Dcucumber.filter.tags=\"${params.TAGS}\" -Dbrowser=\"${params.BROWSER}\""
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Generate Allure Report') {
            steps {
                sh 'mvn allure:report'
            }
        }
        
        stage('Publish Allure Report') {
            steps {
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
            archiveArtifacts artifacts: 'target/cucumber-reports/**/*', allowEmptyArchive: true
        }
        success {
            echo 'Tests executed successfully!'
        }
        failure {
            echo 'Tests failed! Check Allure report for details.'
        }
    }
}
