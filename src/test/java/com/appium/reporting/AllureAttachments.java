package com.appium.reporting;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AllureAttachments {

    private static final Logger log = LoggerFactory.getLogger(AllureAttachments.class);

    private AllureAttachments() {
    }

    public static void captureFailure(AppiumDriver driver) {
        try {
            screenshot(driver);
            pageSource(driver);
        } catch (Exception e) {
            log.warn("Could not attach failure artifacts to Allure report", e);
        }
    }

    @Attachment(value = "Screenshot on failure", type = "image/png", fileExtension = ".png")
    private static byte[] screenshot(AppiumDriver driver) {
        return driver.getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page source on failure", type = "text/xml", fileExtension = ".xml")
    private static String pageSource(AppiumDriver driver) {
        return driver.getPageSource();
    }
}
