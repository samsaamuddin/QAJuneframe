package com.envision.automation.tests;

import com.envision.automation.framework.core.BasePage;

import com.envision.automation.framework.core.BaseTest;
import com.envision.automation.framework.utils.configLoader;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

import java.io.IOException;

public class LaunchBrowserTest extends BaseTest {






    @Test
    public void testLaunchOfBrowser() throws InterruptedException, IOException {
        driver.get("https://www.google.com/");
        BasePage.typeInto("tbxUsername", "Syed Samsaamuddin"+ Keys.ENTER);
        WebElement element= BasePage.findWebElement("tbxUsername");
        Thread.sleep(10000);





    }


}
