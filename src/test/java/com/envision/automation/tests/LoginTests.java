package com.envision.automation.tests;


import com.envision.automation.framework.core.BaseTest;
import com.envision.automation.pages.HomePage;
import com.envision.automation.pages.LandingPage;
import com.envision.automation.pages.LoginPage;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTests extends BaseTest {

    @Test
    public  void validateSuccessfulLoginToApplication() throws IOException, InterruptedException {
        LandingPage landingPage = new LandingPage(driver);
        LoginPage loginPage =  landingPage.launchAutomationPracticeApplication().clickOnSignIn();
        HomePage homePage = loginPage.enterUsername("syed@abc.com").enterPassword("Industrial/9505").clickOnSignIn();
        homePage.checkIfSignOutDisplayed().checkIfUsernameLoggedInIsValid("syed sam");

    }

}
