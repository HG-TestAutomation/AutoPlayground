@echo off
echo Running tests and generating Allure reports...

echo Step 1: Clean and build the project
call mvn clean

echo Step 2: Run the tests
call mvn test

echo Step 3: Generate Allure report
call mvn allure:report

echo Step 4: Open Allure report in browser
call mvn allure:serve

echo Done!
