package com.appium.drivers;

import io.appium.java_client.AppiumDriver;

public final class DriverManager {

    private static final ThreadLocal<AppiumDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    public static AppiumDriver getDriver() {
        AppiumDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("Driver not initialized. Call setDriver() from BaseTest.");
        }
        return driver;
    }

    public static void setDriver(AppiumDriver driver) {
        DRIVER.set(driver);
    }

    public static void quitDriver() {
        AppiumDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}
