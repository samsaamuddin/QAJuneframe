package com.envision.automation.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class configLoader {
    static Properties configurations;

    private static String browserType;
    private static String chromeDriverPath;
    private static String edgeDriverPath;
    private static String firefoxDriverPath;
    private static int waitTime;
    public static String getBrowserType(){

        return  browserType;
    }

    public static String getChromeDriverPath() {
        return chromeDriverPath;
    }

    public static String getEdgeDriverPath() {
        return edgeDriverPath;
    }

    public static String getFirefoxDriverPath() {
        return firefoxDriverPath;
    }



    public static Integer getWaitTime() {
        return waitTime;
    }

    public static void loadConfiguration() throws IOException {
        try {
            configurations = PropertiesLoader.loadPropertiesFile(System.getProperty("user.dir") + "\\src\\main\\resources\\configs\\config.properties");

            browserType = getConfigValue("browserType");
            chromeDriverPath = getConfigValue("chromeDriverPath");
            edgeDriverPath = getConfigValue("edgeDriverPath");
            firefoxDriverPath = getConfigValue("firefoxDriverPath");
            waitTime = Integer.parseInt(getConfigValue("waitTime"));
        }catch (Exception e){
            Reporter.logFailedStep("Unable to load configurations");

        }



    }
    public static String getConfigValue(String propertyName){
        String value = PropertiesLoader.getPropertyValue(propertyName, configurations);
        return value;
    }

    //public static void main(String[] args) throws IOException {
    //   configLoader configLoader = new configLoader();
    //  configLoader.loadConfiguration();
    // System.out.println("Property value is :" + configLoader.getConfigValue("browserType"));
    // }




}
