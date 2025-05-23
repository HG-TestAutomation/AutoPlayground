package com.automation.utils;

public class Constants {

    // General-purpose config file path
    public static final String CONFIGURATION_FILEPATH = System.getProperty("user.dir")
            + "/src/test/resources/config.properties";

    // Default wait times
    public static final int IMPLICIT_WAIT = 60;
    public static final int EXPLICIT_WAIT = 60;
    public static final int MAX_WAIT_SECONDS = 700;
    public static final int POLLING_INTERVAL_SECONDS = 15;

    // (Optional) where screenshots would go
    public static final String SCREENSHOT_FILEPATH = System.getProperty("user.dir") + "/screenshots/";
}
