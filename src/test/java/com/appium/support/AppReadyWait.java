package com.appium.support;

import com.appium.config.ConfigReader;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Waits for the My Demo App catalog screen after launch (SplashActivity seeds the DB on first run).
 */
public final class AppReadyWait {

    private static final Logger log = LoggerFactory.getLogger(AppReadyWait.class);

    private AppReadyWait() {
    }

    public static void waitForCatalog(AppiumDriver driver) {
        if (!"ci".equalsIgnoreCase(System.getProperty("env", "local"))) {
            return;
        }

        int timeoutSeconds = ConfigReader.getInt("app.ready.timeout.seconds", 120);
        log.info("Waiting up to {}s for catalog screen (CI emulator)", timeoutSeconds);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(d -> isCatalogVisible((AppiumDriver) d));
        log.info("Catalog screen is ready");
    }

    private static boolean isCatalogVisible(AppiumDriver driver) {
        try {
            if (driver.findElement(AppiumBy.accessibilityId("title")).isDisplayed()) {
                return true;
            }
        } catch (NoSuchElementException ignored) {
            // Splash or another screen still showing
        }
        try {
            return driver.findElement(AppiumBy.accessibilityId("View menu")).isDisplayed();
        } catch (NoSuchElementException ignored) {
            return false;
        }
    }
}
