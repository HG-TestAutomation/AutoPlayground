package com.automation.utils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final String CONFIG_PATH = "./src/test/resources//config/config.properties";
    private static final String EXTENT_PATH = "./src/test/resources/extent.properties";

    /**
     * Loads the main config.properties file
     */
    public static Properties loadConfigProperties() {
        return loadPropertiesFromFile(CONFIG_PATH);
    }

    /**
     * Loads the extent.properties file
     */
    public static Properties loadExtentProperties() {
        return loadPropertiesFromFile(EXTENT_PATH);
    }

    /**
     * Generic loader for any .properties file
     */
    private static Properties loadPropertiesFromFile(String filePath) {
        Properties prop = new Properties();
        try (FileInputStream ip = new FileInputStream(filePath)) {
            prop.load(ip);
        } catch (IOException e) {
            e.printStackTrace(); // You could log this instead
        }
        return prop;
    }
}
