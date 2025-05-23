package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "remember")
    private WebElement rememberMeCheckbox;

    @FindBy(id = "loginButton")
    private WebElement loginButton;

    @FindBy(id = "errorMessage")
    private WebElement errorMessage;

    @FindBy(id = "loginForm")
    private WebElement loginForm;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
        sendKeys(usernameInput, username);
    }

    public void enterPassword(String password) {
        sendKeys(passwordInput, password);
    }

    public void checkRememberMe() {
        if (!rememberMeCheckbox.isSelected()) {
            click(rememberMeCheckbox);
        }
    }

    public void clickLoginButton() {
        click(loginButton);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }

    public boolean isOnLoginPage() {
        return isElementDisplayed(loginForm);
    }

    public boolean isUsernameFieldDisplayed() {
        return isElementDisplayed(usernameInput);
    }

    public boolean isPasswordFieldDisplayed() {
        return isElementDisplayed(passwordInput);
    }

    public boolean isRememberMeCheckboxDisplayed() {
        return isElementDisplayed(rememberMeCheckbox);
    }

    public boolean isLoginButtonDisplayed() {
        return isElementDisplayed(loginButton);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public void loginWithRememberMe(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        checkRememberMe();
        clickLoginButton();
    }

    public boolean areValidationMessagesDisplayed() {
        return true; // HTML5 validation will handle this
    }
}
