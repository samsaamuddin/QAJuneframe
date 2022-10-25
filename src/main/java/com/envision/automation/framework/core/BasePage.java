package com.envision.automation.framework.core;


import com.envision.automation.framework.utils.ORUtils;
import com.envision.automation.framework.utils.Reporter;
import com.envision.automation.framework.utils.configLoader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;



public class BasePage {
    public BasePage() {

        super();
    }

    static WebDriver baseDriver;

    public BasePage(WebDriver driver) {
        this.baseDriver = driver;

    }

    public static WebDriver launchBrowser(String browserType) {
        try {
            if (browserType.equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver", configLoader.getChromeDriverPath());
                baseDriver = new ChromeDriver();

            } else if (browserType.equalsIgnoreCase("edge")) {
                System.setProperty("webdriver.edge.driver", configLoader.getEdgeDriverPath());
                baseDriver = new EdgeDriver();
            } else if (browserType.equalsIgnoreCase("firefox")) {
                System.setProperty("webdriver.gecko.driver", configLoader.getFirefoxDriverPath());
                baseDriver = new FirefoxDriver();
            } else {
                throw new UnsupportedOperationException("Browser Type [" + browserType + "] is not supported");

            }
            baseDriver.manage().timeouts().pageLoadTimeout(configLoader.getWaitTime(), TimeUnit.SECONDS);
            baseDriver.manage().timeouts().implicitlyWait(configLoader.getWaitTime(), TimeUnit.SECONDS);
            baseDriver.manage().window().maximize();
            Reporter.logPassedStep("Browser [" + browserType + "] launched and maximized successfully");
        } catch (Exception ex) {
            Reporter.logFailedStep("Unable to launch browser[" + browserType + "], exception occurred" + ex);

        }

        return baseDriver;
    }

    public static By getFindBy(String propertyName) throws IOException {
        By by = null;
        try {

            ORUtils.loadFile();

            String orElementValue = ORUtils.getORPropertyValue(propertyName);
            String byMode = orElementValue.split("#", 2)[0];
            String byValue = orElementValue.split("#", 2)[1];

            if (byMode.equalsIgnoreCase("id")) {
                by = By.id(byValue);


            } else if (byMode.equalsIgnoreCase("name")) {
                by = By.name(byValue);
            } else if (byMode.equalsIgnoreCase("class")) {
                by = By.className(byValue);
            } else if (byMode.equalsIgnoreCase("tag")) {
                by = By.tagName(byValue);
            } else if (byMode.equalsIgnoreCase("css")) {
                by = By.cssSelector(byValue);
            } else if (byMode.equalsIgnoreCase("xpath")) {
                by = By.xpath(byValue);
            } else if (byMode.equalsIgnoreCase("lt")) {
                by = By.linkText(byValue);
            } else if (byMode.equalsIgnoreCase("plt")) {
                by = By.partialLinkText(byValue);
            } else {
                throw new UnsupportedOperationException("ByMode value is not supported, check the OR.properties and pass the value");
            }

        } catch (Exception e) {
            Reporter.logFailedStep("unable to get By locator [" + by + "]");

        }


        return by;

    }

    public static WebElement findWebElement(String orElement) throws IOException {
        WebElement element = null;
        try {


            By by = getFindBy(orElement);

            element = new WebDriverWait(baseDriver, configLoader.getWaitTime()).until(ExpectedConditions.presenceOfElementLocated(by));
            element = new WebDriverWait(baseDriver, configLoader.getWaitTime()).until(ExpectedConditions.visibilityOfElementLocated(by));
            Reporter.logPassedStep("OR element[" + orElement + "] found successfully");
        } catch (Exception e) {
            Reporter.logFailedStep("Unable to find the element [" + orElement + "]");

        }
        return element;
    }

    public List<WebElement> findWebElements(String orElement) throws IOException {
        List<WebElement> elements = null;
        try {

            By by = getFindBy(orElement);

            elements = new WebDriverWait(baseDriver, configLoader.getWaitTime()).until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
            Reporter.logPassedStep("Elements [" + orElement + "] found successfully");

        } catch (Exception e) {
            Reporter.logFailedStep("Unable to find elements [" + orElement + "]");

        }
        return elements;
    }

    public void clickOn(String elementName) throws IOException {
        try {
            WebElement element = findWebElement(elementName);
            new WebDriverWait(baseDriver, configLoader.getWaitTime()).until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            Reporter.logPassedStep("Clicked on element [" + elementName + "] successfully");
        } catch (Exception e) {
            Reporter.logFailedStep("Unable to click on element [" + elementName + "]");

        }
    }

    public static void typeInto(String elementName, String contentToType) throws IOException, InterruptedException {
        WebElement element = null;
        try {
            element = findWebElement(elementName);
            new WebDriverWait(baseDriver, configLoader.getWaitTime()).until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            Thread.sleep(100);
            element.clear();
            Thread.sleep(100);
            element.sendKeys(contentToType);
            Reporter.logPassedStep("Typed [" + contentToType + "] into element [" + elementName + "]");
        } catch (Exception e) {
            Reporter.logFailedStep("Unable to type [" + contentToType + "] into element [" + elementName + "]");

        }

    }

