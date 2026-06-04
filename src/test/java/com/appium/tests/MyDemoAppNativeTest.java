package com.appium.tests;

import org.testng.annotations.Test;

import com.appium.base.BaseTest;
import com.appium.pages.LoginPage;
import com.appium.pages.ProductDetailPage;
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

    @Test(description = "Open first product and land on product detail screen")
    public void openFirstProductShowsDetailPage() {
        ProductsPage productsPage = new ProductsPage();
        ProductDetailPage productDetailPage = new ProductDetailPage();

        productsPage.verifyDisplayed();
        productsPage.openFirstProduct();

        productDetailPage.verifyFirstProductDisplayed();
        log.info("Product detail screen displayed for {}", ProductDetailPage.FIRST_PRODUCT_NAME);
    }
}
