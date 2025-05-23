# Git and GitHub Setup

## Initializing Git Repository

```powershell
# Navigate to project root
cd c:\Users\15713\AutoPlayground

# Initialize git repository
git init

# Add all files to git
git add .

# Create initial commit
git commit -m "Initial commit: Login automation with Allure reporting"
```

## Creating GitHub Repository

1. Go to [GitHub](https://github.com/) and sign in
2. Click on the "+" icon in the top right and select "New repository"
3. Name your repository (e.g., "login-automation-testing")
4. Add a description: "Selenium and Cucumber based login automation tests with Allure reporting"
5. Choose visibility (Public or Private)
6. Leave "Initialize this repository with a README" unchecked
7. Click "Create repository"

## Connecting Local Repository to GitHub

```powershell
# Add remote GitHub repository
git remote add origin https://github.com/YOUR_USERNAME/login-automation-testing.git

# Push your code to GitHub
git push -u origin master
```

## .gitignore File

Consider adding a `.gitignore` file to exclude unnecessary files:

```
# IDE files
.idea/
*.iml
.vscode/
.eclipse/
.settings/
.classpath
.project

# Build artifacts
target/
test-output/
build/
out/
bin/

# Logs
*.log
logs/

# OS-specific files
.DS_Store
Thumbs.db

# Allure reports (optional, since these can be generated from results)
allure-report/

# Other
*.swp
*.bak
```
