package com.automation.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import com.automation.utils.DriverManager;

public class Hooks {
    
    @Before
    public void setup() {
        DriverManager.initializeDriver();
        
    }    @After
    public void tearDown(Scenario scenario) {
        if (DriverManager.getDriver() != null) {
            // Take screenshot as bytes
            String screenshotName = scenario.getName().replaceAll(" ", "_");
            byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
            
            // Attach to Cucumber (will be picked up by Allure through the Cucumber plugin)
            scenario.attach(screenshot, "image/png", screenshotName);
            
            // Additional direct attachment to Allure for enhanced reporting
            try {
                Allure.addAttachment(screenshotName, new ByteArrayInputStream(screenshot));
            } catch (Exception e) {
                System.err.println("Failed to attach screenshot to Allure report: " + e.getMessage());
            }
        }
        DriverManager.quitDriver();
    }
}

