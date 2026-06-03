package com.appium.tests;

import com.appium.base.BaseTest;
import com.appium.drivers.DriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Smoke test — verifies Appium session starts.
 * Replace with real flows once app package/activity (or bundleId) are configured.
 */
public class SampleLoginTest extends BaseTest {

    @Test(description = "Appium session starts successfully")
    public void sessionStarts() {
        Assert.assertNotNull(DriverManager.getDriver().getSessionId());
        log.info("Platform: {}", DriverManager.getDriver().getCapabilities().getPlatformName());
    }
}
