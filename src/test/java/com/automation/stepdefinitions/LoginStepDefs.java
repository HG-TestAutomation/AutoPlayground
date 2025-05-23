package com.automation.stepdefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import com.automation.pages.LoginPage;
import com.automation.utils.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginStepDefs {
    private WebDriver driver;
    private LoginPage loginPage;
    private WebDriverWait wait;

    public LoginStepDefs() {
        driver = DriverManager.getDriver();
        loginPage = new LoginPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        driver.get(DriverManager.getProperty("local_url"));
        Assert.assertTrue("Login page is not displayed", loginPage.isOnLoginPage());
        System.out.println("Login form is displayed as expected.");
    }

    @Then("the login form should be displayed")
    public void theLoginFormShouldBeDisplayed() {
        Assert.assertTrue("Login form is not displayed", loginPage.isOnLoginPage());
        System.out.println("Login form is displayed as expected.");
    }

    @And("the username field should be displayed")
    public void theUsernameFieldShouldBeDisplayed() {
        Assert.assertTrue("Username field is not displayed", loginPage.isUsernameFieldDisplayed());
        System.out.println("Username input field is displayed as expected.");
    }

    @And("the password field should be displayed")
    public void thePasswordFieldShouldBeDisplayed() {
        Assert.assertTrue("Password field is not displayed", loginPage.isPasswordFieldDisplayed());
        System.out.println("Password input field is displayed as expected.");
    }

    @And("the remember me checkbox should be displayed")
    public void theRememberMeCheckboxShouldBeDisplayed() {
        Assert.assertTrue("Remember me checkbox is not displayed", loginPage.isRememberMeCheckboxDisplayed());
        System.out.println("Remember me checkbox is displayed as expected.");
    }

    @And("the login button should be displayed")
    public void theLoginButtonShouldBeDisplayed() {
        Assert.assertTrue("Login button is not displayed", loginPage.isLoginButtonDisplayed());
        System.out.println("Login button is displayed as expected.");
    }

    @When("I enter username {string}")
    public void iEnterUsername(String username) {
        loginPage.enterUsername(username);
    }

    @And("I enter password {string}")
    public void iEnterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @And("I check the remember me checkbox")
    public void iCheckTheRememberMeCheckbox() {
        loginPage.checkRememberMe();
    }

    @When("I click the login button")
    public void iClickTheLoginButton() {
        loginPage.clickLoginButton();
    }    @Then("I should be redirected to the home page")
    public void iShouldBeRedirectedToTheHomePage() {
        try {
            Thread.sleep(1000); // Small wait for redirection
            String currentUrl = driver.getCurrentUrl().split("\\?")[0]; // Remove query params
            Assert.assertTrue("Not redirected to home page", 
                currentUrl.toLowerCase().endsWith("home.html"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("the form should show validation messages")
    public void theFormShouldShowValidationMessages() {
        Assert.assertTrue("Form validation is not working", 
            loginPage.areValidationMessagesDisplayed());
    }

    @Then("I should see the error message {string}")
    public void iShouldSeeTheErrorMessage(String expectedMessage) {
        Assert.assertEquals("Error message does not match", 
            expectedMessage, loginPage.getErrorMessage());
    }
}
