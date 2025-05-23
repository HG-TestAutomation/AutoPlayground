# Visual Guide - Login Feature Tests

## Test Flow Diagram

```
+---------------------+      +---------------------+      +-------------------+
| 1. Navigate to      |      | 2. Perform Login    |      | 3. Verify Result  |
| Login Page          | ---> | Action              | ---> | (Success/Error)   |
+---------------------+      +---------------------+      +-------------------+
```

## Test Scenarios Visualization

### Verify Form Elements

```
+-----------------------------------+
| Login Form                        |
|                                   |
| Username: [____________]          |
| Password: [____________]          |
|                                   |
| [✓] Remember Me                   |
|                                   |
| [      Login       ]              |
+-----------------------------------+
```

### Empty Fields Validation

```
+-----------------------------------+
| Login Form                        |
|                                   |
| Username: [____________] ❌        |
| Error: Field is required          |
|                                   |
| Password: [____________] ❌        |
| Error: Field is required          |
|                                   |
| [✓] Remember Me                   |
|                                   |
| [      Login       ]              |
+-----------------------------------+
```

### Success Path

```
+-----------------------------------+     +--------------------------------+
| Login Form                        |     | Home Page                      |
|                                   |     |                                |
| Username: [____admin____]         |     | Welcome, admin!                |
| Password: [____password__]        | --> |                                |
|                                   |     | You are successfully logged in |
| [ ] Remember Me                   |     |                                |
|                                   |     |                                |
| [      Login       ]              |     |                                |
+-----------------------------------+     +--------------------------------+
```

### Invalid Credentials

```
+-----------------------------------+     +-----------------------------------+
| Login Form                        |     | Login Form                        |
|                                   |     |                                   |
| Username: [___invalid___]         |     | Username: [___invalid___]         |
| Password: [___password__]         | --> | Password: [___password__]         |
|                                   |     |                                   |
| [ ] Remember Me                   |     | [ ] Remember Me                   |
|                                   |     |                                   |
| [      Login       ]              |     | [      Login       ]              |
+-----------------------------------+     +-----------------------------------+
                                          | ❌ Invalid username or password    |
                                          +-----------------------------------+
```

## Allure Report Example

```
+-----------------------------------------------------------------------+
| Test Results                                                           |
+-----------------------------------------------------------------------+
| ✓ Verify login form elements are displayed                             |
| ✓ Login with empty fields                                              |
| ✓ Successful login                                                     |
| ✓ Login with remember me checkbox                                      |
| ✓ Login with different credentials (admin/wrong123)                    |
| ✓ Login with different credentials (invalid/password)                  |
| ✓ Login with different credentials (invalid/wrong123)                  |
+-----------------------------------------------------------------------+
| Screenshots | Timeline | Behaviors | Categories | Environment | Trends |
+-----------------------------------------------------------------------+
```

## Test Execution Flow

```
Start
  ↓
Initialize WebDriver
  ↓
Navigate to Login Page
  ↓
Execute Test Steps
  ↓
Take Screenshot
  ↓
Attach to Allure Report
  ↓
Close WebDriver
  ↓
End
```
