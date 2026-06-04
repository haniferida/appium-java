package com.appium.pages;

import com.appium.config.ConfigReader;
import com.appium.drivers.DriverManager;
import com.appium.locators.CrossPlatformLocator;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected final AppiumDriver driver;
    protected final WebDriverWait wait;

    protected BasePage() {
        this.driver = DriverManager.getDriver();
        int timeoutSeconds = ConfigReader.getInt("explicit.wait.seconds", 15);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    protected WebElement find(By locator) {
        return waitForVisible(locator);
    }

    protected WebElement find(CrossPlatformLocator locator) {
        return find(locator.get());
    }

    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForVisible(CrossPlatformLocator locator) {
        return waitForVisible(locator.get());
    }

    protected WebElement waitForPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected WebElement waitForPresence(CrossPlatformLocator locator) {
        return waitForPresence(locator.get());
    }

    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement waitForClickable(CrossPlatformLocator locator) {
        return waitForClickable(locator.get());
    }

    protected void waitUntilInvisible(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void waitUntilInvisible(CrossPlatformLocator locator) {
        waitUntilInvisible(locator.get());
    }

    protected boolean waitForText(By locator, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    protected boolean waitForText(CrossPlatformLocator locator, String text) {
        return waitForText(locator.get(), text);
    }

    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    protected void click(By locator) {
        click(waitForClickable(locator));
    }

    protected void click(CrossPlatformLocator locator) {
        click(locator.get());
    }
}
