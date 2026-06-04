package com.appium.pages;

import com.appium.locators.CrossPlatformLocator;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

/**
 * My Demo App (native) — products catalog (default screen on app launch).
 */
public class ProductsPage extends BasePage {

    private static final By PRODUCTS_TITLE = AppiumBy.accessibilityId("title");

    private static final CrossPlatformLocator FIRST_PRODUCT_IMAGE = CrossPlatformLocator.named("First product image")
            .android(AppiumBy.androidUIAutomator(
                    "new UiSelector().resourceId(\"com.saucelabs.mydemoapp.android:id/productIV\").instance(0)"))
            .ios(AppiumBy.iOSClassChain("**/XCUIElementTypeOther[`name == 'ProductItem'`][1]"))
            .buildLocator();

    public boolean isDisplayed() {
        return waitForText(PRODUCTS_TITLE, "Products");
    }

    /** Reusable check that the products catalog screen is shown. */
    @Step("Verify products screen is displayed")
    public void verifyDisplayed() {
        Assert.assertTrue(isDisplayed(), "Products screen title was not shown");
    }

    @Step("Open first product from catalog")
    public void openFirstProduct() {
        click(FIRST_PRODUCT_IMAGE);
    }
}