    public String getWebElementText(String elementName) throws IOException {
        WebElement element = null;
        String text = null;
        try {
            element = findWebElement(elementName);
            text = element.getText();
            Reporter.logPassedStep("Fetched Text[" + text + "] from element [" + elementName + "]");

        } catch (Exception e) {
            Reporter.logFailedStep("Unable to fetch text from element [" + elementName + "]");

        }
        return text;


    }

    public String getWebElementAttribute(String elementName, String attributeType) throws IOException {
        WebElement element = null;
        String attributeValue = null;
        try {
            element = findWebElement(elementName);
            attributeValue = element.getAttribute(attributeType);
            Reporter.logPassedStep("Fetched attribute[" + attributeType + "] value [" + attributeValue + "]");
        } catch (Exception e) {
            Reporter.logFailedStep("Unable to Fetch attribute[" + attributeType + "] value [" + attributeValue + "]");


        }
        return attributeValue;
    }

    public static void closeBrowser() {
        try {
            baseDriver.close();
            Reporter.logPassedStep("Closed Browser Successfully");
        } catch (Exception e) {
            Reporter.logFailedStep("Unable to Close Browser");

        }
    }

    public static void closeAllbrowsers() {
        try {
            baseDriver.quit();
            Reporter.logPassedStep("Closed All Browsers and terminated driver seesion");
        } catch (Exception e) {
            Reporter.logFailedStep("Unable to Close Browser and driver");


        }
    }

    public static void refreshPage() {
        try {
            baseDriver.navigate().refresh();
            Reporter.logPassedStep("Web Page refreshed!!");

        } catch (Exception e) {
            Reporter.logFailedStep("Web Page failed to refresh");

        }
    }

    public String getCurrentUrl() {
        String url = null;
        try {
            url = baseDriver.getCurrentUrl();
            Reporter.logPassedStep("The current URL of the Page is " + url);
        } catch (Exception e) {
            Reporter.logFailedStep("Unable to fetch current URL of the page");


        }
        return url;
    }

    public String getTitleOfPage() {
        String title = null;
        try {
            title = baseDriver.getTitle();
            Reporter.logPassedStep("Title of the page is: " + title);
        } catch (Exception e) {
            Reporter.logFailedStep("Unable to fetch the title of the page");


        }
        return title;
    }

    public void selectFromDropDown(String elementName, String how, String valueToSelect) throws IOException {
        WebElement element = null;
        try {
            element = findWebElement(elementName);
            Select dropDownList = new Select(element);

            if (how.equalsIgnoreCase("value")) {
                dropDownList.selectByValue(valueToSelect);
            } else if (how.equalsIgnoreCase("visibleText")) {
                dropDownList.deselectByVisibleText(valueToSelect);
            } else if (how.equalsIgnoreCase("index")) {
                dropDownList.selectByIndex(Integer.parseInt(valueToSelect));

            }
            Reporter.logPassedStep("Value [" + valueToSelect + "] selected from dropdown [" + elementName + "]");
        } catch (Exception e) {
            Reporter.logFailedStep("Unable to select Value [" + valueToSelect + "] select from dropdown [" + elementName + "]");


        }

    }

    public void selectIndexValueFromDropdown(String elementName, String value) throws IOException {
        try {
            selectFromDropDown(elementName, "index", value);
        } catch (Exception e) {

        }


    }

    public void selectVisibleValueFromDropdown(String elementName, String value) throws IOException {
        try {
            selectFromDropDown(elementName, "visibleText", value);
        } catch (Exception e) {

        }
    }

    public void selectValueFromDropdown(String elementName, String value) throws IOException {
        try {
            selectFromDropDown(elementName, "value", value);
        } catch (Exception e) {

        }
    }

    public boolean isDisplayed(String elementName) throws IOException {
        WebElement element = null;
        try {
            element = findWebElement(elementName);
            Reporter.logPassedStep("Element [" + elementName + "] displayed  successfully");

        } catch (Exception e) {
            Reporter.logFailedStep("Element[" + elementName + "] not displayed");

        }
        return element.isDisplayed();


    }

    public void switchToSecondWindow() {
        try {
            Set<String> windows = this.baseDriver.getWindowHandles();
            List<String> windowsAll = new ArrayList<String>(windows);
            int secondWindow = 1;
            this.baseDriver.switchTo().window(windowsAll.get(secondWindow));
            Reporter.logPassedStep("Successfully switched to secondWindow");
        } catch (Exception e) {
            Reporter.logFailedStep("Unable to switch to second window");

        }
    }
        public void launchUrl (String url){
            try {
                this.baseDriver.get(url);
                Reporter.logPassedStep("Url [" + url + "] launched succesfully");
            } catch (Exception e) {
                Reporter.logFailedStep("Unable to launch Url [" + url + "]");

            }
        }
}

