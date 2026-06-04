package com.appium.reporting;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AllureAttachments {

    private static final Logger log = LoggerFactory.getLogger(AllureAttachments.class);

    private AllureAttachments() {
    }

    public static void captureFailure(AppiumDriver driver) {
        attachScreenshot(driver);
        attachPageSource(driver);
    }

    public static void attachScreenshot(AppiumDriver driver) {
        try {
            byte[] screenshot = driver.getScreenshotAs(OutputType.BYTES);
            Allure.getLifecycle().addAttachment("Screenshot", "image/png", "png", screenshot);
        } catch (Exception e) {
            log.warn("Could not attach screenshot to Allure report", e);
        }
    }

    public static void attachPageSource(AppiumDriver driver) {
        try {
            String pageSource = driver.getPageSource();
            Allure.addAttachment("Page source", "text/xml", pageSource);
        } catch (Exception e) {
            log.warn("Could not attach page source to Allure report", e);
        }
    }
}
