package com.envision.automation.framework.core;

import com.envision.automation.framework.utils.ExtentManager;
import com.envision.automation.framework.utils.ExtentTestManager;
import com.envision.automation.framework.utils.configLoader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.lang.reflect.Method;

public class BaseTest {
    public WebDriver driver;


    @BeforeSuite
    public  void loadConfigurations() throws IOException {
        ExtentManager.getReporter();
        configLoader.loadConfiguration();


    }

    @BeforeMethod
    public void loadBrowser(Method methodName){
        ExtentTestManager.startTest(methodName.getName(),"");
        this.driver= BasePage.launchBrowser(configLoader.getBrowserType());

    }

    @AfterMethod
    public void tearDownBrowser(){
        BasePage.closeBrowser();
        ExtentTestManager.stopTest();
    }

    @AfterSuite
    public  void  tearDownConfig(){
        driver= null;
        ExtentManager.getReporter().flush();
        ExtentManager.getReporter().close();

    }
}
