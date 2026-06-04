package com.appium.pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.testng.Assert;

/**
 * My Demo App (native) — products catalog after login.
 */
public class ProductsPage extends BasePage {

    private static final By PRODUCTS_TITLE = AppiumBy.accessibilityId("title");

    public boolean isDisplayed() {
        return waitForText(PRODUCTS_TITLE, "Products");
    }

    /** Reusable check that the products catalog screen is shown. */
    public void verifyDisplayed() {
        Assert.assertTrue(isDisplayed(), "Products screen title was not shown");
    }
}
