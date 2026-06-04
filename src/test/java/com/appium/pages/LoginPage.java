package com.appium.pages;

import com.appium.locators.CrossPlatformLocator;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * My Demo App (native) — login flow from the catalog menu.
 */
public class LoginPage extends BasePage {

    public static final String DEMO_USERNAME = "bob@example.com";
    public static final String DEMO_PASSWORD = "10203040";

    // --- Locators (Android / iOS on the same page) ---

    private static final CrossPlatformLocator MENU_BUTTON = CrossPlatformLocator.named("Catalog menu")
            .android(AppiumBy.accessibilityId("View menu"))
            .ios(AppiumBy.accessibilityId("More-tab-item"))
            .buildLocator();

    private static final CrossPlatformLocator LOGIN_MENU_ITEM = CrossPlatformLocator.named("Login menu item")
            .android(AppiumBy.accessibilityId("Login Menu Item"))
            .ios(AppiumBy.accessibilityId("Login Button"))
            .buildLocator();

    private static final CrossPlatformLocator USERNAME_FIELD = CrossPlatformLocator.named("Username field")
            .android(AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET"))
            .ios(AppiumBy.iOSClassChain("**/XCUIElementTypeTextField[1]"))
            .buildLocator();

    private static final CrossPlatformLocator PASSWORD_FIELD = CrossPlatformLocator.named("Password field")
            .android(AppiumBy.id("com.saucelabs.mydemoapp.android:id/passwordET"))
            .ios(AppiumBy.iOSClassChain("**/XCUIElementTypeSecureTextField[1]"))
            .buildLocator();

    private static final CrossPlatformLocator LOGIN_BUTTON = CrossPlatformLocator.named("Login submit")
            .android(AppiumBy.accessibilityId("Tap to login with given credentials"))
            .ios(AppiumBy.iOSNsPredicateString("label == 'Login' AND type == 'XCUIElementTypeButton'"))
            .buildLocator();

    private static final By PRODUCTS_TITLE = AppiumBy.accessibilityId("title");

    // --- Actions ---

    public LoginPage openFromCatalogMenu() {
        click(MENU_BUTTON);
        click(LOGIN_MENU_ITEM);
        waitForVisible(USERNAME_FIELD);
        return this;
    }

    public LoginPage enterCredentials(String username, String password) {
        WebElement usernameField = find(USERNAME_FIELD);
        usernameField.clear();
        usernameField.sendKeys(username);

        WebElement passwordField = find(PASSWORD_FIELD);
        passwordField.clear();
        passwordField.sendKeys(password);
        return this;
    }

    public LoginPage submitLogin() {
        click(LOGIN_BUTTON);
        return this;
    }

    public LoginPage loginAsDemoUser() {
        return openFromCatalogMenu()
                .enterCredentials(DEMO_USERNAME, DEMO_PASSWORD)
                .submitLogin();
    }

    public boolean isProductsScreenDisplayed() {
        return waitForText(PRODUCTS_TITLE, "Products");
    }
}
