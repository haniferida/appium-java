package com.appium.pages;

import com.appium.locators.CrossPlatformLocator;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;
import org.testng.Assert;

/**
 * My Demo App (native) — product detail screen opened from the catalog.
 */
public class ProductDetailPage extends BasePage {

    public static final String FIRST_PRODUCT_NAME = "Sauce Labs Backpack";
    public static final String FIRST_PRODUCT_PRICE = "$ 29.99";

    private static final CrossPlatformLocator ADD_TO_CART_BUTTON = CrossPlatformLocator.named("Add to cart")
            .android(AppiumBy.accessibilityId("Tap to add product to cart"))
            .ios(AppiumBy.accessibilityId("Add To Cart button"))
            .buildLocator();

    private static final CrossPlatformLocator PRODUCT_NAME = CrossPlatformLocator.named("Product name")
            .android(AppiumBy.id("com.saucelabs.mydemoapp.android:id/productTV"))
            .ios(AppiumBy.accessibilityId("Product Title"))
            .buildLocator();

    private static final CrossPlatformLocator PRODUCT_PRICE = CrossPlatformLocator.named("Product price")
            .android(AppiumBy.id("com.saucelabs.mydemoapp.android:id/priceTV"))
            .ios(AppiumBy.accessibilityId("Product Price"))
            .buildLocator();

    @Step("Verify product detail screen is displayed for '{0}'")
    public void verifyDisplayed(String expectedProductName, String expectedPrice) {
        waitForVisible(ADD_TO_CART_BUTTON);
        Assert.assertTrue(
                waitForText(PRODUCT_NAME, expectedProductName),
                "Product name was not shown on detail screen");
        Assert.assertTrue(
                waitForText(PRODUCT_PRICE, expectedPrice),
                "Product price was not shown on detail screen");
    }

    @Step("Verify first product detail screen is displayed")
    public void verifyFirstProductDisplayed() {
        verifyDisplayed(FIRST_PRODUCT_NAME, FIRST_PRODUCT_PRICE);
    }
}
