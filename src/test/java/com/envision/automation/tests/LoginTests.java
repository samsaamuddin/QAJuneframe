package com.envision.automation.tests;


import com.envision.automation.framework.core.BaseTest;
import com.envision.automation.framework.utils.DataGenerator;
import com.envision.automation.pages.HomePage;
import com.envision.automation.pages.LandingPage;
import com.envision.automation.pages.LoginPage;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTests extends BaseTest {


    @Test
    public  void validateSuccessfulLoginToApplication() throws IOException, InterruptedException {
        JSONObject loginData = jsonUtils.getJsonObject(jsonUtils.mainJsoOBj,"loginData");
        String username= jsonUtils.getJsonObjectvalue(loginData,"username");
        String password= jsonUtils.getJsonObjectvalue(loginData,"password");
        String loginname= jsonUtils.getJsonObjectvalue(loginData,"loginName");
        LandingPage landingPage = new LandingPage(driver);
        LoginPage loginPage =  landingPage.launchAutomationPracticeApplication().clickOnSignIn();
        HomePage homePage = loginPage.enterUsername(username).enterPassword(password).clickOnSignIn();
        homePage.checkIfSignOutDisplayed().checkIfUsernameLoggedInIsValid(loginname);

    }

}
