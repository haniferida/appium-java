package com.appium.tests;

import com.appium.base.BaseTest;
import com.appium.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Sample flow using {@link LoginPage} and cross-platform locators.
 * Requires Appium server, emulator/simulator, and {@code sample.app=mda-native}.
 */
public class MyDemoAppLoginTest extends BaseTest {

    @Test(description = "Login with demo user and land on Products screen")
    public void loginWithDemoUserShowsProducts() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginAsDemoUser();

        Assert.assertTrue(loginPage.isProductsScreenDisplayed(), "Products screen title was not shown");
        log.info("Logged in successfully");
    }
}
