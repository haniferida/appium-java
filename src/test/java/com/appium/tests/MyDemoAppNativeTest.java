package com.appium.tests;

import org.testng.annotations.Test;

import com.appium.base.BaseTest;
import com.appium.pages.LoginPage;
import com.appium.pages.ProductsPage;

/**
 * Sample flow using {@link LoginPage} and cross-platform locators.
 * Requires Appium server, emulator/simulator, and {@code sample.app=mda-native}.
 */
public class MyDemoAppNativeTest extends BaseTest {

    @Test(description = "Login with demo user and land on Products screen")
    public void loginWithDemoUserShowsProducts() {
        LoginPage loginPage = new LoginPage();
        ProductsPage productsPage = new ProductsPage();

        loginPage.loginAsDemoUser();

        productsPage.verifyDisplayed();
        log.info("Logged in successfully");
    }
}
